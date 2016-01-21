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

package net.ljcomputing.sr.strategy.impl;

import net.ljcomputing.core.exception.NoEntityFoundException;
import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.core.service.DomainEntityConverterStrategy;
import net.ljcomputing.core.strategy.AbstractDomainEntityStrategy;
import net.ljcomputing.logging.annotation.InjectLogging;
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.entity.ActivityEntity;

import org.slf4j.Logger;

import org.springframework.stereotype.Component;

/**
 * Activity domain entity conversion strategy.
 * 
 * @author James G. Willmore
 *
 */
@Component
public class ActivityStrategy
    extends AbstractDomainEntityStrategy<Activity, ActivityEntity>
    implements DomainEntityConverterStrategy<Activity, ActivityEntity> {
  @InjectLogging
  private static Logger logger;

  /**
   * @see net.ljcomputing.core.strategy.AbstractDomainEntityStrategy#entityToDomain(net.ljcomputing.core.entity.PersistedEntity)
   */
  public Activity entityToDomain(ActivityEntity entity)
      throws RequiredValueException, NoEntityFoundException {
    logger.debug("mapping entity : {}", entity);
    
    if(null == entity) {
      throw new NoEntityFoundException();
    }
    
    return mapper.map(entity, Activity.class);
  }

  /**
   * @see net.ljcomputing.core.strategy.AbstractDomainEntityStrategy#domainToEntity(net.ljcomputing.core.domain.Domain)
   */
  public ActivityEntity domainToEntity(Activity domain)
      throws RequiredValueException {
    logger.debug("mapping domain : {}", domain);
    return mapper.map(domain, ActivityEntity.class);
  }
}
