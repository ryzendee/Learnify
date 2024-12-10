package com.ryzendee.cardservice.mapper;

import com.ryzendee.cardservice.dto.request.CardCreateRequest;
import com.ryzendee.cardservice.dto.request.CardUpdateRequest;
import com.ryzendee.cardservice.dto.response.CardResponse;
import com.ryzendee.cardservice.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {

    CardEntity mapToEntity(CardCreateRequest request);
    CardEntity mapToEntity(CardUpdateRequest request);
    CardResponse mapToResponse(CardEntity entity);
}
