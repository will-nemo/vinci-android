package io.anua.vinci.model;

import com.google.gson.annotations.SerializedName;

public class Quote {

    /**************************
     * Private Members
     *************************/

    @SerializedName("symbol")
    private String stockSymbol;

    @SerializedName("companyName")
    private String companyName;

    @SerializedName("primaryExchange")
    private String primaryExchange;

    @SerializedName("open")
    private Float openValue;

    @SerializedName("openTime")
    private Long openTime;

    @SerializedName("close")
    private Float closeValue;

    @SerializedName("closeTime")
    private Long closeTime;

    @SerializedName("high")
    private Float highValue;

    @SerializedName("low")
    private Float lowValue;

    @SerializedName("iexRealtimePrice")
    private Float realtimePrice;

    @SerializedName("latestPrice")
    private Float latestValue;

    @SerializedName("latestTime")
    private String latestTime;

    @SerializedName("marketCap")
    private Long marketCapValue;

    @SerializedName("change")
    private Double changeValue;

    /**************************
     * Public Methods
     *************************/

    public String getStockSymbol() {
        return stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPrimaryExchange() {
        return primaryExchange;
    }

    public Float getOpenValue() {
        return openValue;
    }

    public Long getOpenTime() {
        return openTime;
    }

    public Float getCloseValue() {
        return closeValue;
    }

    public Long getCloseTime() {
        return closeTime;
    }

    public Float getHighValue() {
        return highValue;
    }

    public Float getLowValue() {
        return lowValue;
    }

    public Float getLatestValue() {
        return latestValue;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public Float getRealtimePrice() { return realtimePrice; }

    public Long getMarketCapValue() { return marketCapValue; }

    public Double getChangeValue() { return changeValue; }

    /**************************
     * Constructor
     *************************/

    public Quote() {
    }
}
