package com.bbolab.gaonna.core.domain.region;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class RegionL2 {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private RegionL3 regionL3;

    @OneToMany(mappedBy = "regionL2")
    private List<RegionL1> regionL1s;

    private String admCode;

    private String name;

    private String version;
}
