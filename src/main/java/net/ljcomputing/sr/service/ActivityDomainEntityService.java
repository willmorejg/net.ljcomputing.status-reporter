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
import net.ljcomputing.sr.entity.ActivityEntity;
import net.ljcomputing.sr.repository.ActivityEntityRepository;

import java.util.List;

/**
 * Interface defining activity domain entity service.
 * 
 * @author James G. Willmore
 *
 */
public interface ActivityDomainEntityService extends
    DomainEntityService<Activity, ActivityEntity, String, ActivityEntityRepository> {

  /**
   * Find by name.
   *
   * @param name the name
   * @return the activity
   * @throws RequiredValueException the required value exception
   */
  public Activity findByName(String name) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Find by work breakdown structure.
   *
   * @param wbsUuid the wbs uuid
   * @return the activity
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
  public List<Activity> findByWbs(String wbsUuid) throws RequiredValueException, NoEntityFoundException;
}
