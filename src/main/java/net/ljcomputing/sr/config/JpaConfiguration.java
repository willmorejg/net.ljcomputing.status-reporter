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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * JPA configuration class.
 * 
 * @author James G. Willmore
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
@EnableJpaRepositories(
    basePackages = { "net.ljcomputing.sr.repository" })
@EntityScan("net.ljcomputing.sr.entity")
@EnableTransactionManagement
public class JpaConfiguration {
  
  /** The user. */
  @Value("${spring.datasource.username}")
  private String user;

  /** The password. */
  @Value("${spring.datasource.password}")
  private String password;

  /** The data source url. */
  @Value("${spring.datasource.url}")
  private String dataSourceUrl;

  /** The data source class name. */
  @Value("${spring.datasource.dataSourceClassName}")
  private String dataSourceClassName;

  /** The pool name. */
  @Value("${spring.datasource.poolName}")
  private String poolName;

  /** The connection timeout. */
  @Value("${spring.datasource.connectionTimeout}")
  private int connectionTimeout;

  /** The max lifetime. */
  @Value("${spring.datasource.maxLifetime}")
  private int maxLifetime;

  /** The maximum pool size. */
  @Value("${spring.datasource.maximumPoolSize}")
  private int maximumPoolSize;

  /** The minimum idle. */
  @Value("${spring.datasource.minimumIdle}")
  private int minimumIdle;

  /** The idle timeout. */
  @Value("${spring.datasource.idleTimeout}")
  private int idleTimeout;

  /**
   * Property sources placeholder configurer.
   *
   * @return the property sources placeholder configurer
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  /**
   * Configured data source bean.
   *
   * @return the data source
   */
  @Bean(destroyMethod = "close")
  public DataSource dataSource() {
    Properties configProps = new Properties();
    configProps.put("jdbcUrl", dataSourceUrl);
    configProps.put("username", user);
    configProps.put("password", password);
    configProps.put("driverClassName", dataSourceClassName);
    configProps.put("poolName", poolName);
    configProps.put("maximumPoolSize", maximumPoolSize);
    configProps.put("minimumIdle", minimumIdle);
    configProps.put("minimumIdle", minimumIdle);
    configProps.put("connectionTimeout", connectionTimeout);
    configProps.put("idleTimeout", idleTimeout);

    HikariConfig hc = new HikariConfig(configProps);
    HikariDataSource ds = new HikariDataSource(hc);

    return ds;
  }

  /**
   * Configured entity manager factory bean.
   *
   * @param dataSource the data source
   * @param jpaVendorAdapter the jpa vendor adapter
   * @return the local container entity manager factory bean
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
    LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
    lef.setDataSource(dataSource);
    lef.setJpaVendorAdapter(jpaVendorAdapter);
    lef.setPackagesToScan("net.ljcomputing.statusReporter");
    return lef;
  }

  /**
   * Configured JPA vendor adapter bean.
   *
   * @return the jpa vendor adapter
   */
  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setShowSql(false);
    hibernateJpaVendorAdapter.setGenerateDdl(true);
    hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
    return hibernateJpaVendorAdapter;
  }

  /**
   * Configured transaction manager bean.
   *
   * @param entityManagerFactory the entity manager factory
   * @return the platform transaction manager
   */
  @Bean
  public PlatformTransactionManager transactionManager(
      EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

  /**
   * Persistence exception translation bean.
   *
   * @return the persistence exception translation post processor
   */
  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }
}
