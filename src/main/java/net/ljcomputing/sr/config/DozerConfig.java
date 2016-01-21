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

package net.ljcomputing.sr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import org.dozer.spring.DozerBeanMapperFactoryBean;

/**
 * Dozer configuration.
 * 
 * @author James G. Willmore
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan({"net.ljcomputing.sr.domain", "net.ljcomputing.sr.entity"})
public class DozerConfig {
  
  /**
   * Dozer bean mapper factory bean.
   *
   * @param resources the resources
   * @return the dozer bean mapper factory bean
   * @throws Exception the exception
   */
  @Bean
  public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(
      @Value("classpath*:dozer/*mapping*.xml") Resource[] resources)
          throws Exception {
    final DozerBeanMapperFactoryBean bean = new DozerBeanMapperFactoryBean();
    // Other configurations
    bean.setMappingFiles(resources);
    return bean;
  }
}
