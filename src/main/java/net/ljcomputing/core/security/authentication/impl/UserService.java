package net.ljcomputing.core.security.authentication.impl;

import net.ljcomputing.core.security.entity.UserSecurityProfile;
import net.ljcomputing.core.security.repository.UserSecurityProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

import com.google.common.collect.Lists;

/**
 * Implementation of the Spring Security user details service.
 */
@Component
public class UserService implements
    org.springframework.security.core.userdetails.UserDetailsService {
  
  /** The user security profile repository. */
  @Autowired
  private UserSecurityProfileRepository userSecurityProfileRepository;

  /** The details checker. */
  private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

  /**
   * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
   */
  @Override
  public final User loadUserByUsername(String username)
      throws UsernameNotFoundException {
    final UserSecurityProfile userProfile = userSecurityProfileRepository.findByEmail(username);
    
    if (userProfile == null) {
      throw new UsernameNotFoundException("user not found");
    }
    
    final User user = getUser(userProfile);

    detailsChecker.check(user);
    
    return user;
  }
  
  /**
   * Gets the user.
   *
   * @param userSecurityProfile the user security profile
   * @return the user
   */
  private final User getUser(UserSecurityProfile userSecurityProfile) {
    return new User(userSecurityProfile.getEmail(), userSecurityProfile.getPasswordHash(), getGrantedAuthorities(userSecurityProfile));
  }
  
  /**
   * Gets the granted authorities.
   *
   * @param userSecurityProfile the user security profile
   * @return the granted authorities
   */
  private final Collection<GrantedAuthority> getGrantedAuthorities(UserSecurityProfile userSecurityProfile) {
    return Lists.newArrayList(new GrantedAuthority() {
      private static final long serialVersionUID = 8149726960407838098L;

      @Override
      public String getAuthority() {
        return userSecurityProfile.getRole().toString();
      }
    }
    );
  }
}
