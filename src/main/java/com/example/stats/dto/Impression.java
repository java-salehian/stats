package com.example.stats.dto;

public class Impression {

    private String id;

    private int appId;

    private String countryCode;

    private int advertiserId;

    public Impression() {
    }

    public Impression(String id, int appId, String countryCode, int advertiserId) {
        this.id = id;
        this.appId = appId;
        this.countryCode = countryCode;
        this.advertiserId = advertiserId;
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
}
