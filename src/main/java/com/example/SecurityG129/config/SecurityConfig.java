package com.example.SecurityG129.config;


import com.example.SecurityG129.model.User;
import com.example.SecurityG129.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hibernate.mapping.Array;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var user = userService.getUserByEmail(username);
            if (user == null) throw new UsernameNotFoundException("User Not Found");
            else return user;
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var auth = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
            auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

            httpSecurity.csrf(AbstractHttpConfigurer::disable);

            httpSecurity.exceptionHandling(e -> e.accessDeniedPage("/forbidden"));

            httpSecurity.formLogin(fl ->
                    fl.loginProcessingUrl("/auth")
                    .usernameParameter("email")
                            .passwordParameter("password")
                            .loginPage("/sign-in")
                            .defaultSuccessUrl("/", true)
                            .failureForwardUrl("/sign-in?error"));

            httpSecurity.logout(lg -> lg.logoutSuccessUrl("/log-out").logoutSuccessUrl("/sign-in"));

            return httpSecurity.build();

    }
}
