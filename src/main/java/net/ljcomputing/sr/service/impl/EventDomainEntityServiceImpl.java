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
import net.ljcomputing.sr.domain.Event;
import net.ljcomputing.sr.entity.ActivityEntity;
import net.ljcomputing.sr.entity.EventEntity;
import net.ljcomputing.sr.repository.ActivityEntityRepository;
import net.ljcomputing.sr.repository.EventEntityRepository;
import net.ljcomputing.sr.service.EventDomainEntityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

/**
 * Event domain entity service.
 * 
 * @author James G. Willmore
 *
 */
@Service
@Transactional
public class EventDomainEntityServiceImpl extends
    AbstractDomainEntityServiceImpl<Event, EventEntity, String, EventEntityRepository>
    implements EventDomainEntityService {

  /** The activity repository. */
  @Autowired
  private ActivityEntityRepository activityRepository;

  /**
   * @see net.ljcomputing.sr.service.EventDomainEntityService#findByActivity(net.ljcomputing.sr.domain.Activity)
   */
  public List<Event> findByActivity(Activity activity)
      throws RequiredValueException, NoEntityFoundException {
    if (null == activity) {
      throw new RequiredValueException(
          "Cannot search for events without an activity.");
    }

    return findByActivity(activity.getUuid());
  }

  /**
   * @see net.ljcomputing.sr.service.EventDomainEntityService#findByActivity(java.lang.String)
   */
  public List<Event> findByActivity(String activityUuid)
      throws RequiredValueException, NoEntityFoundException {
    if (null == activityUuid) {
      throw new RequiredValueException(
          "Cannot search for events without an activity.");
    }

    ActivityEntity entity = activityRepository.findOne(activityUuid);

    if (null == entity) {
      throw new NoEntityFoundException(
          "Activity provided not found - cannot search for events without an activity.");
    }

    return strategy.entityToDomain(repository.findByActivity(entity));
  }
}
