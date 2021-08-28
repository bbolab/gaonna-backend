package com.bbolab.gaonna.core.domain.quest;

import com.bbolab.gaonna.core.domain.member.Member;
import com.bbolab.gaonna.core.domain.member.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class MemberQuestPerformer {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private Member performer;

    @OneToOne
    @JoinColumn
    private Quest quest;

    @ManyToOne
    @JoinColumn
    private Profile profile;

    @Column
    private Boolean selected; // 해당 수행 요청이 수락이 됐는지 여부
}
