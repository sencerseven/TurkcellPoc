package com.turkcell.poc.service;

import com.turkcell.poc.converter.UserMapper;
import com.turkcell.poc.entity.Users;
import com.turkcell.poc.model.CustomUserDetails;
import com.turkcell.poc.model.UserDto;
import com.turkcell.poc.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = "transactionManager")
@Service public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<Users> userOptional =
        userRepository.findByUserName(s);

    userOptional.orElseThrow(() ->new UsernameNotFoundException("user not found"));

    Optional<UserDto> userDto = Optional.of(userMapper.userToUserDto(userOptional.get()));

    return userDto.map(CustomUserDetails::new).get();
  }
}
