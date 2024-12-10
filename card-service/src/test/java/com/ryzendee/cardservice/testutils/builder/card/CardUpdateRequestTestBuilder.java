package com.ryzendee.cardservice.testutils.builder.card;

import com.ryzendee.cardservice.dto.request.CardUpdateRequest;
import com.ryzendee.cardservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CardUpdateRequestTestBuilder implements TestBaseBuilder<CardUpdateRequest> {

    private String term = "cardUpdateRequestTerm";
    private String definition = "cadUpdateRequestDefinition";

    @Override
    public CardUpdateRequest build() {
        return new CardUpdateRequest(term, definition);
    }
}
