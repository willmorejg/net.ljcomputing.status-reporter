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
import net.ljcomputing.sr.entity.EventEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Event entity repository.
 * 
 * @author James G. Willmore
 *
 */
@Repository
public interface EventEntityRepository
    extends BaseRepository<EventEntity, String> {

  /**
   * Find by activity.
   *
   * @param activityEntity the activity entity
   * @return the list
   */
  List<EventEntity> findByActivity(ActivityEntity activityEntity);
}
