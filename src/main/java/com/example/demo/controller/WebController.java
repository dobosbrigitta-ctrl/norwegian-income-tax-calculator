package com.example.demo.controller;

import com.example.demo.TaxCalculator;
import com.example.demo.util.ExchangeRate;
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
    public String sayHello(
            @Pattern(
                    regexp = "^[0-9]+$",
                    message = "Not supported currency!"
            )  @RequestParam("myTextBox") String submittedText,
            @Pattern(
                    regexp = "^(EUR|HUF|NOK|USD)$",
                    message = "Please submit only positive integer!"
            ) @RequestParam("currency") String currency, Model model) {

        double amountAsDouble = Double.parseDouble(submittedText);
        double convertedAmount = new ExchangeRate().convertCurrencyToNok(amountAsDouble, currency);

        TaxCalculator taxCalculator = new TaxCalculator(convertedAmount);

        String netSalary = String.valueOf(Math.round(taxCalculator.calculateNetValue()));

        model.addAttribute("textValue", "Your net: " + netSalary);
        model.addAttribute("BasicTax", String.valueOf(Math.round(taxCalculator.calculateBasictax())));
        model.addAttribute("BandTax", String.valueOf(Math.round(taxCalculator.calculateBandtax())));
        model.addAttribute("HealthContribution", String.valueOf(Math.round(taxCalculator.calculateHealthContribution())));
        return "calculate";
    }
}
