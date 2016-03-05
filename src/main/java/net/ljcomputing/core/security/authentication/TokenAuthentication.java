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

package net.ljcomputing.core.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface defining authentication of an end user to a system.
 * 
 * @author James G. Willmore
 *
 */
public interface TokenAuthentication {
  
  /** The HTTP authentication header name */
  public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
  
  /**
   * Adds the authentication.
   *
   * @param response the response
   * @param authentication the authentication
   * @return the string
   */
  public String addAuthentication(HttpServletResponse response, UserDetails authentication);
  
  /**
   * Gets the authentication.
   *
   * @param request the request
   * @return the authentication
   */
  public Authentication getAuthentication(HttpServletRequest request);
}
