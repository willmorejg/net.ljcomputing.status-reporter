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

package net.ljcomputing.sr.domain;

import net.ljcomputing.core.domain.Domain;
import net.ljcomputing.core.entity.AbstractDomain;
import net.ljcomputing.core.exception.RequiredValueException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defining a work breakdown structure.
 * 
 * @author James G. Willmore
 *
 */
public class WorkBreakdownStructure extends AbstractDomain implements Domain {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -2834895408835732844L;

  /** The name. */
  private String name;

  /** The description. */
  public String description;

  /** The activities. */
  private List<Activity> activities;

  /**
   * Instantiates a new work breakdown structure.
   */
  WorkBreakdownStructure() {
  }

  /**
   * Instantiates a new work breakdown structure.
   *
   * @param name the name
   * @throws RequiredValueException the required value exception
   */
  public WorkBreakdownStructure(final String name)
      throws RequiredValueException {
    if (null == name) {
      throw new RequiredValueException("name is required.");
    }

    this.name = name;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the activities.
   *
   * @return the activities
   */
  public List<Activity> getActivities() {
    if(null == activities) {
      activities = new ArrayList<Activity>();
    }
    
    return activities;
  }

  /**
   * Adds the activity.
   *
   * @param activity the activity
   */
  public void addActivity(final Activity activity) {
    Activity result = getActivityByName(activity.getName());

    if (null == result) {
      getActivities().add(activity);
    } else {
      getActivities().remove(result);
      getActivities().add(activity);
    }
  }

  /**
   * Gets the activity by name. Return the activity found, or null if the 
   * activity is not found.
   *
   * @param name the name
   * @return the activity by name
   */
  public Activity getActivityByName(final String name) {
    Activity result = null;

    for (Activity activity : getActivities()) {
      if (name.equals(activity.getName())) {
        result = activity;
        break;
      }
    }

    return result;
  }

  /**
   * Gets the events by activity.
   *
   * @param activity the activity
   * @return the events by activity
   */
  public List<Event> getEventsByActivity(final Activity activity) {
    Activity found = getActivityByName(activity.getName());
    List<Event> result = new ArrayList<Event>();

    if (null != found) {
      result.addAll(found.getEvents());
    }

    return result;
  }

  /**
   * Gets the events by ticket number.
   *
   * @param ticketNumber the ticket number
   * @return the events by ticket number
   */
  public List<Event> getEventsByTicketNumber(final String ticketNumber) {
    List<Event> result = new ArrayList<Event>();

    for (Activity activity : getActivities()) {
      if (null != activity.getEvents()) {
        for (Event event : activity.getEvents()) {
          if (ticketNumber.equals(event.ticketNumber)) {
            result.add(event);
          }
        }
      }
    }

    return result;
  }

  /**
   * @see net.ljcomputing.core.entity.AbstractDomain#toString()
   */
  @Override
  public String toString() {
    return "WorkBreakdownStructure [name=" + name + ", description="
        + description + ", activities=" + activities + ", getUuid()="
        + getUuid() + "]";
  }
}
