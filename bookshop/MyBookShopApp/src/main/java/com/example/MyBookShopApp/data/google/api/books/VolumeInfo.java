package com.example.MyBookShopApp.data.google.api.books;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VolumeInfo {
    String title;
    List<String> authors;
    String publisher;
    String publishedDate;
    String description;
    List<IndustryIdentifier> industryIdentifiers;
    ReadingModes readingModes;
    String printType;
    List<String> categories;
    String maturityRating;
    boolean allowAnonLogging;
    String contentVersion;
    PanelizationSummary panelizationSummary;
    ImageLinks imageLinks;
    String language;
    String previewLink;
    String infoLink;
    String canonicalVolumeLink;
    int pageCount;
    double averageRating;
    int ratingsCount;

    @JsonProperty("title")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("authors")
    public List<String> getAuthors() {
        return this.authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @JsonProperty("publisher")
    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @JsonProperty("publishedDate")
    public String getPublishedDate() {
        return this.publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @JsonProperty("description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("industryIdentifiers")
    public List<IndustryIdentifier> getIndustryIdentifiers() {
        return this.industryIdentifiers;
    }

    public void setIndustryIdentifiers(List<IndustryIdentifier> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    @JsonProperty("readingModes")
    public ReadingModes getReadingModes() {
        return this.readingModes;
    }

    public void setReadingModes(ReadingModes readingModes) {
        this.readingModes = readingModes;
    }

    @JsonProperty("printType")
    public String getPrintType() {
        return this.printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    @JsonProperty("categories")
    public List<String> getCategories() {
        return this.categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @JsonProperty("maturityRating")
    public String getMaturityRating() {
        return this.maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    @JsonProperty("allowAnonLogging")
    public boolean getAllowAnonLogging() {
        return this.allowAnonLogging;
    }

    public void setAllowAnonLogging(boolean allowAnonLogging) {
        this.allowAnonLogging = allowAnonLogging;
    }

    @JsonProperty("contentVersion")
    public String getContentVersion() {
        return this.contentVersion;
    }

    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
    }

    @JsonProperty("panelizationSummary")
    public PanelizationSummary getPanelizationSummary() {
        return this.panelizationSummary;
    }

    public void setPanelizationSummary(PanelizationSummary panelizationSummary) {
        this.panelizationSummary = panelizationSummary;
    }

    @JsonProperty("imageLinks")
    public ImageLinks getImageLinks() {
        return this.imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("previewLink")
    public String getPreviewLink() {
        return this.previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    @JsonProperty("infoLink")
    public String getInfoLink() {
        return this.infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    @JsonProperty("canonicalVolumeLink")
    public String getCanonicalVolumeLink() {
        return this.canonicalVolumeLink;
    }

    public void setCanonicalVolumeLink(String canonicalVolumeLink) {
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    @JsonProperty("pageCount")
    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @JsonProperty("averageRating")
    public double getAverageRating() {
        return this.averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @JsonProperty("ratingsCount")
    public int getRatingsCount() {
        return this.ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }
}
