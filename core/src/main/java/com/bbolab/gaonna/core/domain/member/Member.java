package com.bbolab.gaonna.core.domain.member;

import com.bbolab.gaonna.core.domain.quest.MemberQuestPerformer;
import com.bbolab.gaonna.core.domain.quest.MemberQuestRequester;
import com.bbolab.gaonna.core.domain.quest.Quest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "firstname", "lastname", "nickname"})
public class Member {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    private String firstname;

    private String lastname;

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
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberArticleReport> memberArticleReports = new LinkedList<>();

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

    public boolean addMemberArticleReport(MemberArticleReport report) {
        if (this.memberArticleReports.contains(report)) {
            return false;
        }
        report.setMember(this);
        return this.memberArticleReports.add(report);
    }
}
