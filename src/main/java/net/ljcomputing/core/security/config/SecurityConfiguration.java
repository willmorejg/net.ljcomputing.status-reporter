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

package net.ljcomputing.core.security.config;

import net.ljcomputing.core.security.authentication.TokenAuthentication;
import net.ljcomputing.core.security.authentication.impl.TokenAuthenticationImpl;
import net.ljcomputing.core.security.authentication.impl.UserService;
import net.ljcomputing.core.security.filter.StatelessAuthenticationFilter;
import net.ljcomputing.core.security.handler.AccessDeniedHandlerCustomImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security.
 * 
 * Original code: <a href="https://github.com/technical-rex/spring-security-jwt/blob/master/src/main/java/com/technicalrex/springsecurityjwt/vendor/SpringSecurityConfig.java">com.technicalrex.springsecurityjwt.vendor.SpringSecurityConfig</a>
 * 
 * @author James G. Willmore
 *
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  /** The user details service. */
  private final UserDetailsService userDetailsService;

  /** The token authentication service. */
  private final TokenAuthentication tokenAuthenticationService;

  /**
   * @throws Exception 
   * Instantiates a new security configuration.
   */
  public SecurityConfiguration() throws Exception {
    super(true);
    userDetailsService = new UserService();
    tokenAuthenticationService = new TokenAuthenticationImpl("tooManySecrets",
        userDetailsService);
  }

  /**
   * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.exceptionHandling()
        .accessDeniedHandler(new AccessDeniedHandlerCustomImpl()).and()
        .anonymous().and().servletApi().and().headers().cacheControl().and()
        .authorizeRequests()

    // // Allow anonymous logins
    // .antMatchers("/auth/**").permitAll()

    .antMatchers(new String[] { "/", "/favicon.ico", "/**/*.htm", "/**/*.html",
        "/**/*.css", "/**/*.js", "/webjars/*" }).permitAll()

    // All other request need to be authenticated
        .anyRequest().authenticated().and()

    // Custom Token based authentication based on the header previously given to
    // the client
        .addFilterBefore(
            new StatelessAuthenticationFilter(tokenAuthenticationService),
            UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService())
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  /**
   * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#authenticationManagerBean()
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
