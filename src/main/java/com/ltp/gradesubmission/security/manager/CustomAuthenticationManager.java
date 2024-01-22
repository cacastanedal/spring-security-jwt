package com.ltp.gradesubmission.security.manager;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

  private UserService userService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    User user = userService.getUser(authentication.getName());
    if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
      throw new BadCredentialsException("Wrong password");
    }

    return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
  }
}
