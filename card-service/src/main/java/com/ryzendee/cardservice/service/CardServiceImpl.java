package com.ryzendee.cardservice.service;

import com.ryzendee.cardservice.dto.request.CardCreateRequest;
import com.ryzendee.cardservice.dto.request.CardUpdateRequest;
import com.ryzendee.cardservice.dto.response.CardResponse;
import com.ryzendee.cardservice.entity.CardEntity;
import com.ryzendee.cardservice.exception.CardNotFoundException;
import com.ryzendee.cardservice.mapper.CardMapper;
import com.ryzendee.cardservice.repository.CardJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardJpaRepository cardJpaRepository;
    private final CardMapper cardMapper;

    @Transactional
    @Override
    public CardResponse createCard(CardCreateRequest request) {
        CardEntity entity = cardMapper.mapToEntity(request);
        cardJpaRepository.save(entity);

        return cardMapper.mapToResponse(entity);
    }

    @Transactional
    @Override
    public CardResponse updateCardById(UUID id, CardUpdateRequest request) {
        CardEntity entityToUpdate = getCardEntityById(id);

        CardEntity updatedEntity = cardMapper.mapToEntity(request);
        updatedEntity.setId(entityToUpdate.getId());
        cardJpaRepository.save(updatedEntity);

        return cardMapper.mapToResponse(updatedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public CardResponse getCardById(UUID id) {
        CardEntity entity = getCardEntityById(id);
        return cardMapper.mapToResponse(entity);
    }

    @Transactional
    @Override
    public void deleteCardById(UUID id) {
        CardEntity entity = getCardEntityById(id);
        cardJpaRepository.delete(entity);
    }

    @Override
    public Page<CardResponse> getAllCards(Pageable pageable) {
        return cardJpaRepository.findAll(pageable)
                .map(cardMapper::mapToResponse);
    }

    private CardEntity getCardEntityById(UUID id) {
        return cardJpaRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card with given id not found, id: " + id));
    }
}
