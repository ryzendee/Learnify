package com.ryzendee.cardservice.service;

import com.ryzendee.cardservice.dto.request.CardCreateRequest;
import com.ryzendee.cardservice.dto.request.CardUpdateRequest;
import com.ryzendee.cardservice.dto.response.CardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CardService {

    CardResponse createCard(CardCreateRequest request);
    CardResponse updateCardById(UUID id, CardUpdateRequest request);
    CardResponse getCardById(UUID id);
    void deleteCardById(UUID id);
    Page<CardResponse> getAllCards(Pageable pageable);
}
