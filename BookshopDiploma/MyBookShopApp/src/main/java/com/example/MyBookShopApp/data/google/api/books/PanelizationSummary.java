package com.example.MyBookShopApp.data.google.api.books;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PanelizationSummary {
    boolean containsEpubBubbles;
    boolean containsImageBubbles;

    @JsonProperty("containsEpubBubbles")
    public boolean getContainsEpubBubbles() {
        return this.containsEpubBubbles;
    }

    public void setContainsEpubBubbles(boolean containsEpubBubbles) {
        this.containsEpubBubbles = containsEpubBubbles;
    }

    @JsonProperty("containsImageBubbles")
    public boolean getContainsImageBubbles() {
        return this.containsImageBubbles;
    }

    public void setContainsImageBubbles(boolean containsImageBubbles) {
        this.containsImageBubbles = containsImageBubbles;
    }
}
