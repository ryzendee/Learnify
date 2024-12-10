package com.ryzendee.cardservice.controller;

import com.ryzendee.cardservice.dto.request.CardCreateRequest;
import com.ryzendee.cardservice.dto.request.CardUpdateRequest;
import com.ryzendee.cardservice.dto.response.CardResponse;
import com.ryzendee.cardservice.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardRestController {

    private final CardService cardService;

    @Operation(summary = "Create a new card",
            description = "Creates a new card based on the provided request data.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Card created successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid request data.")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CardResponse createCard(@Valid @RequestBody CardCreateRequest request) {
        return cardService.createCard(request);
    }


    @Operation(summary = "Update a card by ID",
            description = "Updates an existing card identified by its unique ID using the provided request data.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the card to update.", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Card updated successfully."),
                    @ApiResponse(responseCode = "404", description = "Card not found for the given ID."),
                    @ApiResponse(responseCode = "400", description = "Invalid request data.")
            })
    @PutMapping("/{id}")
    public CardResponse updateById(@PathVariable UUID id,
                                   @Valid @RequestBody CardUpdateRequest request) {
        return cardService.updateCardById(id, request);
    }

    @Operation(summary = "Delete a card by ID",
            description = "Deletes a specific card identified by its unique ID.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the card to delete.", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Card deleted successfully."),
                    @ApiResponse(responseCode = "404", description = "Card not found for the given ID.")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        cardService.deleteCardById(id);
    }


    @Operation(summary = "Get a card by ID",
            description = "Retrieves a specific card based on its unique identifier.",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the card to retrieve.", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Card retrieved successfully."),
                    @ApiResponse(responseCode = "404", description = "Card not found for the given ID.")
            })
    @GetMapping("/{id}")
    public CardResponse getCardById(@PathVariable UUID id) {
        return cardService.getCardById(id);
    }

    @Operation(summary = "Get cards",
            description = "Retrieves a paginated list of cards. If no tasks exist, an empty list is returned.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cards retrieved successfully. Returns an empty list if no cards found."),
            })
    @GetMapping
    public Page<CardResponse> getAllCards(Pageable pageable) {
        return cardService.getAllCards(pageable);
    }
}
