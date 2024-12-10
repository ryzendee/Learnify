package com.ryzendee.cardservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CardCreateRequest(

        @NotBlank(message = "Term must not be blank")
        @Size(min = 1, max = 255, message = "Term's length must be between {min} and {max} characters")
        String term,

        @NotBlank(message = "Definition must not be blank")
        @Size(min = 1, message = "Definition's length must be at least {min} characters")
        String definition
)  {
}
