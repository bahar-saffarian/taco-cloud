package com.bahar.tacos;

import com.bahar.tacos.model.user.User;
import com.bahar.tacos.repository.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> userList = new ArrayList<>();
//        userList.add(new User("buzz", encoder.encode("password"), List.of(new SimpleGrantedAuthority("ROLE_USER"))));
//        userList.add(new User("woody", encoder.encode("password"), List.of(new SimpleGrantedAuthority("ROLE_USER"))));
//        return new InMemoryUserDetailsManager(userList);
//    }

    @Bean
    UserDetailsService userDetailsService(UserRepository repository) {
        return username -> {
            User user = repository.findByUsername(username);
            if (user == null)
                throw new UsernameNotFoundException("User " + username + " not found" );
            return user;
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .antMatchers("/design", "/orders").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/api/tacos/**").hasRole("USER")
                .antMatchers("/", "/**").permitAll()
//                .antMatchers(HttpMethod.POST,"/tacos").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/tacos/**").hasRole("ADMIN") instead @preAuthorized is used
                .and()
//                .formLogin()
//                .loginPage("/login")
//                .and()
                .logout()
                .and()
                .build();
    }
}
