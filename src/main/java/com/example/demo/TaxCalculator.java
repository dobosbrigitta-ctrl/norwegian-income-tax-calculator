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

        return Math.min(discount, MINSTEFRADRAG_MAX);
    }

    private double calculatePersonfradrag() {
        return PERSONFRADRAG;
    }

    private double calculateTaxbase() {
        double taxBase = grossValue - calculateMinstefradrag() - calculatePersonfradrag();
        return Math.max(taxBase, 0);
    }

    public double calculateBasictax() {
        return calculateTaxbase() * TAXBASE;
    }


    public double calculateBandtax() {
        double totalBandTax = 0;


        if (grossValue > BAND_TAX_LIMIT_1) {
            double taxableInThisBand = Math.min(grossValue, BAND_TAX_LIMIT_2) - BAND_TAX_LIMIT_1;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_1;
        }


        if (grossValue > BAND_TAX_LIMIT_2) {
            double taxableInThisBand = Math.min(grossValue, BAND_TAX_LIMIT_3) - BAND_TAX_LIMIT_2;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_2;
        }


        if (grossValue > BAND_TAX_LIMIT_3) {
            double taxableInThisBand = Math.min(grossValue, BAND_TAX_LIMIT_4) - BAND_TAX_LIMIT_3;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_3;
        }


        if (grossValue > BAND_TAX_LIMIT_4) {
            double taxableInThisBand = Math.min(grossValue, BAND_TAX_LIMIT_5) - BAND_TAX_LIMIT_4;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_4;
        }

       
        if (grossValue > BAND_TAX_LIMIT_5) {
            double taxableInThisBand = grossValue - BAND_TAX_LIMIT_5;
            totalBandTax += taxableInThisBand * BAND_TAX_RATE_5;
        }

        return totalBandTax;
    }

    public double calculateHealthContribution() {
        return grossValue * HEALTH_CONTRIBUTION;
    }
}
