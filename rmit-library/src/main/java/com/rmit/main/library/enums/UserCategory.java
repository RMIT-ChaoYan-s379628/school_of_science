package com.rmit.main.library.enums;

public enum UserCategory {

    ADMIN, 
    CLIENT,
    USER;

    public String getAuthority() {
        return this.name();
    }

}
