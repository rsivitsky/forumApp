package com.sivitsky;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import javax.sql.DataSource;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select email, password, 1 as enabled from user where email=?")
                .authoritiesByUsernameQuery(
                        "select email as username, role from user where email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/setions").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/topics").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/message").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/topic/*").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("login").passwordParameter("password")
                .and()
                /*.logout().permitAll()*/
                /*.logout().logoutSuccessUrl("/login?logout").permitAll()*/
                .logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf();
    }
}