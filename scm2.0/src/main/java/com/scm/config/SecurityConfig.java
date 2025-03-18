package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
//user create and login using java code in memory service


/*@Bean
public UserDetailsService userDetailsService(){

    /*UserDetails user=User
    .withDefaultPasswordEncoder()
    .username("admin")
   .password("admin")
   .roles("ADMIN","USER")
   .build();

    var inMemoryUserDetailsManager=new InMemoryUserDetailsManager(user);
    return inMemoryUserDetailsManager;
}*/
@Autowired
private SecurityCustomUserDetailService userDetailsService;
@Autowired
private OAuthAuthenticationSuccessHandler handler;
//configuration of authentication provider  
@Bean
public DaoAuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
    //user details service object
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

    return daoAuthenticationProvider;
}
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
    //configuration
    httpSecurity.authorizeHttpRequests(authorize->{
        //authorize.requestMatchers("/home","/register").permitAll();
        authorize.requestMatchers("/user/**").authenticated();
        authorize.anyRequest().permitAll();
    });

    httpSecurity.formLogin(formLogin->{
        formLogin.loginPage("/login");
        formLogin.loginProcessingUrl("/authenticate");
        formLogin.defaultSuccessUrl("/user/dashboard");
        formLogin.failureForwardUrl("/login?error=true");
        formLogin.usernameParameter("email");
        formLogin.passwordParameter("password");
        
    });
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.logout(logoutForm->{
        logoutForm.logoutUrl("/do-logout");
        logoutForm.logoutSuccessUrl("/login?logout=true");
    });
    //oauth configuration
    httpSecurity.oauth2Login(oauth->{
        oauth.loginPage("/login");
        oauth.successHandler(handler);
    });

    return httpSecurity.build();    
}
@Bean 
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}  

}

