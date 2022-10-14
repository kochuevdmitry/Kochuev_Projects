package com.example.MyBookShopApp.data.google.api.books;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pdf {
    boolean isAvailable;
    String acsTokenLink;

    @JsonProperty("isAvailable")
    public boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @JsonProperty("acsTokenLink")
    public String getAcsTokenLink() {
        return this.acsTokenLink;
    }

    public void setAcsTokenLink(String acsTokenLink) {
        this.acsTokenLink = acsTokenLink;
    }
}
