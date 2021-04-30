package com.bbolab.gaonna.core.domain.region;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegionL1 {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private RegionL2 regionL2;

    private String admCode;

    private String name;

    private String version;
}
