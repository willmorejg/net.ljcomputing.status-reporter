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
import net.ljcomputing.sr.entity.WbsEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Work breakdown structure entity repository.
 * 
 * @author James G. Willmore
 *
 */
@Repository
public interface WbsEntityRepository extends BaseRepository<WbsEntity, String> {
  
  /**
   * Find by name.
   *
   * @param name the name
   * @return the wbs entity
   */
  WbsEntity findByName(String name);
  
  /**
   * Find work breakdown structures where associated events are between two dates.
   *
   * @param start the start
   * @param end the end
   * @return the list
   */
  @Query("SELECT w FROM WbsEntity w JOIN w.activities a JOIN a.events e WHERE e.startTime >= ?1 AND e.endTime <= ?2")
  List<WbsEntity> findEventsBetween(Date start, Date end);
}
