package company.project.core.config;

import company.project.core.config.properties.SecurityProperties;
import company.project.core.config.springsecurity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * spring security config
 *
 * @author huang
 */

@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "core.security", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

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

    @Autowired
    CustomValidateCodeFilter customValidateCodeFilter;

    @Autowired
    CustomAccessDecisionManager customAccessDecisionManager;

    @Autowired
    CustomSecurityInterceptor customSecurityInterceptor;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //add security ignore file
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers(securityProperties.getIgnoreUrl());
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
                .addFilterBefore(customValidateCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customSecurityInterceptor, FilterSecurityInterceptor.class)

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
                .loginPage(securityProperties.getLoginPage())
                .loginProcessingUrl(securityProperties.getLoginProcessUrl())
                .failureForwardUrl(securityProperties.getFailureForwardUrl())
                .defaultSuccessUrl(securityProperties.getDefaultSuccessUrl())
                .failureHandler(customAuthenticationFailHandler)
                .successHandler(customAuthenticationSuccessHandler)

                // create no session
                //.and()
                //.sessionManagement()
                //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getLoginPage()).permitAll()
                .antMatchers(securityProperties.getLoginProcessUrl()).permitAll()
                .antMatchers(securityProperties.getFailureForwardUrl()).permitAll()
                .antMatchers(securityProperties.getValidCodeImgUrl()).permitAll()
                .antMatchers(securityProperties.getDefaultSuccessUrl()).permitAll()
                .antMatchers(securityProperties.getIgnoreUrl()).permitAll()
                .anyRequest().authenticated();


//                .and()
//                .apply(null);

    }
}
