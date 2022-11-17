package com.example.manage_requests.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RequestDto {

    private long id;
    @NotEmpty
    private String text;
    @NotNull
    private long modifiedDate;
    private long length;
}
