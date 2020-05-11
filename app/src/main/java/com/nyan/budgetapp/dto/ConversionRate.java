package com.nyan.budgetapp.dto;

import java.util.Map;

public class ConversionRate {
    private boolean success;
    private String terms;
    private String privacy;
    private long timestamp;
    private String source;
    private Map<String, Double> quotes;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean value) {
        this.success = value;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String value) {
        this.terms = value;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String value) {
        this.privacy = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long value) {
        this.timestamp = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, Double> value) {
        this.quotes = value;
    }
}
