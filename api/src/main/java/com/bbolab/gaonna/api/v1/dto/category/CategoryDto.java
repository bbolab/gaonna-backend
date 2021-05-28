package com.bbolab.gaonna.api.v1.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    String key;

    String value;

    @Override
    public String toString() {
        return key + " : " + value;
    }
}
