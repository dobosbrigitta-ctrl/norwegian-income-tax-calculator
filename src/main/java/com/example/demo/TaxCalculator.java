package com.example.demo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaxCalculator {

    private double grossValue;

    private static final double MINSTEFRADRAG_RATE = 0.46;
    private static final double MINSTEFRADRAG_MAX = 104450;
    private static final double PERSONFRADRAG = 114540;
    private static final double TAXBASE = 0.22;

    private static final double BAND_TAX_LIMIT_1 = 226100;
    private static final double BAND_TAX_LIMIT_2 = 318300;
    private static final double BAND_TAX_LIMIT_3 = 725050;
    private static final double BAND_TAX_LIMIT_4 = 980100;
    private static final double BAND_TAX_LIMIT_5 = 1427200;

    private static final double BAND_TAX_RATE_1 = 0.017;
    private static final double BAND_TAX_RATE_2 = 0.04;
    private static final double BAND_TAX_RATE_3 = 0.137;
    private static final double BAND_TAX_RATE_4 = 0.168;
    private static final double BAND_TAX_RATE_5 = 0.178;
    private static final double HEALTH_CONTRIBUTION = 0.076;

    public TaxCalculator(double grossValue) {
        this.grossValue = grossValue;

        log.info("[VIEW] New tax calculation initialized. Submitted gross value: {} NOK", grossValue);

        if (grossValue <= 0) {
            log.warn("[NOK_VAL_ERROR] Invalid, negative or zero value provided in NOK: {}", grossValue);
        } else if (grossValue > 2000000) {
            log.info("[HIGH_INCOME] Checking high-income bracket (2M+ NOK): {} NOK", grossValue);
        }
    }

    public double calculateNetValue() {
        double netValue = grossValue - calculateBasictax() - calculateBandtax() - calculateHealthContribution();
        log.info("[SUCCESS] Successful net calculation. Gross: {} NOK -> Net: {} NOK", grossValue, netValue);
        return netValue;
    }

    private double calculateMinstefradrag() {
        double discount = grossValue * MINSTEFRADRAG_RATE;
        double finalMinstefradrag = Math.min(discount, MINSTEFRADRAG_MAX);

        if (discount >= MINSTEFRADRAG_MAX) {
            log.debug("[MINSTEFRADRAG] Maximum deduction limit reached ({} NOK)", MINSTEFRADRAG_MAX);
        }
        return finalMinstefradrag;
    }

    private double calculatePersonfradrag() {
        return PERSONFRADRAG;
    }

    private double calculateTaxbase() {
        double taxBase = grossValue - calculateMinstefradrag() - calculatePersonfradrag();

        if (taxBase <= 0) {
            log.info("[TAX_BASE_ZERO] After deductions, the tax base dropped to 0 NOK for gross value: {}", grossValue);
        }
        return Math.max(taxBase, 0);
    }

    public double calculateBasictax() {
        return calculateTaxbase() * TAXBASE;
    }

    public double calculateBandtax() {
        double totalBandTax = 0;

        if (grossValue > BAND_TAX_LIMIT_1) {
            log.info("[BAND_1_ACTIVE] Income exceeded bracket 1 threshold ({} NOK)", BAND_TAX_LIMIT_1);
            double taxableInThisBand = Math.min(grossValue, BAND_TAX_LIMIT_2) - BAND_TAX_LIMIT_1;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_1;
        } else {
            log.debug("[BAND_1_INACTIVE] Bracket 1 is inactive.");
        }

        if (grossValue > BAND_TAX_LIMIT_2) {
            log.info("[BAND_2_ACTIVE] Income exceeded bracket 2 threshold ({} NOK)", BAND_TAX_LIMIT_2);
            double taxableInThisBand = Math.min(grossValue, BAND_TAX_LIMIT_3) - BAND_TAX_LIMIT_2;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_2;
        }

        if (grossValue > BAND_TAX_LIMIT_3) {
            log.info("[BAND_3_ACTIVE] Income exceeded bracket 3 threshold ({} NOK)", BAND_TAX_LIMIT_3);
            double taxableInThisBand = Math.min(grossValue, BAND_TAX_LIMIT_4) - BAND_TAX_LIMIT_3;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_3;
        }

        if (grossValue > BAND_TAX_LIMIT_4) {
            log.info("[BAND_4_ACTIVE] Income exceeded bracket 4 threshold ({} NOK)", BAND_TAX_LIMIT_4);
            double taxableInThisBand = Math.min(grossValue, BAND_TAX_LIMIT_5) - BAND_TAX_LIMIT_4;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_4;
        }

        if (grossValue > BAND_TAX_LIMIT_5) {
            log.info("[BAND_5_ACTIVE] Top tax bracket is active (Above {} NOK)", BAND_TAX_LIMIT_5);
            double taxableInThisBand = grossValue - BAND_TAX_LIMIT_5;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_5;
        }

        return totalBandTax;
    }

    public double calculateHealthContribution() {
        return grossValue * HEALTH_CONTRIBUTION;
    }
}

