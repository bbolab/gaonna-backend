package com.bbolab.gaonna.core.domain.member;

import com.bbolab.gaonna.core.domain.tag.ProfileTag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
public class Profile {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private Member member;

    private String name;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<ProfileTag> profileTags;
}

