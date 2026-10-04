package com.example.demo;

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
    }

    public double calculateNetValue() {
        return grossValue - calculateBasictax() - calculateBandtax() - calculateHealthContribution();
    }

    private double calculateMinstefradrag() {
        double discount = grossValue * MINSTEFRADRAG_RATE;

        if(discount >= MINSTEFRADRAG_RATE) {
            return MINSTEFRADRAG_RATE;
        } else {
            return discount;
        }
    }

    private double calculatePersonfradrag() {
        return PERSONFRADRAG;

    }

    private double calculateTaxbase() {
        double taxBase = grossValue - calculateMinstefradrag() - calculatePersonfradrag();
        if (taxBase > 0) {
            return taxBase;
        }
        else {
            return 0;
        }

    }

    public double calculateBasictax() {
        return calculateTaxbase() * TAXBASE;

    }

    public double calculateBandtax() {
        if(grossValue <= BAND_TAX_LIMIT_1) {
            return 0;
        } else if (grossValue > BAND_TAX_LIMIT_1 && grossValue <= BAND_TAX_LIMIT_2) {
            return grossValue * BAND_TAX_RATE_1;
        } else if (grossValue > BAND_TAX_LIMIT_2 && grossValue <= BAND_TAX_LIMIT_3) {
            return grossValue * BAND_TAX_RATE_2;
        } else if (grossValue > BAND_TAX_LIMIT_3 && grossValue <= BAND_TAX_LIMIT_4) {
            return grossValue * BAND_TAX_RATE_3;
        } else if (grossValue > BAND_TAX_LIMIT_4 && grossValue <= BAND_TAX_LIMIT_5) {
            return grossValue * BAND_TAX_RATE_4;
        } else {
            return grossValue * BAND_TAX_RATE_5;
        }
    }
    public double calculateHealthContribution() {
        return grossValue * HEALTH_CONTRIBUTION;

    }

}
