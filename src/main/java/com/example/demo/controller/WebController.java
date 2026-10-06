package com.example.demo.controller;

import com.example.demo.TaxCalculator;
import com.example.demo.util.ExchangeRate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Validated
@Controller
public class WebController {

    private final ExchangeRate exchangeRate;

    public WebController(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @GetMapping("/")
    public String index(Model model) {
        log.info("[PAGE_VIEW] A visitor opened the tax calculator landing page.");
        model.addAttribute("textValue", "");
        return "index";
    }

    @PostMapping("/calculate")
    public String processTaxForm(
            @Min(value = 0, message = "Please submit only positive integer!")
            @RequestParam(value = "grossValue", required = false) Integer submittedGrossValue,

            @Min(value = 0, message = "The minimum tax percentage is 0%!")
            @Max(value = 100, message = "The maximum tax percentage is 100%!")
            @RequestParam("taxPercentage") int taxPercentage,

            @Pattern(
                    regexp = "^(EUR|HUF|NOK|USD)$",
                    message = "Not supported currency!"
            ) @RequestParam("currency") String currency,

            @RequestParam(value = "haveTaxCard", defaultValue = "false") boolean haveTaxCard,
            Model model) {

        if (haveTaxCard) {
            log.info("[CHECKBOX] User checked the 'haveTaxCard' field.");
        } else {
            log.info("[CHECKBOX] User left the 'haveTaxCard' field unchecked.");
        }

        if (submittedGrossValue == null) {
            log.warn("[VALIDATION_FAILED] Calculation failed: The submitted gross value was empty.");
            model.addAttribute("textValue", "Error: Gross value cannot be empty!");
            model.addAttribute("BasicTax", "0");
            model.addAttribute("BandTax", "0");
            model.addAttribute("HealthContribution", "0");
            return "calculate";
        }

        log.info("[INPUT_DATA] Received input payload: {} {}", submittedGrossValue, currency);

        double convertedGrossValue;

        try {
            convertedGrossValue = exchangeRate.convertCurrencyToNok(submittedGrossValue, currency);
            log.info("[CURRENCY_CONVERSION] Successfully converted to NOK. Result: {} NOK (Original: {} {})",
                    convertedGrossValue, submittedGrossValue, currency);

        } catch (Exception e) {
            log.error("[API_ERROR] Exchange rate conversion failed. Cause: {}", e.getMessage());

            model.addAttribute("textValue", "Error: The ExchangeRate API is not available! Please select NOK currency and try again.");
            model.addAttribute("BasicTax", "0");
            model.addAttribute("BandTax", "0");
            model.addAttribute("HealthContribution", "0");
            return "calculate";
        }

        if (haveTaxCard) {
            int netSalary = (int) (convertedGrossValue / 100 * taxPercentage);
            log.info("[CALCULATION_TYPE] Running flat-rate tax card logic. Percentage: {}%, Net result: {} NOK",
                    taxPercentage, netSalary);

            model.addAttribute("textValue", "Your net: " + netSalary);
            return "calculate_withcard";

        } else {
            log.info("[CALCULATION_TYPE] Running standard progressive bracket tax logic.");
            TaxCalculator taxCalculator = new TaxCalculator(convertedGrossValue);

            String netSalary = String.valueOf(Math.round(taxCalculator.calculateNetValue()));

            model.addAttribute("textValue", "Your net: " + netSalary);
            model.addAttribute("BasicTax", String.valueOf(Math.round(taxCalculator.calculateBasictax())));
            model.addAttribute("BandTax", String.valueOf(Math.round(taxCalculator.calculateBandtax())));
            model.addAttribute("HealthContribution", String.valueOf(Math.round(taxCalculator.calculateHealthContribution())));
            return "calculate";
        }
    }
}

