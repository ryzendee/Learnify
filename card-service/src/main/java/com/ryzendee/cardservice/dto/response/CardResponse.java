package com.ryzendee.cardservice.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CardResponse(
        UUID id,
        String term,
        String definition,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
