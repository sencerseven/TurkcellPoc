package com.turkcell.poc.model;

import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomUserDetails extends UserDto implements UserDetails {

  public CustomUserDetails(UserDto userDto) {
    super(userDto);
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles().stream()
        .map(roleDto -> new SimpleGrantedAuthority("ROLE" + roleDto.getRoleType().toString()))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return super.getPassword();
  }

  @Override
  public String getUsername() {
    return super.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return (super.getActive() == 1 ? true : false);
  }
}
