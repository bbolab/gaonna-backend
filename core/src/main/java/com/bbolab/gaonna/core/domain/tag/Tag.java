package com.bbolab.gaonna.core.domain.tag;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
