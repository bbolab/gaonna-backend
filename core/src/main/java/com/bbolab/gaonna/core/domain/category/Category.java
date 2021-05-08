package com.bbolab.gaonna.core.domain.category;

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
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "key"})
public class Category {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "category_key", nullable = false)
    private String key;

    @OneToMany(mappedBy = "category")
    private List<CategoryValue> categoryValues;
}
