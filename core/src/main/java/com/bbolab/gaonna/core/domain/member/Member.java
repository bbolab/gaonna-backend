package com.bbolab.gaonna.core.domain.member;

import com.bbolab.gaonna.core.domain.quest.MemberQuest;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Lob
    @Basic(fetch = FetchType.EAGER)  // TODO : Should link data source
    private String profileImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberQuest> memberQuest = new LinkedList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Profile> profiles = new LinkedList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberArticleComment> memberArticleComments = new LinkedList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberArticleLike> memberArticleLikes = new LinkedList<>();

    public boolean addMemberQuest(MemberQuest memberQuest) {
        if(this.memberQuest.contains(memberQuest)) {
            return false;
        }
        memberQuest.setMember(this);
        return this.memberQuest.add(memberQuest);
    }

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

}
