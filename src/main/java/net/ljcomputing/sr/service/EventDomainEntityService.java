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

package net.ljcomputing.sr.service;

import net.ljcomputing.core.exception.NoEntityFoundException;
import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.core.service.DomainEntityService;
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.domain.Event;
import net.ljcomputing.sr.entity.EventEntity;
import net.ljcomputing.sr.repository.EventEntityRepository;

import java.util.List;

/**
 * Interface defining work breakdown structure domain entity service.
 * 
 * @author James G. Willmore
 *
 */
public interface EventDomainEntityService extends
    DomainEntityService<Event, EventEntity, String, EventEntityRepository> {

  /**
   * Find by activity.
   *
   * @param activity the activity
   * @return the list
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
  List<Event> findByActivity(Activity activity)
      throws RequiredValueException, NoEntityFoundException;

  /**
   * Find by activity.
   *
   * @param activityUuid the activity uuid
   * @return the list
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
  List<Event> findByActivity(String activityUuid)
      throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Find events for today.
   *
   * @return the list
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
  List<Event> findEventsForToday() 
      throws RequiredValueException, NoEntityFoundException;
}
