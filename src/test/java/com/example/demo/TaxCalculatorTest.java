package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxCalculatorTest {

    @Test
    public void calculateBandtax_underBAND_TAX_LIMIT_1() {

        TaxCalculator taxCalculator = new TaxCalculator(226099);
        double result = taxCalculator.calculateBandtax();
        assertEquals(0, result, 0.01);
    }

    @Test
    public void calculateBandtax_between_LIMIT1_and_LIMIT2() {

        TaxCalculator taxCalculator = new TaxCalculator(300000);
        double result = taxCalculator.calculateBandtax();
        assertEquals(1256.3, result, 0.01);
    }

    @Test
    public void calculateBandtax_between_LIMIT2_and_LIMIT3() {

        TaxCalculator taxCalculator = new TaxCalculator(500000);
        double result = taxCalculator.calculateBandtax();
        assertEquals(8835.4, result, 0.01);
    }

    @Test
    public void calculateBandtax_between_LIMIT3_and_LIMIT4() {

        TaxCalculator taxCalculator = new TaxCalculator(800000);
        double result = taxCalculator.calculateBandtax();
        assertEquals(28105.55, result, 0.01);
    }

    @Test
    public void calculateBandtax_between_LIMIT4_and_LIMIT5() {

        TaxCalculator taxCalculator = new TaxCalculator(1000000);
        double result = taxCalculator.calculateBandtax();
        assertEquals(56122.95, result, 0.5);
    }

    @Test
    public void calculateBandtax_aboveBAND_TAX_LIMIT_5() {

        TaxCalculator taxCalculator = new TaxCalculator(1500000);
        double result = taxCalculator.calculateBandtax();
        assertEquals(140850.95, result, 0.5);
    }
}
