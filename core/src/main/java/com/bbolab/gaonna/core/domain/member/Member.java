package com.bbolab.gaonna.core.domain.member;

import com.bbolab.gaonna.core.domain.quest.MemberQuestPerformer;
import com.bbolab.gaonna.core.domain.quest.MemberQuestRequester;
import com.bbolab.gaonna.core.domain.report.ArticleReport;
import com.bbolab.gaonna.core.domain.report.MemberBlockReport;
import com.bbolab.gaonna.core.domain.quest.QuestReview;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@EqualsAndHashCode(of = {"id", "name", "nickname", "email"})
public class Member {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private LocalDateTime birthDate;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Lob
    @Basic(fetch = FetchType.EAGER)  // TODO : Should link data source
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Builder.Default
    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL)
    private List<MemberQuestPerformer> performerQuest = new LinkedList<>();

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
    private List<MemberQuestRequester> requesterQuest = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Profile> profiles = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberArticleComment> memberArticleComments = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberArticleLike> memberArticleLikes = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)        // 해당 멤버가 신고한 다른 게시글
    private List<ArticleReport> articleReports = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)
    private List<MemberBlockReport> reportedMember = new LinkedList<>(); // 해당 멤버가 차단한 다른 멤버들

    @Builder.Default
    @OneToMany(mappedBy = "targetMember", cascade = CascadeType.ALL)
    private List<MemberBlockReport> blockedMember = new LinkedList<>(); // 해당 멤버를 차단한 다른 멤버들

    @Builder.Default
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)
    private List<ArticleReport> reportedArticles = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private List<QuestReview> reviewedQuests = new LinkedList<>();

    public boolean addProfiles(Profile profile) {
        if(this.profiles.contains(profile)) {
            return false;
        }
        profile.setMember(this);
        return this.profiles.add(profile);
    }

    public boolean addMemberArticleComment(MemberArticleComment articleComment) {
        if (this.memberArticleComments.contains(articleComment)) {
            return false;
        }
        articleComment.setMember(this);
        return this.memberArticleComments.add(articleComment);
    }

    public boolean addMemberArticleLike(MemberArticleLike articleLike) {
        if (this.memberArticleLikes.contains(articleLike)) {
            return false;
        }
        articleLike.setMember(this);
        return this.memberArticleLikes.add(articleLike);
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.profileImage = picture;
        return this;
    }
}
