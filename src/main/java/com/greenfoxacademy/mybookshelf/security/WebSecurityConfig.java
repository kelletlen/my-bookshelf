package com.greenfoxacademy.mybookshelf.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenFilter jwtTokenFilter;

  public WebSecurityConfig(JwtTokenFilter jwtTokenFilter) {
    this.jwtTokenFilter = jwtTokenFilter;
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user1").password("pw").roles("user")
        .authorities("USER")
        .and()
        .withUser("admin").password("adminPass").roles("admin")
        .authorities("ADMIN");

  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/users/register").permitAll()
        .antMatchers(HttpMethod.POST, "/users/login").permitAll()
        .antMatchers(HttpMethod.POST, "/books/add").authenticated()
        .antMatchers(HttpMethod.GET, "/books/search/*").authenticated()
        .antMatchers(HttpMethod.POST, "/copy/add/*").authenticated()
        .antMatchers(HttpMethod.POST, "/copy/loan").authenticated()
        .antMatchers(HttpMethod.DELETE, "/copy/loan/delete/*").authenticated()
        .antMatchers(HttpMethod.GET, "/loans/list/*").authenticated()
        .antMatchers(HttpMethod.POST, "/books/review").authenticated()
        .antMatchers(HttpMethod.DELETE, "/books/review/delete/*").authenticated()
        .antMatchers(HttpMethod.PATCH, "/users/updateRole/*").hasAuthority("admin")
        .antMatchers(HttpMethod.POST, "/wishlist/add/*").authenticated()
        .antMatchers(HttpMethod.DELETE, "/wishlist/delete/*").authenticated()
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
