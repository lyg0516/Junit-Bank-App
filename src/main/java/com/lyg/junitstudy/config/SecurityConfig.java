package com.lyg.junitstudy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyg.junitstudy.config.jwt.JwtAuthenticationFilter;
import com.lyg.junitstudy.config.jwt.JwtAuthorizationFilter;
import com.lyg.junitstudy.domain.user.UserEnum;
import com.lyg.junitstudy.dto.ResponseDto;
import com.lyg.junitstudy.util.CustomResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        log.debug("디버그: BCryptPasswordEncoder 빈 등록");
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록이 필요함

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity>{
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            super.configure(builder);
        }
    }


    // JWT 서버를 만들 예정 -> Session 사용안함
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록");
        http.headers().frameOptions().disable(); // iframe 허용안함.
        http.csrf().disable();
        http.cors().configurationSource(configurationSource());

        // JSessionId를 서버쪽에서 관리안하겠다는 뜻
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // react, 앱으로 요청할 예정
        http.formLogin().disable();
        // httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다.
        http.httpBasic().disable();

        http.apply(new CustomSecurityFilterManager());

        // Exception 가로채기
        http.exceptionHandling().authenticationEntryPoint((request, response, authenticationException) ->{
            CustomResponseUtil.fail(response, 401, "로그인을 진행해 주세요");
        });

        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException)->{
            CustomResponseUtil.fail(response, 403, "권한이 없습니다.");
                });
        http.authorizeRequests()
                .requestMatchers(new AntPathRequestMatcher("/api/s/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
        return http.build();
    }

    public CorsConfigurationSource configurationSource(){
        log.debug("디버그 : configurationSource cors 설정");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
