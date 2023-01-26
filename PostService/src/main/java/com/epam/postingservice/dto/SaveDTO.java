package com.epam.postingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveDTO {
    private Long authorId;
    private String text;
}
