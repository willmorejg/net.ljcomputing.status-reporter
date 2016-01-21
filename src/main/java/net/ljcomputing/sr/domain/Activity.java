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
import net.ljcomputing.gson.annotation.ExcludeFromJson;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Class defining an activity.
 * 
 * @author James G. Willmore
 *
 */
public class Activity extends AbstractDomain implements Domain {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -4045728912328282323L;

  /** The name. */
  private String name;

  /** The description. */
  public String description;

  /** The wbs. */
  @ExcludeFromJson
  private WorkBreakdownStructure wbs;

  /** The events. */
  private List<Event> events;

  /**
   * Instantiates a new activity.
   */
  Activity() {
  }

  /**
   * Instantiates a new activity.
   *
   * @param name the name
   * @throws RequiredValueException the required value exception
   */
  public Activity(final String name) throws RequiredValueException {
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
   * Gets the wbs.
   *
   * @return the wbs
   */
  public WorkBreakdownStructure getWbs() {
    return wbs;
  }

  /**
   * Sets the wbs.
   *
   * @param wbs the new wbs
   */
  public void setWbs(final WorkBreakdownStructure wbs) {
    this.wbs = wbs;
  }

  /**
   * Gets the events.
   *
   * @return the events
   */
  public List<Event> getEvents() {
    if (null == events) {
      events = new ArrayList<Event>();
    }

    return events;
  }

  /**
   * Adds the event.
   *
   * @param event the event
   */
  public void addEvent(final Event event) {
    if (!getEvents().contains(event)) {
      ListIterator<Event> eventsIt = getEvents().listIterator();

      while (eventsIt.hasNext()) {
        Event obj = eventsIt.next();

        if (obj.eventsAreEqual(event)) {
          eventsIt.remove();
        }
      }

      event.setActivity(this);
      getEvents().add(event);
    }
  }

  /**
   * Gets the event by ticket number. If found, the event is returned, otherwise
   *  null is returned.
   *
   * @param ticketNumber the ticket number
   * @return the event by ticket number
   */
  public Event getEventByTicketNumber(final String ticketNumber) {
    Event result = null;

    for (Event event : getEvents()) {
      if (ticketNumber.equals(event.ticketNumber)) {
        result = event;
        break;
      }
    }

    return result;
  }

  /** 
   * @see net.ljcomputing.core.entity.AbstractDomain#toString()
   */
  @Override
  public String toString() {
    return "Activity [name=" + name + ", description=" + description
        + ", events=" + events + ", getUuid()=" + getUuid() + "]";
  }
}
