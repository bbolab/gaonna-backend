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
import javax.persistence.ManyToOne;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class QuestReview {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    private Member reviewer;

    @ManyToOne
    private Profile profile;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private Double point;
}
