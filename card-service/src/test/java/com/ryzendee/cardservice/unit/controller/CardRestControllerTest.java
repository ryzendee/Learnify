package com.ryzendee.cardservice.unit.controller;

import com.ryzendee.cardservice.controller.CardRestController;
import com.ryzendee.cardservice.dto.request.CardCreateRequest;
import com.ryzendee.cardservice.dto.request.CardUpdateRequest;
import com.ryzendee.cardservice.dto.response.CardResponse;
import com.ryzendee.cardservice.exception.CardNotFoundException;
import com.ryzendee.cardservice.service.CardService;
import com.ryzendee.cardservice.testutils.builder.card.CardCreateRequestTestBuilder;
import com.ryzendee.cardservice.testutils.builder.card.CardResponseTestBuilder;
import com.ryzendee.cardservice.testutils.builder.card.CardUpdateRequestTestBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(CardRestController.class)
public class CardRestControllerTest {

    private static final String BASE_URI = "/api/v1/cards";
    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";

    private static final int MAX_TERM_LENGTH = 255;

    @MockBean
    private CardService cardService;
    
    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification restAssuredRequest;
    private UUID id;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
        restAssuredRequest = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON);

        id = UUID.randomUUID();
    }

    @Test
    void createCard_validRequest_shouldReturnStatusCreated() {
        var createRequest = CardCreateRequestTestBuilder.builder().build();

        var expectedCardResponse = CardResponseTestBuilder.builder().build();
        when(cardService.createCard(createRequest)).thenReturn(expectedCardResponse);

        restAssuredRequest
                .body(createRequest)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

        verify(cardService).createCard(createRequest);
    }

    @MethodSource("getInvalidTermCases")
    @ParameterizedTest
    void createCard_invalidTerm_shouldReturnStatusBadRequest(String term) {
        var createRequest = CardCreateRequestTestBuilder.builder()
                .withTerm(term)
                .build();

        restAssuredRequest
                .body(createRequest)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        verify(cardService, never()).createCard(any(CardCreateRequest.class));
    }

    @MethodSource("getInvalidDefinitionCases")
    @ParameterizedTest
    void createCard_invalidDefinition_shouldReturnStatusBadRequest(String definition) {
        var createRequest = CardCreateRequestTestBuilder.builder()
                .withDefinition(definition)
                .build();

        restAssuredRequest
                .body(createRequest)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        verify(cardService, never()).createCard(any(CardCreateRequest.class));
    }

    @Test
    void updateCard_validRequest_shouldReturnStatusOk() {
        var updateRequest = CardUpdateRequestTestBuilder.builder().build();

        var expectedCardResponse = CardResponseTestBuilder.builder().build();
        when(cardService.updateCardById(id, updateRequest)).thenReturn(expectedCardResponse);

        restAssuredRequest
                .body(updateRequest)
                .when()
                .put("/{id}", id.toString())
                .then()
                .statusCode(HttpStatus.OK.value());

        verify(cardService).updateCardById(id, updateRequest);
    }

    @Test
    void updateCard_nonExistsCard_shouldReturnStatusNotFound() {
        var updateRequest = CardUpdateRequestTestBuilder.builder().build();

        doThrow(new CardNotFoundException())
                .when(cardService).updateCardById(id, updateRequest);

        restAssuredRequest
                .body(updateRequest)
                .when()
                .put("/{id}", id.toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        verify(cardService).updateCardById(id, updateRequest);
    }

    @MethodSource("getInvalidTermCases")
    @ParameterizedTest
    void updateCard_invalidTerm_shouldReturnStatusBadRequest(String term) {
        var updateRequest = CardUpdateRequestTestBuilder.builder()
                .withTerm(term)
                .build();

        restAssuredRequest
                .body(updateRequest)
                .when()
                .put("/{id}", id.toString())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        verify(cardService, never()).updateCardById(any(UUID.class), any(CardUpdateRequest.class));
    }

    @MethodSource("getInvalidDefinitionCases")
    @ParameterizedTest
    void updateCard_invalidDefinition_shouldReturnStatusBadRequest(String definition) {
        var updateRequest = CardUpdateRequestTestBuilder.builder()
                .withDefinition(definition)
                .build();

        restAssuredRequest
                .body(updateRequest)
                .when()
                .put("/{id}", id.toString())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        verify(cardService, never()).updateCardById(any(UUID.class), any(CardUpdateRequest.class));
    }

    @Test
    void deleteCard_existsCard_shouldReturnStatusNoContent() {
        restAssuredRequest
                .when()
                .delete("/{id}", id.toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        verify(cardService).deleteCardById(id);
    }

    @Test
    void deleteCard_nonExistsCard_shouldReturnStatusNotFound() {
        doThrow(new CardNotFoundException())
                .when(cardService).deleteCardById(id);

        restAssuredRequest
                .when()
                .delete("/{id}", id.toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        verify(cardService).deleteCardById(id);
    }

    @Test
    void getCardById_existsCard_shouldReturnStatusOk() {
        var expectedCardResponse = CardResponseTestBuilder.builder().build();
        when(cardService.getCardById(id)).thenReturn(expectedCardResponse);

        restAssuredRequest
                .when()
                .get("/{id}", id.toString())
                .then()
                .statusCode(HttpStatus.OK.value());

        verify(cardService).getCardById(id);
    }

    @Test
    void getCardById_nonExistsCard_shouldReturnStatusNotFound() {
        doThrow(new CardNotFoundException())
                .when(cardService).getCardById(id);

        restAssuredRequest
                .when()
                .get("/{id}", id.toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        verify(cardService).getCardById(id);
    }

    @Test
    void getAllCards_validRequestParams_shouldReturnCards() {
        var cardResponsePage = new PageImpl<>(List.of(CardResponseTestBuilder.builder().build()));
        var pageable = PageRequest.of(0, 10);

        when(cardService.getAllCards(pageable)).thenReturn(cardResponsePage);

        restAssuredRequest
                .queryParam(PAGE_PARAM, 0)
                .queryParam(SIZE_PARAM, 10)
                .when()
                .get()
                .then()
                .status(HttpStatus.OK);

        verify(cardService).getAllCards(pageable);
    }

    private static Stream<Arguments> getInvalidTermCases() {
        return Stream.of(
                arguments(named("Term is blank", "       ")),
                arguments(named("Term is null", null)),
                arguments(named("Term is too long", generateStringWithLength(MAX_TERM_LENGTH + 1)))
        );
    }

    private static Stream<Arguments> getInvalidDefinitionCases() {
        return Stream.of(
                arguments(named("Definition is blank", "      ")),
                arguments(named("Definition is null", null))
        );
    }

    private static String generateStringWithLength(int length) {
        return RandomStringUtils.random(length);
    }
}
