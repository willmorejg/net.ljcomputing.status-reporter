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

package net.ljcomputing.sr.service.impl;

import net.ljcomputing.core.exception.NoEntityFoundException;
import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.core.service.AbstractDomainEntityServiceImpl;
import net.ljcomputing.core.util.DateUtils;
import net.ljcomputing.sr.domain.WorkBreakdownStructure;
import net.ljcomputing.sr.entity.WbsEntity;
import net.ljcomputing.sr.repository.WbsEntityRepository;
import net.ljcomputing.sr.service.WbsDomainEntityService;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Work breakdown structure domain entity service.
 * 
 * @author James G. Willmore
 *
 */
@Service
@Transactional
public class WbsDomainEntityServiceImpl extends
    AbstractDomainEntityServiceImpl<WorkBreakdownStructure, WbsEntity, String, WbsEntityRepository>
    implements WbsDomainEntityService {

  /**
   * @see net.ljcomputing.core.service.AbstractDomainEntityServiceImpl#createOrUpdate(net.ljcomputing.core.domain.Domain)
   */
  @Override
  public WorkBreakdownStructure createOrUpdate(WorkBreakdownStructure domain)
      throws RequiredValueException, NoEntityFoundException {
    WbsEntity entity = repository.findByName(domain.getName());
    
    if(null != entity) {
      domain.setUuid(entity.getUuid());
    }
    
    return super.createOrUpdate(domain);
  }

  /**
   * @see net.ljcomputing.sr.service.WbsDomainEntityService#findByName(java.lang.String)
   */
  public WorkBreakdownStructure findByName(String name) 
      throws RequiredValueException, NoEntityFoundException {
    return strategy.entityToDomain(repository.findByName(name));
  }
  
  /**
   * @see net.ljcomputing.sr.service.WbsDomainEntityService#findEventsForToday()
   */
  public List<WorkBreakdownStructure> findEventsForToday() 
      throws RequiredValueException, NoEntityFoundException {
    Date today = new Date();
    return findEventsBetween(today, today);
  }

  /**
   * @see net.ljcomputing.sr.service.WbsDomainEntityService#findEventsBetween(java.util.Date, java.util.Date)
   */
  public List<WorkBreakdownStructure> findEventsBetween(Date start, Date end) 
      throws RequiredValueException, NoEntityFoundException {
    Date started = DateUtils.midnight(start);
    Date ended = DateUtils.endOfDay(end);

    return strategy.entityToDomain(repository.findEventsBetween(started, ended));
  }
}
