package com.ryzendee.cardservice.repository;

import com.ryzendee.cardservice.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardJpaRepository extends JpaRepository<CardEntity, UUID> {
}
