package com.turkcell.poc.config;

import com.turkcell.poc.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Sencer SEVEN
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  CustomUserDetailService customUserDetailService;

  @Autowired
  ClientDetailsService clientDetailsService;

  @Bean
  public static BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .and()
        .authorizeRequests().antMatchers(
            "/h2-console", "/h2-console/**", "/oauth/token",
        "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
        .permitAll()
        .anyRequest().authenticated()
        .and().csrf().disable();
  }


  @Override
  public void configure(WebSecurity web) throws Exception {
    web
        .ignoring()
        .antMatchers("/h2-console/**","/v2/api-docs",
            "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**");
  }

  @Autowired
  public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  @Autowired
  public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
    TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
    handler.setTokenStore(tokenStore);
    handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
    handler.setClientDetailsService(clientDetailsService);
    return handler;
  }


}
