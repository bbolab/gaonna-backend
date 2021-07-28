package com.bbolab.gaonna.core.domain.region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn
    private RegionL2 regionL2;

    private String admCode; // TODO : type?

    private String name;

    private Polygon geoPolygon;

    private Point centerPosition;

    private String version;
}
