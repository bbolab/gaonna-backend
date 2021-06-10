package com.bbolab.gaonna.core.domain.category;

import com.bbolab.gaonna.core.domain.quest.QuestCategoryValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;
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

    @Builder.Default
    @OneToMany(mappedBy = "categoryValue", cascade = CascadeType.ALL)
    private List<QuestCategoryValue> questCategoryValueList = new LinkedList<>();

    @Override
    public String toString() {
        return category.getKey() + " : " + this.value;
    }

    public boolean addQuestCategoryValue(QuestCategoryValue questCategoryValue) {
        if(this.questCategoryValueList.contains(questCategoryValue)) {
            return false;
        }
        return questCategoryValueList.add(questCategoryValue);
    }
}
