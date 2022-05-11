package com.example.MyBookShopApp.data.dto;

import java.util.Date;

public class RecentDatesDto {
    Date recentFrom;
    Date recentTo;

    public RecentDatesDto() {
    }

    public RecentDatesDto(Date recentFrom, Date recentTo) {
        this.recentFrom = recentFrom;
        this.recentTo = recentTo;
    }

    public Date getRecentFrom() {
        return recentFrom;
    }

    public void setRecentFrom(Date recentFrom) {
        this.recentFrom = recentFrom;
    }

    public Date getRecentTo() {
        return recentTo;
    }

    public void setRecentTo(Date recentTo) {
        this.recentTo = recentTo;
    }
}
