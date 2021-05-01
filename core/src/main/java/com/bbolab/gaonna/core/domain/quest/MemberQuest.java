package com.bbolab.gaonna.core.domain.quest;

import com.bbolab.gaonna.core.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id") // TODO : Side effect?
public class MemberQuest {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private Member member;

    @ManyToOne
    @JoinColumn
    private Quest quest;

    @Enumerated(EnumType.STRING)
    private QuestRoleType roleType;

    // TODO : equals & hashCode


}
