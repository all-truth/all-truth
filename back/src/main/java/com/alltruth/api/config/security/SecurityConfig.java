package com.alltruth.api.config.security;

import com.alltruth.api.config.security.jwt.JwtAuthenticationFilter;
import com.alltruth.api.config.security.jwt.JwtAuthorizationFilter;
import com.alltruth.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CorsFilter corsFilter;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 인증 위임 객체
        AuthenticationManager authenticationManager = authenticationManager((AuthenticationConfiguration) http.getSharedObject(AuthenticationManager.class));
        http.csrf((csrfConfig) -> csrfConfig.disable())
                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager))// authenticationManager를 넣어줘야함 로그인을 진행하는 매니저임
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository))
                .sessionManagement((sessionPolicy)->sessionPolicy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin((form) -> form.disable())
                .httpBasic((basic)->basic.disable())
                .authorizeHttpRequests((request)->
                        request.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {return new BCryptPasswordEncoder();}

    // 빈으로 등록해야 사용가능 왜지?
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        System.out.println("authenticationManagerBean authenticationConfiguration");
        System.out.println(authenticationConfiguration);
        System.out.println("authenticationManagerBean authenticationConfiguration");
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
//        System.out.println("authenticationManagerBean Http");
//        System.out.println(http);
//        System.out.println("authenticationManagerBean Http");
//        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        builder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//        return builder.build();
//    }
}
