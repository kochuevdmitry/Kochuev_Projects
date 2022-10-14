package com.example.MyBookShopApp.data.google.api.books;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Offer {
    int finskyOfferType;
    ListPrice listPrice;
    RetailPrice retailPrice;

    @JsonProperty("finskyOfferType")
    public int getFinskyOfferType() {
        return this.finskyOfferType;
    }

    public void setFinskyOfferType(int finskyOfferType) {
        this.finskyOfferType = finskyOfferType;
    }

    @JsonProperty("listPrice")
    public ListPrice getListPrice() {
        return this.listPrice;
    }

    public void setListPrice(ListPrice listPrice) {
        this.listPrice = listPrice;
    }

    @JsonProperty("retailPrice")
    public RetailPrice getRetailPrice() {
        return this.retailPrice;
    }

    public void setRetailPrice(RetailPrice retailPrice) {
        this.retailPrice = retailPrice;
    }
}
