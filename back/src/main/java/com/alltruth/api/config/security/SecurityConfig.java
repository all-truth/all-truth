package com.alltruth.api.config.security;

import com.alltruth.api.config.security.auth.PrincipalDetails;
import com.alltruth.api.config.security.jwt.JwtAuthenticationFilter;
import com.alltruth.api.config.security.jwt.JwtAuthorizationFilter;
import com.alltruth.api.config.security.jwt.JwtTokenProvider;
import com.alltruth.api.entity.User;
import com.alltruth.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 인증 위임 객체
        AuthenticationManager authenticationManager = authenticationManager((AuthenticationConfiguration) http.getSharedObject(AuthenticationManager.class));
        http.csrf((csrfConfig) -> csrfConfig.disable())
                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider))// authenticationManager를 넣어줘야함 로그인을 진행하는 매니저임
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository, jwtTokenProvider))
                .sessionManagement((sessionPolicy)->sessionPolicy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin((form) -> form.disable())
                .httpBasic((basic)->basic.disable())
                .authorizeHttpRequests((request)-> {
                    request.requestMatchers("/review").authenticated();
                    request.requestMatchers(HttpMethod.PUT,"/review/**").authenticated();
                    request.requestMatchers(HttpMethod.POST,"/review/**").authenticated();
                    request.requestMatchers(HttpMethod.DELETE,"/review/**").authenticated();
                    request.requestMatchers("/user").authenticated();
                    request.anyRequest().permitAll();
                });

        return http.build();
    }

    public static Long getUserId() {
        Authentication at = SecurityContextHolder.getContext().getAuthentication();

        if(at == null || at.getPrincipal() == null) throw new RuntimeException("사용자가 없습니다!");
        PrincipalDetails pd = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = pd.getUser();
        return user.getId();
    }

    public static User getUser() {
        Authentication at = SecurityContextHolder.getContext().getAuthentication();

        if(at == null || at.getPrincipal() == null) throw new RuntimeException("사용자가 없습니다!");
        PrincipalDetails pd = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = pd.getUser();
        return user;
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
