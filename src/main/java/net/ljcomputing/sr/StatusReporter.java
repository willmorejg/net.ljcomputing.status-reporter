/**
           Copyright 2015, James G. Willmore

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

package net.ljcomputing.sr;

import net.ljcomputing.gson.config.GsonConfiguration;
import net.ljcomputing.gson.config.GsonWebMvcConfigurerAdapter;
import net.ljcomputing.logging.config.LoggingConfiguration;
import net.ljcomputing.sr.config.DozerConfig;
import net.ljcomputing.sr.config.JpaConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * Status Reporter application.
 * 
 * @author James G. Willmore
 *
 */
@Configuration
@EnableAutoConfiguration(exclude = { JacksonAutoConfiguration.class })
@ComponentScan(basePackages = { "net.ljcomputing.core.*",
    "net.ljcomputing.sr.*", "net.ljcomputing.gson.*" })
@Import({ GsonConfiguration.class, GsonWebMvcConfigurerAdapter.class,
    LoggingConfiguration.class, JpaConfiguration.class, DozerConfig.class })
// ModelMapperConfig.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class StatusReporter {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(final String[] args) {
    SpringApplication.run(StatusReporter.class, args);
  }

  /**
   * Configure the application.
   *
   * @param application the application
   * @return the spring application builder
   */
  protected final SpringApplicationBuilder configure(
      final SpringApplicationBuilder application) {
    return application.sources(StatusReporter.class);
  }

}
