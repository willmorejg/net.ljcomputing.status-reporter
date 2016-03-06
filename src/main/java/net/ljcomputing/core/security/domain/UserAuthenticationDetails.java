package net.ljcomputing.core.security.domain;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class representing an end user to a system, and their authentication status.
 * 
 * Original code: <a href="https://github.com/technical-rex/spring-security-jwt/blob/master/src/main/java/com/technicalrex/springsecurityjwt/auth/jwt/UserAuthentication.java">com.technicalrex.springsecurityjwt.auth.jwt.UserAuthentication</a>
 */
public class UserAuthenticationDetails implements Authentication, Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 4549784992128559711L;

  /** The user details. */
  private final UserDetails user;

  /** The authenticated. */
  private boolean authenticated = true;

  /**
   * Instantiates a new user authentication.
   *
   * @param user the user
   */
  public UserAuthenticationDetails(UserDetails user) {
    this.user = user;
  }

  /**
   * @see java.security.Principal#getName()
   */
  public String getName() {
    return user.getUsername();
  }

  /**
   * @see org.springframework.security.core.Authentication#getAuthorities()
   */
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthorities();
  }

  /**
   * @see org.springframework.security.core.Authentication#getCredentials()
   */
  public Object getCredentials() {
    return user.getPassword();
  }

  /**
   * @see org.springframework.security.core.Authentication#getDetails()
   */
  public UserDetails getDetails() {
    return user;
  }

  /**
   * @see org.springframework.security.core.Authentication#getPrincipal()
   */
  public Object getPrincipal() {
    return user.getUsername();
  }

  /**
   * @see org.springframework.security.core.Authentication#isAuthenticated()
   */
  public boolean isAuthenticated() {
    return authenticated;
  }

  /**
   * @see org.springframework.security.core.Authentication#setAuthenticated(boolean)
   */
  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "UserAuthentication [user=" + user + ", authenticated="
        + authenticated + ", name=" + getName() + ", authorities="
        + getAuthorities() + ", credentials=" + getCredentials()
        + ", details=" + getDetails() + ", principal="
        + getPrincipal() + ", isAuthenticated=" + isAuthenticated() + "]";
  }

  
}
