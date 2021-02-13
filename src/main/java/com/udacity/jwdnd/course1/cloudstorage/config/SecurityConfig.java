package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationSerivce;

    //Inject AuthenticationService into our SecurityConfig class
    public SecurityConfig(AuthenticationService authenticationService){
        this.authenticationSerivce = authenticationService;
    }

//    //Custom authentication provider to check saved credentials in the db
//    @Override
//    protected void configure(AuthenticationManager auth){
//        auth.authenticationProvider(this.authenticationSerivce);
//    }

    //Our protected routes, need error handling
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/js/**", "/home", "/result").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll();

        http.formLogin()
                .defaultSuccessUrl("/home", true);
    }

}
