package com.bbolab.gaonna.core.domain.member;

import com.bbolab.gaonna.core.domain.article.Article;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "content"})
public class MemberArticleComment {
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
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
