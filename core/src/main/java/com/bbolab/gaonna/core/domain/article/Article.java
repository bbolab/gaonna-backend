package com.bbolab.gaonna.core.domain.article;

import com.bbolab.gaonna.core.domain.member.MemberArticleComment;
import com.bbolab.gaonna.core.domain.member.MemberArticleLike;
import com.bbolab.gaonna.core.domain.quest.Quest;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "title", "content"})
public class Article {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne
    @JoinColumn
    private Quest quest;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    // TODO : Use @Temporal annotation
    @Column
    private LocalDateTime deadline;

    @Column
    private long linkCount;

    @Column
    private long commentCount;

    @OneToMany(mappedBy = "article")
    private List<MemberArticleComment> comments;

    @OneToMany(mappedBy = "article")
    private List<MemberArticleLike> articleLikeMembers;

}