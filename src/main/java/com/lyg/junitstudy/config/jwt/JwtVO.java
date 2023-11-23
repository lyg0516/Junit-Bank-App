package com.lyg.junitstudy.config.jwt;

/**
 * SECRET 노출하면 안된다. (클라우드 AWS - 환경변수, 파일 등등)
 * 리플래시 토큰(X)
 */
public interface JwtVO {

    public static final String SECRET = "yg";
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
