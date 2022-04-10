package com.bbolab.gaonna.core.domain.member;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_GUEST("Guest"),
    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}
