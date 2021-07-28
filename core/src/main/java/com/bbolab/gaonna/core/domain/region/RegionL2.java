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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegionL2 {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private RegionL1 regionL1;

    @Builder.Default
    @OneToMany(mappedBy = "regionL2")
    private List<RegionL3> regionL3s = new LinkedList<>();

    private String admCode;

    private String name;

    private String version;

    public void addRegionL3(RegionL3 regionL3) {
        if (!regionL3s.contains(regionL3)) {
            regionL3s.add(regionL3);
            regionL3.setRegionL2(this);
        }
    }
}
