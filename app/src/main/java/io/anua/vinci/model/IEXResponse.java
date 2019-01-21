package io.anua.vinci.model;

import com.google.gson.annotations.SerializedName;

public class IEXResponse {

    /**************************
     * Private Members
     *************************/

    @SerializedName("quote")
    private Quote quote;

    @SerializedName("logo")
    private Logo logo;

    /**************************
     * Public Methods
     *************************/

    public Quote getQuote() {
        return quote;
    }

    public Logo getLogo() {
        return logo;
    }
}
