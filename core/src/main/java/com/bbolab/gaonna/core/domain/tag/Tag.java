package com.bbolab.gaonna.core.domain.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class Tag {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;
}
