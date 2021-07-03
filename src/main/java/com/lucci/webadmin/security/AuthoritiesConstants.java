package com.lucci.webadmin.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String RECEPTIONIST = "ROLE_RECEPTIONIST";
    public static final String DOCTOR = "ROLE_DOCTOR";
    public static final String MARKETING = "ROLE_MARKETING";
    public static final String MANAGER = "ROLE_MANAGER";

    private AuthoritiesConstants() {
    }
}
