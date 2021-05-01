package com.bbolab.gaonna.core.domain.member;

import com.bbolab.gaonna.core.domain.article.Article;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MemberArticleLike {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Member member;

    // TODO : how to check undo "like" - soft or hard delete?
}
