package com.example.stats.dto;

public class Click {

    private String impressionId;

    private double revenue;

    public Click() {
    }

    public Click(String impressionId, double revenue) {
        this.impressionId = impressionId;
        this.revenue = revenue;
    }

    public String getImpressionId() {
        return impressionId;
    }

    public void setImpressionId(String impressionId) {
        this.impressionId = impressionId;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
