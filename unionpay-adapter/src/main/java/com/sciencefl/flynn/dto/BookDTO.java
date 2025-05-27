package com.sciencefl.flynn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDTO {

    @NotBlank(message = "用户唯一标识不能为空")
    @JsonProperty(value = "book_id")
    private String bookId;
}
