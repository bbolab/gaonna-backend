package com.bbolab.gaonna.core.repository;

import com.bbolab.gaonna.core.domain.quest.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface QuestRepository extends JpaRepository<Quest, UUID> {
}
