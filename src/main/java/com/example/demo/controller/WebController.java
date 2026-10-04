package com.example.demo.controller;

import com.example.demo.TaxCalculator;
import com.example.demo.util.ExchangeRate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Validated
@Controller
public class WebController {


    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("textValue", "");
        return "index";
    }


    @PostMapping("/calculate")
    public String processTaxForm(

            @Min(value = 0, message = "Please submit only positive integer!")
            @RequestParam("grossValue") int submittedGrossValue,

            @Min(value = 0, message = "The minimum tax percentage is 0%!")
            @Max(value = 100, message = "The maximum tax percentage is 100%!")
            @RequestParam("taxPercentage") int taxPercentage,

            @Pattern(
                    regexp = "^(EUR|HUF|NOK|USD)$",
                    message = "Not supported currency!"
            ) @RequestParam("currency") String currency,

            @RequestParam(value = "haveTaxCard", defaultValue = "false") boolean haveTaxCard,
            Model model) {


        double convertedGrossValue = new ExchangeRate().convertCurrencyToNok(submittedGrossValue, currency);

        if (haveTaxCard) {
            int netSalary = (int) (convertedGrossValue / 100 * taxPercentage);

            model.addAttribute("textValue", "Your net: " + netSalary);

            return "calculate_withcard";

        } else {
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
