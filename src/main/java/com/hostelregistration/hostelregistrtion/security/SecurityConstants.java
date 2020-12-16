package com.hostelregistration.hostelregistrtion.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/**";
    //public static final String MySQL_URL = "MySQL-console/**";
    public static final String SECRET = "SecretkeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 300_000;
}
