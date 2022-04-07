package com.bbolab.gaonna.core.repository;

import com.bbolab.gaonna.core.domain.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, UUID> {

}
