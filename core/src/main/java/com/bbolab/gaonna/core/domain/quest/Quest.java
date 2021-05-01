package com.bbolab.gaonna.core.domain.quest;

import com.bbolab.gaonna.core.domain.article.Article;
import com.bbolab.gaonna.core.domain.tag.QuestTag;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
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

    // TODO : List vs Set // 예외처리 관점에서
    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL)
    private List<MemberQuest> memberQuest = new LinkedList<>();

    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL)
    private List<QuestTag> questTags = new LinkedList<>();

    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL)
    private List<QuestRegionL3> questRegionL3s = new LinkedList<>();

    public boolean addMemberQuest(MemberQuest memberQuest) {
        if(this.memberQuest.contains(memberQuest)){
            return false;
        }
        memberQuest.setQuest(this);
        return this.memberQuest.add(memberQuest);
    }

    public boolean addQuestTag(QuestTag questTag) {
        if (this.questTags.contains(questTag)) {
            return false;
        }
        questTag.setQuest(this);
        return this.questTags.add(questTag);
    }

    public boolean addQuestRegionL3(QuestRegionL3 regionL3) {
        if (this.questRegionL3s.contains(regionL3)) {
            return false;
        }
        regionL3.setQuest(this);
        return this.questRegionL3s.add(regionL3);
    }

}
