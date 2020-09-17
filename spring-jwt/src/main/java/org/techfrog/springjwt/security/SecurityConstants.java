package org.techfrog.springjwt.security;

public final class SecurityConstants {

    public static final String SIGNUP_URL = "/signup";
    public static final String AUTH_URL = "/authenticate";
    public static final String AUTH_HEADER_NAME = "Authorization";
    public static final int JWT_TOKEN_VALIDITY = 1000 * 60;

    private SecurityConstants() {
    }
}
