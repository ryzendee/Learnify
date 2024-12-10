package com.ryzendee.cardservice.controller;

import com.ryzendee.cardservice.dto.request.CardCreateRequest;
import com.ryzendee.cardservice.dto.request.CardUpdateRequest;
import com.ryzendee.cardservice.dto.response.CardResponse;
import com.ryzendee.cardservice.service.CardService;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CardResponse createCard(@Valid @RequestBody CardCreateRequest request) {
        return cardService.createCard(request);
    }

    @PutMapping("/{id}")
    public CardResponse updateById(@PathVariable UUID id,
                                   @Valid @RequestBody CardUpdateRequest request) {
        return cardService.updateCardById(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        cardService.deleteCardById(id);
    }

    @GetMapping("/{id}")
    public CardResponse getCardById(@PathVariable UUID id) {
        return cardService.getCardById(id);
    }

    @GetMapping
    public Page<CardResponse> getAllCards(Pageable pageable) {
        return cardService.getAllCards(pageable);
    }
}
