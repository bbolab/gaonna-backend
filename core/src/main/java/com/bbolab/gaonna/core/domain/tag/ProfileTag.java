package com.bbolab.gaonna.core.domain.tag;

import com.bbolab.gaonna.core.domain.member.Profile;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProfileTag {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private Profile profile;

    @ManyToOne
    @JoinColumn
    private Tag tag;
}
