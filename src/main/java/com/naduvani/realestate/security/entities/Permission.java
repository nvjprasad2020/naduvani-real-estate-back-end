package com.naduvani.realestate.security.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    READ("read"),
    UPDATE("update"),
    CREATE("create"),
    DELETE("delete");
    @Getter
    private final String permission;
}
