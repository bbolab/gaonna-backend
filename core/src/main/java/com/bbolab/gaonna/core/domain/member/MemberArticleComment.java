package com.bbolab.gaonna.core.domain.member;

import com.bbolab.gaonna.core.domain.article.Article;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "content"})
public class MemberArticleComment {
    // TODO : Should consider soft delete
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private Article article;

    @ManyToOne
    @JoinColumn
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime updatedTime;
}
