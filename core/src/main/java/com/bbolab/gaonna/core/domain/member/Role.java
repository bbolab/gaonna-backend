package com.bbolab.gaonna.core.domain.member;

import lombok.Getter;

@Getter
public enum Role {
    GUEST("Guest"),
    USER("User"),
    ADMIN("Admin");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}
