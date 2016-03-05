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

package net.ljcomputing.core.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Access denied implementation.
 * 
 * @author James G. Willmore
 *
 */
public class AccessDeniedHandlerCustomImpl implements AccessDeniedHandler {

  /** The Constant logger. */
  private static Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException)
          throws IOException, ServletException {
    logger.error("An error occured during the processing of {}:",
        request.getRequestURL().toString(), accessDeniedException);
  }

}
