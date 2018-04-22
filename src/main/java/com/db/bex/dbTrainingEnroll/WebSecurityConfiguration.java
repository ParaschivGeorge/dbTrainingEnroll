package com.db.bex.dbTrainingEnroll;

import com.db.bex.dbTrainingEnroll.entity.UserType;
import com.db.bex.dbTrainingEnroll.security.JwtAuthenticationEntryPoint;
import com.db.bex.dbTrainingEnroll.security.JwtAuthorizationTokenFilter;
import com.db.bex.dbTrainingEnroll.security.JwtTokenUtil;
import com.db.bex.dbTrainingEnroll.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/trainings").permitAll()
                    .antMatchers("/dummy").authenticated()
                    .antMatchers("/dummypost").authenticated()
                    .antMatchers("/crapa").hasAuthority(UserType.MANAGER.name())
//                    .antMatchers("/pendingTrainings").hasAuthority(UserType.PM.name())
//                    .antMatchers("/pendingUsers").hasAuthority(UserType.PM.name())
//                    .antMatchers("/approveList").hasAuthority(UserType.PM.name())
//                    .antMatchers("/subordinates").hasAuthority(UserType.MANAGER.name())
//                    .antMatchers("/subordinatesResult").hasAuthority(UserType.MANAGER.name())
//                    .antMatchers("/crapa").permitAll()
                    .antMatchers("/pendingTrainings").permitAll()
                    .antMatchers("/pendingUsers").permitAll()
                    .antMatchers("/approveList").permitAll()
                    .antMatchers("/subordinates").permitAll()
                    .antMatchers("/subordinatesResult").permitAll()
                // this should be set later, only for testing
//                    .antMatchers(HttpMethod.GET, "/**").permitAll()
//                    .antMatchers(HttpMethod.POST, "/**").permitAll()
//                    .antMatchers(HttpMethod.PUT, "/**").permitAll()
//                    .antMatchers(HttpMethod.PATCH, "/**").permitAll()
//                    .antMatchers(HttpMethod.DELETE, "/**").permitAll()

//                .and()
//                .httpBasic()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .and()
//                .exceptionHandling().accessDeniedPage("/login")
                .and()
                .logout().permitAll();

        // Custom JWT based security filter
        JwtAuthorizationTokenFilter authenticationTokenFilter = new JwtAuthorizationTokenFilter(userDetailsService(), jwtTokenUtil, tokenHeader);
        http
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

//        WebSecurityCorsFilter webSecurityCorsFilter = new WebSecurityCorsFilter();
//        http
//                .addFilterBefore(webSecurityCorsFilter, JwtAuthorizationTokenFilter.class);
        // disable page caching
        http
                .headers()
                .frameOptions().sameOrigin()
                .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        authenticationPath,
                        "/",
                        "/trainings",
                        "/register",
                        "/pendingTrainings",
                        "/approveList",
                        "/subordinates",
                        "/subordinatesResult"
                )

                // allow anonymous resource requests
                .and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/trainings",
                        "/register",
                        "/pendingTrainings",
                        "/approveList",
                        "/subordinates",
                        "/subordinatesResult"
                );
    }
}