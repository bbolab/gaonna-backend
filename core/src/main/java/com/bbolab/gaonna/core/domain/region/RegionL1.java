package com.bbolab.gaonna.core.domain.region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegionL1 {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @Builder.Default
    @OneToMany(mappedBy = "regionL1")
    private List<RegionL2> regionL2s = new LinkedList<>();

    private String admCode;

    private String name;

    private String version;

    public void addRegionL2(RegionL2 regionL2) {
        if (!regionL2s.contains(regionL2)) {
            regionL2s.add(regionL2);
            regionL2.setRegionL1(this);
        }
    }
}
