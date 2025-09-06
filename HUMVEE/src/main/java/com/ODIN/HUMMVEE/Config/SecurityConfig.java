package com.ODIN.HUMMVEE.Config;

import com.ODIN.HUMMVEE.Constants.Permissions;
import com.ODIN.HUMMVEE.Service.CustomUserDetailsService;
import com.ODIN.HUMMVEE.filters.JWTAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/authenticate")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET , "/Gadget/**").hasAnyAuthority(Permissions.GADGET_READ.name())
                        .requestMatchers(HttpMethod.POST , "/Gadget/**").hasAuthority(Permissions.GADGET_WRITE.name())
                        .requestMatchers(HttpMethod.DELETE , "/Gadget/**").hasAuthority(Permissions.GADGET_DELETE.name())
                        .anyRequest().authenticated());
            http.addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


//    Earlier implementation with basic authentication now trying to implement jwt authentication
    /*

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/authenticate")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET , "/Gadget/**").hasAnyAuthority(Permissions.GADGET_READ.name())
                        .requestMatchers(HttpMethod.POST , "/Gadget/**").hasAuthority(Permissions.GADGET_WRITE.name())
                        .requestMatchers(HttpMethod.DELETE , "/Gadget/**").hasAuthority(Permissions.GADGET_DELETE.name())
                        .anyRequest().authenticated())
                        .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    Taking backup so that I can implement authority based authorization

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/authenticate")
                        .permitAll()
                        .requestMatchers("/Gadget/getHealthOfApplication").hasRole(Roles.ADMIN.name())
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/authenticate")
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/authenticate")
                        .permitAll()
                        .anyRequest().authenticated());
                http.addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

     */

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService , PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);
    }

}
