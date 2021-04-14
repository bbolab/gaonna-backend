package com.bbolab.gaonna.core.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class SampleEntity {
    @Id
    private UUID id;
}
