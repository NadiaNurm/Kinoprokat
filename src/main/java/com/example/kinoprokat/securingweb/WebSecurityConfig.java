package com.example.kinoprokat.securingweb;

import com.example.kinoprokat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/mainPage", "/registration", "/**/*.js", "/**/*.css", "/**/*.jpg", "/**/*.jpeg", "/**/*.png").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http.formLogin().defaultSuccessUrl("/mainPage", true);
    }

    //.antMatchers("/","/mainPage","/registration","/**/*.js", "/**/*.css","/adminAddFilm", "/**/*.img").permitAll()
    //  @Bean
    //  public UserDetailsService userDetailsService() {
    //      UserDetails user =
    //              User.withDefaultPasswordEncoder()
    //                      .username("user")
    //                      .password("password")
    //                      .roles("USER")
    //  @Override
    //                      .build();
    //      return new InMemoryUserDetailsManager(user);
    //  }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {//auth
        auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());//отключили шифрование пароля
    }

}