package com.bbolab.gaonna.core.domain.member;

import com.bbolab.gaonna.core.domain.quest.MemberQuest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "firstname", "lastname", "nickname"})
public class Member {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    private String firstname;

    private String lastname;

    private String nickname;

    private LocalDateTime birthDate;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(updatable = false)
    @CreationTimestamp
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
}
