package com.bbolab.gaonna.api.v1.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    String key;

    String value;

    @Override
    public String toString() {
        return key + " : " + value;
    }
}
