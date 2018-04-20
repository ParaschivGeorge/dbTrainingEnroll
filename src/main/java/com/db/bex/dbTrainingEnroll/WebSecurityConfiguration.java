package com.db.bex.dbTrainingEnroll;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/index.html").permitAll()
//                .antMatchers("/persons/**").hasRole("ADMIN")
//                .and()
//                .httpBasic()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/persons")
//                .and()
//                .exceptionHandling().accessDeniedPage("/login")
////            .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
//                .and()
//                .logout().permitAll();

        http.csrf().disable();

        http
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/**").permitAll();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("{noop}admin").roles("ADMIN")
//                .and()
//                .withUser("user").password("{noop}password").roles("USER");
//    }
}