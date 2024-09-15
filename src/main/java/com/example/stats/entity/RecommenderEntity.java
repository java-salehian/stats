package com.example.stats.entity;

import com.example.stats.dto.Recommender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "recommender")
public class RecommenderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private int appId;

    private String countryCode;

    private int advertiserId;

    private int impressions;

    private double revenue;

    public RecommenderEntity() {
    }

    public RecommenderEntity(Recommender recomender) {
        this.appId = recomender.getAppId();
        this.countryCode = recomender.getCountryCode();
        this.advertiserId = recomender.getAdvertiserId();
        this.impressions = recomender.getImpressions().get();
        this.revenue = recomender.getRevenue().get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(int advertiserId) {
        this.advertiserId = advertiserId;
    }

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

}
