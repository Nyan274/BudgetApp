package com.nyan.budgetapp.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CurrencyUtils {
    public static String parse(double value) {
        return Double.toString(value);
    }

    public static double parse(String value) {
        return Double.parseDouble(value);
    }

    public static String format(double value) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(value);
    }

    public static String format(double value, String currencyISO) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return currencyISO + formatter.format(value);
    }
}
