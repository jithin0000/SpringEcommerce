package com.jithin.Ecommerce.security;

import com.jithin.Ecommerce.services.CustomUserDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import java.beans.Beans;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true, prePostEnabled = true, jsr250Enabled = true
)
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private CustomUserDetailServices userDetailsService;
    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/**/*.png",
                        "/**/*.jpg",
                        "/**/*.svg",
                        "/**/*.gif",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"

                ).permitAll().antMatchers("/api/user/**",
                "/api/cart/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/category/**",
                        "/api/product/**")
                .permitAll()
                .anyRequest().fullyAuthenticated();

        http.addFilterBefore(authenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    public JwtAuthenticationFilter authenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
