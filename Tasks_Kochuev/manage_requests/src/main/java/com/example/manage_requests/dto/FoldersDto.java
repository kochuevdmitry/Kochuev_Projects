package com.example.manage_requests.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FoldersDto {
    @ApiModelProperty(notes = "folder id", example = "1")
    private long id;
    @ApiModelProperty(notes = "folder name", required = true, example = "folder_name")
    private String folderName;
}
