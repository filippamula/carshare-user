package com.project.carshare.user.configuration.security;

import com.project.carshare.user.domain.enums.Role;

public final class SecurityRole {

    public static final String ADMIN = Role.ADMIN.name();
    public static final String USER = Role.USER.name();


    private SecurityRole() {
    }


}
