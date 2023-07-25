package com.mkrasikoff.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveDTO {
    private Long authorId;
    private String text;
}
