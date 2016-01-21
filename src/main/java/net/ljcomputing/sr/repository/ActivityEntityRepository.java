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

package net.ljcomputing.sr.repository;

import net.ljcomputing.core.repository.BaseRepository;
import net.ljcomputing.sr.entity.ActivityEntity;
import net.ljcomputing.sr.entity.WbsEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Activity entity repository.
 * 
 * @author James G. Willmore
 *
 */
@Repository
public interface ActivityEntityRepository
    extends BaseRepository<ActivityEntity, String> {
  
  /**
   * Find by name.
   *
   * @param name the name
   * @return the activity entity
   */
  ActivityEntity findByName(String name);
  
  /**
   * Find by work breakdown structure.
   *
   * @param wbsEntity the wbs entity
   * @return the list
   */
  List<ActivityEntity> findByWbs(WbsEntity wbsEntity);
}
