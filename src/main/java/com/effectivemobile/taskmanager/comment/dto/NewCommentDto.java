package com.effectivemobile.taskmanager.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewCommentDto {

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, message = "size must be between 2 and 500")
    @Size(max = 500, message = "size must be between 2 and 500")
    private final String commentText;

    private final String commentTitle;
}
