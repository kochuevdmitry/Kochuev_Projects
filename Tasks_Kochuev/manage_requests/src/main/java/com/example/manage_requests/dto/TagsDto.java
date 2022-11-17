package com.example.manage_requests.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TagsDto {
    private long id;
    @JsonProperty("tag_name")
    private String tagName;
}
