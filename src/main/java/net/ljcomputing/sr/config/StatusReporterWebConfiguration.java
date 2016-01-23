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

package net.ljcomputing.sr.config;

import net.ljcomputing.gson.config.GsonWebMvcConfigurerAdapter;
import net.ljcomputing.sr.StatusReporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Status Reporter deployment configuration class.
 * 
 * @author James G. Willmore
 *
 */
@Configuration
@ComponentScan(basePackages = { "net.ljcomputing.core", "net.ljcomputing.sr" })
@EnableWebMvc
@Import({ GsonWebMvcConfigurerAdapter.class, StatusReporter.class })
public class StatusReporterWebConfiguration extends SpringBootServletInitializer
    implements WebApplicationInitializer {

  /** The logger. */
  private static Logger logger = LoggerFactory
      .getLogger(StatusReporterWebConfiguration.class);

  /**
   * Listener.
   *
   * @return the servlet context listener
   */
      @Bean
  protected ServletContextListener listener() {
    return new ServletContextListener() {

      /**
       * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
       */
      @Override
      public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized");
        logger.info("ServletContext initialized");
      }

      /**
       * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
       */
      @Override
      public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");
        logger.info("ServletContext destroyed");
      }

    };
  }

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    SpringApplication.run(StatusReporterWebConfiguration.class, args);
  }

  /**
   * @see org.springframework.boot.context.web.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
   */
  protected SpringApplicationBuilder configure(
      SpringApplicationBuilder builder) {
    System.out.println("configure");
    logger.debug("Configuring ... ");
    return builder.showBanner(true)
        .sources(StatusReporterWebConfiguration.class).web(true)
        .logStartupInfo(true);
  }

  /**
   * @see org.springframework.boot.context.web.SpringBootServletInitializer#onStartup(javax.servlet.ServletContext)
   */
  public void onStartup(ServletContext container) throws ServletException {
    logger.info("onStartup ...");
    AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
    ctx.register(StatusReporterWebConfiguration.class);
    ctx.setServletContext(container);

    DispatcherServlet ds = new DispatcherServlet(ctx);
    ServletRegistration.Dynamic servlet = container
        .addServlet("dispatcherServlet", ds);

    servlet.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    servlet.setLoadOnStartup(1);
    servlet.addMapping("/");

    logger.warn("servlet name: {}", servlet.getName());
    logger.warn("servlet throwExceptionIfNoHandlerFound: {}",
        servlet.getInitParameter("throwExceptionIfNoHandlerFound"));
  }

}
