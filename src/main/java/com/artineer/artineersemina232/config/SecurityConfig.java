package com.artineer.artineersemina232.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        auth -> auth.antMatchers("/login","/loginPage" ,"/signUp", "/h2-console/**"
                                        , "/signUp/**").permitAll()
                                .anyRequest().authenticated()
                )

                .formLogin(form -> form.loginPage("/loginPage")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .headers( headers -> headers.frameOptions().disable());

        return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations())
                .antMatchers("/fonts/**","/img/**", "/css/**" , "/scss/**")
                .antMatchers("/h2-console/**");

        //        return web -> web.ignoring()
        //                .antMatchers("/css/**","/img/**");
    }
}
