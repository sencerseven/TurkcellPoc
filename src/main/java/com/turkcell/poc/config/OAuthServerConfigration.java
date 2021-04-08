package com.turkcell.poc.config;

import com.turkcell.poc.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Sencer SEVEN
 */
@Configuration
public class OAuthServerConfigration {

  private static final String SERVER_RESOURCE_ID = "oauth2-server";

  private static final int TIME = 10000;

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Autowired
  CustomUserDetailService customUserDetailService;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;


  @Configuration
  @EnableResourceServer
  protected static class ResourceServer extends ResourceServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
      resources.resourceId(SERVER_RESOURCE_ID).tokenStore(tokenStore);
    }
  }

  @Configuration
  @EnableAuthorizationServer
  protected class AuthConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
        throws Exception {

      endpoints
          .authenticationManager(authenticationManager)
          .reuseRefreshTokens(false)
          .tokenStore(tokenStore())
          .tokenServices(tokenServices())
          .accessTokenConverter(accessTokenConverter())
          .tokenEnhancer(accessTokenConverter())
          .userDetailsService(customUserDetailService);

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

      clients.inMemory()
          .withClient("turkcell")
          .secret(passwordEncoder().encode("turkcell"))
          .authorizedGrantTypes("client_credentials", "password", "refresh_token")
          .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
          .scopes("read", "write", "trust")
          .accessTokenValiditySeconds(TIME)
          .refreshTokenValiditySeconds(TIME)
          .resourceIds(SERVER_RESOURCE_ID);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
      security
          .allowFormAuthenticationForClients();
    }

  }

  @Bean
  public TokenStore tokenStore() {
    return new InMemoryTokenStore();
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    DefaultUserAuthenticationConverter duac = new DefaultUserAuthenticationConverter();
    duac.setUserDetailsService(customUserDetailService);

    DefaultAccessTokenConverter datc = new DefaultAccessTokenConverter();
    datc.setUserTokenConverter(duac);

    JwtAccessTokenConverter jatc = new JwtAccessTokenConverter();
    jatc.setAccessTokenConverter(datc); // IMPORTANT

    jatc.setSigningKey("123");
    return jatc;
  }

  @Primary
  @Bean
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices tokenServices = new DefaultTokenServices();
    tokenServices.setTokenStore(tokenStore());
    tokenServices.setSupportRefreshToken(true);
    tokenServices.setTokenEnhancer(accessTokenConverter());
    return tokenServices;
  }
}
