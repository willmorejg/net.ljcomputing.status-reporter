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
import net.ljcomputing.gson.annotation.ExcludeFromJson;

import java.util.Date;

/**
 * Class defining an event.
 * 
 * @author James G. Willmore
 *
 */
public class Event extends AbstractDomain implements Domain {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -616308535456230921L;

  /** The start time. */
  public Date startTime = new Date();

  /** The end time. */
  public Date endTime;

  /** The description. */
  public String description;

  /** The ticket number. */
  public String ticketNumber;

  /** The activity. */
  @ExcludeFromJson
  private Activity activity;

  /**
   * Gets the activity.
   *
   * @return the activity
   */
  public Activity getActivity() {
    return activity;
  }

  /**
   * Sets the activity.
   *
   * @param activity the new activity
   */
  public void setActivity(final Activity activity) {
    this.activity = activity;
  }

  /**
   * Determines if the given event can be added to an activitiy's events. Did 
   * not want to override equals and hashCode, because of the possible 
   * interference it may cause to how Hibernate acts when equals and hashCode 
   * are overridden.
   *
   * @param event the event
   * @return true, if successful
   */
  boolean eventsAreEqual(final Event event) {
    boolean result = false;

    if (null != event) {
      if (null != event.getUuid() && null != getUuid()
          && event.getUuid().equals(getUuid())) {
        result = true;
      }

      if (!result && null != event.startTime && null != startTime
          && event.startTime.equals(startTime)) {
        result = true;
      }
    }

    return result;
  }

  /**
   * @see net.ljcomputing.core.entity.AbstractDomain#toString()
   */
  @Override
  public String toString() {
    return "Event [startTime=" + startTime + ", endTime=" + endTime
        + ", description=" + description + ", ticketNumber=" + ticketNumber
        + ", uuid=" + getUuid() + "]";
  }
}
