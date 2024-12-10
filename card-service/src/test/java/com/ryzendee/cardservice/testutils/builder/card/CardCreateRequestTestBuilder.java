package com.ryzendee.cardservice.testutils.builder.card;

import com.ryzendee.cardservice.dto.request.CardCreateRequest;
import com.ryzendee.cardservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CardCreateRequestTestBuilder implements TestBaseBuilder<CardCreateRequest> {

    private String term = "cardCreateRequestTerm";
    private String definition = "cardCreateRequestDefinition";

    @Override
    public CardCreateRequest build() {
        return new CardCreateRequest(term, definition);
    }
}
