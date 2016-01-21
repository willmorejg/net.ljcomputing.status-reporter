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


package net.ljcomputing.sr.service;

import net.ljcomputing.core.exception.NoEntityFoundException;
import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.domain.Event;
import net.ljcomputing.sr.domain.WorkBreakdownStructure;

import java.util.List;

/**
 * Status Reporter service.
 * 
 * @author James G. Willmore
 *
 */
 public interface StatusReporterService {
  
  /**
   * Save the work breakdown structure.
   *
   * @param wbs the work breakdown structure
   * @return the persisted work breakdown structure
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   WorkBreakdownStructure saveWbs(WorkBreakdownStructure wbs) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * List all work breakdown structures.
   *
   * @return the list
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   List<WorkBreakdownStructure> listAllWbs() throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Find work breakdown structure by name.
   *
   * @param wbsName the wbs name
   * @return the work breakdown structure
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   WorkBreakdownStructure findWbsByName(String wbsName) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Removes the work breakdown structure.
   *
   * @param wbsUuid the wbs uuid
   * @return the boolean
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   Boolean removeWbs(String wbsUuid) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Save the activity to the work breakdown structure.
   *
   * @param activity the activity
   * @param wbsUuid the work breakdown structure uuid
   * @return the activity
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   Activity saveActivity(Activity activity, String wbsUuid) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Find activity by name.
   *
   * @param activityName the activity name
   * @return the activity
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   Activity findActivityByName(String activityName) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Find activities for the given work breakdown structure.
   *
   * @param wbsUuid the wbs uuid
   * @return the list
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   List<Activity> findActivitiesForWbs(String wbsUuid) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Removes the activiy.
   *
   * @param activityUuid the activity uuid
   * @return the boolean
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   Boolean removeActiviy(String activityUuid) throws RequiredValueException, NoEntityFoundException;

  /**
   * Save the event to the activity.
   *
   * @param event the event
   * @param activityUuid the activity uuid
   * @return the event
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   Event saveEvent(Event event, String activityUuid) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Find events for activity.
   *
   * @param activityUuid the activity uuid
   * @return the list
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   List<Event> findEventsForActivity(String activityUuid) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Indicate the end of an event.
   *
   * @param eventUuid the event uuid
   * @return the event
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   Event endEvent(String eventUuid) throws RequiredValueException, NoEntityFoundException;
  
  /**
   * Removes the event.
   *
   * @param eventUuid the event uuid
   * @return the boolean
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
   Boolean removeEvent(String eventUuid) throws RequiredValueException, NoEntityFoundException;
}
