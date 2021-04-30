package com.bbolab.gaonna.core.domain.quest;

import com.bbolab.gaonna.core.domain.region.RegionL3;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class QuestRegionL3 {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private Quest quest;

    @ManyToOne
    @JoinColumn
    private RegionL3 regionL3;
}
