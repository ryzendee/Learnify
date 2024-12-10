package com.ryzendee.cardservice.testutils.builder.card;

import com.ryzendee.cardservice.entity.CardEntity;
import com.ryzendee.cardservice.testutils.builder.TestBaseBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.convert.Jsr310Converters;

import java.time.LocalDateTime;


@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CardTestEntityBuilder implements TestBaseBuilder<CardEntity> {

    private String term = "entityTerm";
    private String definition = "entityDefinition";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Override
    public CardEntity build() {
        return CardEntity.builder()
                .term(term)
                .definition(definition)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
