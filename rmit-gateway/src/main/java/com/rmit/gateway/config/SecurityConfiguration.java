
package com.rmit.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rmit.gateway.security.RestAccessDeniedHandler;
import com.rmit.gateway.security.RestAuthenticationFailureHandler;
import com.rmit.gateway.security.RestAuthenticationSuccessHandler;
import com.rmit.gateway.security.RestUnauthorizedEntryPoint;
import com.rmit.main.library.gateway.enums.UserRole;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security.user.name}")
    private String                           username;

    @Value("${security.user.password}")
    private String                           password;

    @Autowired
    private RestUnauthorizedEntryPoint       restUnauthorizedEntryPoint;

    @Autowired
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private RestAccessDeniedHandler          restAccessDeniedHandler;

    @Autowired
    private UserDetailsService               userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        auth.inMemoryAuthentication().withUser(username).password(password).roles(UserRole.ADMIN.toString());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .httpBasic().and()
        .csrf().disable()
        .authorizeRequests()
         .antMatchers("/verify").permitAll()
         .antMatchers("/setPassword").permitAll()
         .antMatchers("/forgotPassword").permitAll()
         .antMatchers("/resetPassword").permitAll()
         .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
         .anyRequest().authenticated()
         .and()
        .exceptionHandling()
         .authenticationEntryPoint(restUnauthorizedEntryPoint)
         .accessDeniedHandler(restAccessDeniedHandler)
         .and()
        .formLogin()
         .loginProcessingUrl("/authenticate")
         .failureHandler(restAuthenticationFailureHandler)
         .successHandler(restAuthenticationSuccessHandler)
         .usernameParameter("username")
         .passwordParameter("password")
         .permitAll()
         .and()
        .logout()
         .logoutUrl("/logout")
         .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
         .deleteCookies("JSESSIONID")
         .permitAll()
         .and();
      }

    @Override
    public void configure(WebSecurity web) throws Exception {
        /*web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/login", "/");*/
        //web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
        web.ignoring().antMatchers("/resources/**", "/index.html", "/login.html", "/partials/**", "/", "/error/**");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("x-auth-token");
        config.addAllowedHeader("x-requested-with");
        config.addAllowedHeader("x-xsrf-token");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return source;

        /*CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;*/
    }
}
