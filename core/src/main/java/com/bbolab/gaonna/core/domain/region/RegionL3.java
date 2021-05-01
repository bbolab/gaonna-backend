package com.bbolab.gaonna.core.domain.region;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegionL3 {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @OneToMany(mappedBy = "regionL3")
    private List<RegionL2> regionL2s;

    private String admCode; // TODO : type?

    private String name;

    private String geoPolygon; // TODO : type?

    private String centerPosition;  // TODO : type?

    private String version;
}
