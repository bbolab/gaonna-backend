package com.bbolab.gaonna.core.domain.quest;

import com.bbolab.gaonna.core.domain.category.CategoryValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class QuestCategoryValue {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn
    private Quest quest;

    @ManyToOne
    @JoinColumn
    private CategoryValue categoryValue;

    public void setQuestAndCategoryValue(Quest quest, CategoryValue categoryValue) {
        quest.addCategoryValue(this);
        this.quest = quest;
        categoryValue.addQuestCategoryValue(this);
        this.categoryValue = categoryValue;
    }
}
