package com.example.MyBookShopApp.data.google.api.books;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessInfo {
    String country;
    String viewability;
    boolean embeddable;
    boolean publicDomain;
    String textToSpeechPermission;
    Epub epub;
    Pdf pdf;
    String webReaderLink;
    String accessViewStatus;
    boolean quoteSharingAllowed;

    @JsonProperty("country")
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("viewability")
    public String getViewability() {
        return this.viewability;
    }

    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    @JsonProperty("embeddable")
    public boolean getEmbeddable() {
        return this.embeddable;
    }

    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

    @JsonProperty("publicDomain")
    public boolean getPublicDomain() {
        return this.publicDomain;
    }

    public void setPublicDomain(boolean publicDomain) {
        this.publicDomain = publicDomain;
    }

    @JsonProperty("textToSpeechPermission")
    public String getTextToSpeechPermission() {
        return this.textToSpeechPermission;
    }

    public void setTextToSpeechPermission(String textToSpeechPermission) {
        this.textToSpeechPermission = textToSpeechPermission;
    }

    @JsonProperty("epub")
    public Epub getEpub() {
        return this.epub;
    }

    public void setEpub(Epub epub) {
        this.epub = epub;
    }

    @JsonProperty("pdf")
    public Pdf getPdf() {
        return this.pdf;
    }

    public void setPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    @JsonProperty("webReaderLink")
    public String getWebReaderLink() {
        return this.webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    @JsonProperty("accessViewStatus")
    public String getAccessViewStatus() {
        return this.accessViewStatus;
    }

    public void setAccessViewStatus(String accessViewStatus) {
        this.accessViewStatus = accessViewStatus;
    }

    @JsonProperty("quoteSharingAllowed")
    public boolean getQuoteSharingAllowed() {
        return this.quoteSharingAllowed;
    }

    public void setQuoteSharingAllowed(boolean quoteSharingAllowed) {
        this.quoteSharingAllowed = quoteSharingAllowed;
    }
}
