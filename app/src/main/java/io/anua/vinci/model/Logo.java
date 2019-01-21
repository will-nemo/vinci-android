package io.anua.vinci.model;

import com.google.gson.annotations.SerializedName;

public class Logo {

    /**************************
     * Private Members
     *************************/

    @SerializedName("url")
    private String logoURL;

    /**************************
     * Public Methods
     *************************/

    public String getLogoURL() {
        return logoURL;
    }
}
