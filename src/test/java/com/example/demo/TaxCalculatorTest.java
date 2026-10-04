package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxCalculatorTest {

    @Test
    public void calculateBandtax_underBAND_TAX_LIMIT_1() {
        TaxCalculator taxCalculator = new TaxCalculator(226099);

        double result = taxCalculator.calculateBandtax();

        assertEquals(0, result);
    }

    @Test
    public void calculateBandtax_between_LIMIT1_and_LIMIT2() {
        TaxCalculator taxCalculator = new TaxCalculator(300000);

        double result = taxCalculator.calculateBandtax();

        assertEquals(300000 * 0.017, result);

    }

    @Test
    public void calculateBandtax_between_LIMIT2_and_LIMIT3() {
        TaxCalculator taxCalculator = new TaxCalculator(500000);

        double result = taxCalculator.calculateBandtax();

        assertEquals(500000 * 0.04, result);
    }

    @Test
    public void calculateBandtax_between_LIMIT3_and_LIMIT4() {
        TaxCalculator taxCalculator = new TaxCalculator(800000);

        double result = taxCalculator.calculateBandtax();

        assertEquals(800000 * 0.137, result);
    }

    @Test
    public void calculateBandtax_between_LIMIT4_and_LIMIT5() {
        TaxCalculator taxCalculator = new TaxCalculator(1000000);

        double result = taxCalculator.calculateBandtax();

        assertEquals(1000000 * 0.168, result);
    }

    @Test
    public void calculateBandtax_aboveBAND_TAX_LIMIT_5() {
        TaxCalculator taxCalculator = new TaxCalculator(1500000);

        double result = taxCalculator.calculateBandtax();

        assertEquals(1500000 * 0.178, result);
    }
}
