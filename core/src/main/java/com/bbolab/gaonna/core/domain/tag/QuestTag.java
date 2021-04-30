package com.bbolab.gaonna.core.domain.tag;

import com.bbolab.gaonna.core.domain.quest.Quest;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class QuestTag {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private Quest quest;

    @ManyToOne
    @JoinColumn
    private Tag tag;
}
