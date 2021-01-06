package company.project.app.config;

import company.project.app.config.springsecurity.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * spring security config
 *
 * @author huang
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CorsFilter corsFilter;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    CustomAuthenticationFailHandler customAuthenticationFailHandler;

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //add security ignore file
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/*.html",
                        "/favicon.ico",
                        "/**/*.htm",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.jpg",
                        "/**/*.webp",
                        "/**/*.mp4",
                        "/**/*.eot",
                        "/**/*.woff2",
                        "/**/*.woff",
                        "/**/*.ttf",
                        "/druid/**",
                        "/actuator/**",
                        "/h2-console/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .failureForwardUrl("/login/error")
                .defaultSuccessUrl("/login/success")
                .failureHandler(customAuthenticationFailHandler)
                .successHandler(customAuthenticationSuccessHandler)

                // create no session
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/login/process").permitAll()
                .antMatchers("/login/error").permitAll()
                .anyRequest().authenticated();


//                .and()
//                .apply(null);

    }
}
