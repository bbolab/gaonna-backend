package com.bbolab.gaonna.core.domain.quest;

import com.bbolab.gaonna.core.domain.article.Article;
import com.bbolab.gaonna.core.domain.tag.QuestTag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "price"})
public class Quest {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne(mappedBy = "quest")
    private Article article;

    // TODO : Should add Categories relationship.. maybe @ManyToMany

    // TODO : Should add gpsCoordinates field with MySQL's Geometry supports.

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(updatable = false)
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Column(nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL)
    private List<MemberQuest> questMember = new LinkedList<>(); // TODO : List vs Set // 예외처리 관점에서

    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL)
    private List<QuestTag> questTags = new LinkedList<>();

    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL)
    private List<QuestRegionL3> questRegionL3s = new LinkedList<>();
}
