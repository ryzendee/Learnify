package com.ryzendee.cardservice.testutils.builder.card;

import com.ryzendee.cardservice.dto.response.CardResponse;
import com.ryzendee.cardservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CardResponseTestBuilder implements TestBaseBuilder<CardResponse> {

    private UUID id = null;
    private String term = "responseTerm";
    private String definition = "responseDefinition";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Override
    public CardResponse build() {
        return new CardResponse(
                id,
                term,
                definition,
                createdAt,
                updatedAt
        );
    }
}
