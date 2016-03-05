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

package net.ljcomputing.core.security.filter;

import net.ljcomputing.core.security.authentication.TokenAuthentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Preconditions;

/**
 * Implementation of a Spring Security stateless authentication filter.
 * 
 * Original code: <a href="https://github.com/technical-rex/spring-security-jwt/blob/master/src/main/java/com/technicalrex/springsecurityjwt/auth/jwt/StatelessAuthenticationFilter.java">com.technicalrex.springsecurityjwt.auth.jwt.StatelessAuthenticationFilter</a>
 * 
 * @author James G. Willmore
 *
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

  /** The token authentication service. */
  private final TokenAuthentication tokenAuthenticationService;

  /**
   * Instantiates a new stateless authentication filter.
   *
   * @param tokenAuthenticationService the token authentication service
   */
  public StatelessAuthenticationFilter(
      TokenAuthentication tokenAuthenticationService) {
    super();
    this.tokenAuthenticationService = Preconditions
        .checkNotNull(tokenAuthenticationService);
  }

  /**
   * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {
    System.out.println( ((HttpServletRequest)request).getRequestURL() );
    Authentication authentication = tokenAuthenticationService
        .getAuthentication((HttpServletRequest) request);
    if(null == authentication) {
      System.err.println("  no authentication for " + ((HttpServletRequest)request).getRequestURL());
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
    SecurityContextHolder.getContext().setAuthentication(null);
  }
}
