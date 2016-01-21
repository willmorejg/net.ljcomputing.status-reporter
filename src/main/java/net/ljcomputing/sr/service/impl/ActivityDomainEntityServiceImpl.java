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
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.entity.ActivityEntity;
import net.ljcomputing.sr.entity.WbsEntity;
import net.ljcomputing.sr.repository.ActivityEntityRepository;
import net.ljcomputing.sr.repository.WbsEntityRepository;
import net.ljcomputing.sr.service.ActivityDomainEntityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

/**
 * Activity domain entity service.
 * 
 * @author James G. Willmore
 *
 */
@Service
@Transactional
public class ActivityDomainEntityServiceImpl extends
    AbstractDomainEntityServiceImpl<Activity, ActivityEntity, String, ActivityEntityRepository>
    implements ActivityDomainEntityService {
  @Autowired
  private WbsEntityRepository wbsRepository;

  /**
   * @see net.ljcomputing.core.service.AbstractDomainEntityServiceImpl#createOrUpdate(net.ljcomputing.core.domain.Domain)
   */
  @Override
  public Activity createOrUpdate(Activity domain)
      throws RequiredValueException, NoEntityFoundException {
    ActivityEntity entity = repository.findByName(domain.getName());

    if (null != entity) {
      domain.setUuid(entity.getUuid());
    }

    return super.createOrUpdate(domain);
  }

  /**
   * @see net.ljcomputing.sr.service.ActivityDomainEntityService#findByName(java.lang.String)
   */
  public Activity findByName(final String name)
      throws RequiredValueException, NoEntityFoundException {
    return strategy.entityToDomain(repository.findByName(name));
  }

  /**
   * @see net.ljcomputing.sr.service.ActivityDomainEntityService#findByWbs(java.lang.String)
   */
  public List<Activity> findByWbs(final String wbsUuid)
      throws RequiredValueException, NoEntityFoundException {
    if (null == wbsUuid) {
      throw new RequiredValueException(
          "Cannot search for activities without a work breakdown structure.");
    }

    WbsEntity wbs = wbsRepository.findOne(wbsUuid);

    if (null == wbs) {
      throw new NoEntityFoundException(
          "Work breakdown structure provided not found - cannot search for activities without a work breakdown structure.");
    }

    return strategy.entityToDomain(repository.findByWbs(wbs));
  }
}
