package com.bbolab.gaonna.core.domain.category;

import com.bbolab.gaonna.core.domain.quest.Quest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "value"})
public class CategoryValue {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Quest quest;

    @Override
    public String toString() {
        return category.getTitle() + " : " + this.value;
    }
}
