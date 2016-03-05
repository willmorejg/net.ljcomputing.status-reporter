/**
           Copyright 2016, James G. Willmore

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package net.ljcomputing.core.security.authentication.impl;

import net.ljcomputing.core.security.authentication.TokenAuthentication;
import net.ljcomputing.core.security.domain.UserAuthenticationDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Preconditions;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Implementation defining authentication of an end user to a system.
 * 
 * @author James G. Willmore
 *
 */
public class TokenAuthenticationImpl implements TokenAuthentication {

  /** The secret. */
  private final String secret;

  /** The user details service. */
  private final UserDetailsService userDetailsService;

  /**
   * Instantiates a new token authentication implementation.
   *
   * @param secret the secret
   * @param userDetailsService the user details service
   * @throws Exception the exception
   */
  public TokenAuthenticationImpl(String secret,
      UserDetailsService userDetailsService) throws Exception {
    this.secret = secret;
    this.userDetailsService = userDetailsService;
    validate();
  }

  /**
   * Validate the constructor arguments.
   */
  private void validate() {
    Preconditions.checkArgument(secret != null && secret.trim().length() > 0);
    Preconditions.checkNotNull(userDetailsService);
  }

  /**
   * @see net.ljcomputing.core.security.authentication.TokenAuthentication#addAuthentication(javax.servlet.http.HttpServletResponse, net.ljcomputing.core.security.domain.UserAuthentication)
   */
  public String addAuthentication(HttpServletResponse response,
      UserDetails authentication) {
    String token = createTokenForUser(authentication);
    response.addHeader(AUTH_HEADER_NAME, token);
    return token;
  }

  /**
   * @see net.ljcomputing.core.security.authentication.TokenAuthentication#getAuthentication(javax.servlet.http.HttpServletRequest)
   */
  public Authentication getAuthentication(HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER_NAME);

    if (token != null) {
      final UserDetails user = parseUserFromToken(token);

      if (user != null) {
        return new UserAuthenticationDetails(user);
      }
    }

    return null;
  }

  /**
   * Parses the user from token.
   *
   * @param token the token
   * @return the user details
   */
  private UserDetails parseUserFromToken(String token) {
    String username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
        .getBody().getSubject();

    return userDetailsService.loadUserByUsername(username);
  }

  /**
   * Creates the token for user.
   *
   * @param user the user
   * @return the string
   */
  private String createTokenForUser(UserDetails user) {
    return Jwts.builder().setSubject(user.getUsername())
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }
}
