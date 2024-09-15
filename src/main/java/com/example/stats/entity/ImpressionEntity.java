package com.example.stats.entity;

import com.example.stats.dto.Impression;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "impression")
public class ImpressionEntity {

    @Id
    private String id;

    private int appId;

    private String countryCode;

    private int advertiserId;

    private Double revenue;

    public ImpressionEntity() {
    }

    public ImpressionEntity(Impression impression) {
        this.id = impression.getId();
        this.appId = impression.getAppId();
        this.countryCode = impression.getCountryCode();
        this.advertiserId = impression.getAdvertiserId();
        this.revenue = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
