package com.ryzendee.cardservice.integration.service;

import com.ryzendee.cardservice.entity.CardEntity;
import com.ryzendee.cardservice.exception.CardNotFoundException;
import com.ryzendee.cardservice.service.CardService;
import com.ryzendee.cardservice.testutils.base.BaseTestContainers;
import com.ryzendee.cardservice.testutils.builder.card.CardCreateRequestTestBuilder;
import com.ryzendee.cardservice.testutils.builder.card.CardTestEntityBuilder;
import com.ryzendee.cardservice.testutils.builder.card.CardUpdateRequestTestBuilder;
import com.ryzendee.cardservice.testutils.config.TestConfig;
import com.ryzendee.cardservice.testutils.facade.TestDatabaseFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@Import(TestConfig.class)
@SpringBootTest
public class CardServiceIT extends BaseTestContainers {

    @Autowired
    private CardService cardService;

    @Autowired
    private TestDatabaseFacade databaseFacade;

    private CardEntity preparedCardEntity;

    @BeforeAll
    static void startupContainers() {
        POSTGRE_SQL_CONTAINER.start();
    }

    @BeforeEach
    void prepareDatabaseData() {
        cleanDatabase();
        preparedCardEntity = insertCardEntity();
    }

    @Test
    void createCard_noCondition_shouldSaveCard() {
        var cardCreateRequest = CardCreateRequestTestBuilder.builder().build();

        var cardResponse = cardService.createCard(cardCreateRequest);

        var savedEntity = databaseFacade.find(cardResponse.id(), CardEntity.class);
        assertThat(savedEntity.getTerm()).isEqualTo(cardCreateRequest.term());
        assertThat(savedEntity.getDefinition()).isEqualTo(cardCreateRequest.definition());
    }

    @Test
    void updateCardById_withCardInDatabase_shouldUpdateCard() {
        var updateRequest = CardUpdateRequestTestBuilder.builder().build();

        cardService.updateCardById(preparedCardEntity.getId(), updateRequest);

        var updatedEntity = databaseFacade.find(preparedCardEntity.getId(), CardEntity.class);
        assertThat(updatedEntity.getTerm()).isEqualTo(updateRequest.term());
        assertThat(updatedEntity.getDefinition()).isEqualTo(updateRequest.definition());
    }

    @Test
    void updateCardById_withoutCardInDatabase_shouldUpdateCard() {
        var id = UUID.randomUUID();
        var updateRequest = CardUpdateRequestTestBuilder.builder().build();

        assertThatThrownBy(() -> cardService.updateCardById(id, updateRequest))
                .isInstanceOf(CardNotFoundException.class)
                .message().isNotBlank();
    }

    @Test
    void getCardById_withCardInDatabase_shouldReturnCard() {
        var entity = databaseFacade.find(preparedCardEntity.getId(), CardEntity.class);
        assertThat(entity).isNotNull();
    }

    @Test
    void getCardById_withoutCardInDatabase_shouldReturnCard() {
        assertThatThrownBy(() -> cardService.getCardById(UUID.randomUUID()))
                .isInstanceOf(CardNotFoundException.class)
                .message().isNotBlank();
    }

    @Test
    void deleteCard_withCardInDatabase_shouldDeleteCard() {
        cardService.deleteCardById(preparedCardEntity.getId());

        var deletedEntity = databaseFacade.find(preparedCardEntity.getId(), CardEntity.class);
        assertThat(deletedEntity).isNull();
    }

    @Test
    void deleteCard_withoutCardInDatabase_shouldThrowCardNotFoundEx() {
        assertThatThrownBy(() -> cardService.deleteCardById(UUID.randomUUID()))
                .isInstanceOf(CardNotFoundException.class)
                .message().isNotBlank();
    }

    @Test
    void getAllCards_withCardInDatabase_shouldReturnCardPage() {
        var pageable = PageRequest.of(0, 10);

        var cardResponsePage = cardService.getAllCards(pageable);
        assertThat(cardResponsePage).isNotEmpty();
    }

    @Test
    void getAllCards_sortByUnusedField_shouldThrowPropertyReferenceEx() {
        var pageable = PageRequest.of(0, 10, Sort.by("dummy"));

        assertThatThrownBy(() -> cardService.getAllCards(pageable))
                .isInstanceOf(PropertyReferenceException.class);
    }

    private CardEntity insertCardEntity() {
        return databaseFacade.save(CardTestEntityBuilder.builder());
    }

    private void cleanDatabase() {
        databaseFacade.cleanDatabase();
    }
}
