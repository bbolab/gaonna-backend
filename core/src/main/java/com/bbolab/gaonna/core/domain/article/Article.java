package com.bbolab.gaonna.core.domain.article;

import com.bbolab.gaonna.core.domain.member.MemberArticleComment;
import com.bbolab.gaonna.core.domain.member.MemberArticleLike;
import com.bbolab.gaonna.core.domain.quest.Quest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Column
    private long likeCount;

    @Column
    private long commentCount;

    @OneToMany(mappedBy = "article")
    private List<MemberArticleComment> comments;

    @OneToMany(mappedBy = "article")
    private List<MemberArticleLike> articleLikeMembers;

    public boolean addMemberArticleComment(MemberArticleComment memberArticleComment) {
        if (comments.contains(memberArticleComment)) {
            return false;
        }
        // TODO : add commentCount increment
        memberArticleComment.setArticle(this);
        return comments.add(memberArticleComment);
    }

    public boolean addMemberArticleLike(MemberArticleLike memberArticleLike) {
        if (articleLikeMembers.contains(memberArticleLike)) {
            return false;
        }
        this.likeCount++;
        memberArticleLike.setArticle(this);
        return articleLikeMembers.add(memberArticleLike);
    }

    public void removeMemberArticleLike(MemberArticleLike memberArticleLike) {
        if (this.articleLikeMembers.contains(memberArticleLike)) {
            this.likeCount--;
            this.articleLikeMembers.remove(memberArticleLike);
        }
    }
}
