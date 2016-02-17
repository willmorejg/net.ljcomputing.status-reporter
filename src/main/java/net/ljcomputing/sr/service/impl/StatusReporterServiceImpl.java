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
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.domain.Event;
import net.ljcomputing.sr.domain.WorkBreakdownStructure;
import net.ljcomputing.sr.service.ActivityDomainEntityService;
import net.ljcomputing.sr.service.EventDomainEntityService;
import net.ljcomputing.sr.service.StatusReporterService;
import net.ljcomputing.sr.service.WbsDomainEntityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Status Reporter service implementation.
 * 
 * @author James G. Willmore
 *
 */
@Service
public class StatusReporterServiceImpl implements StatusReporterService {

  /** The work breakdown structure service. */
  @Autowired
  private WbsDomainEntityService wbsService;

  /** The activity service. */
  @Autowired
  private ActivityDomainEntityService activityService;

  /** The event service. */
  @Autowired
  private EventDomainEntityService eventService;

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#saveWbs(net.ljcomputing.sr.domain.WorkBreakdownStructure)
   */
  public final WorkBreakdownStructure saveWbs(final WorkBreakdownStructure wbs)
      throws RequiredValueException, NoEntityFoundException {
    return wbsService.createOrUpdate(wbs);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#listAllWbs()
   */
  public final List<WorkBreakdownStructure> listAllWbs()
      throws RequiredValueException, NoEntityFoundException {
    return wbsService.findAll();
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findWbsByUuid(java.lang.String)
   */
  public final WorkBreakdownStructure findWbsByUuid(String wbsUuid)
      throws RequiredValueException, NoEntityFoundException {
    return wbsService.findById(wbsUuid);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findWbsByName(java.lang.String)
   */
  public final WorkBreakdownStructure findWbsByName(final String wbsName)
      throws RequiredValueException, NoEntityFoundException {
    return wbsService.findByName(wbsName);
  }
  
  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findWbsForToday()
   */
  public final List<WorkBreakdownStructure> findWbsForToday() 
      throws RequiredValueException, NoEntityFoundException {
    return wbsService.findEventsForToday();
  }
  
  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findEventsBetween(java.util.Date, java.util.Date)
   */
  public final List<WorkBreakdownStructure> findEventsBetween(Date start, Date end) 
      throws RequiredValueException, NoEntityFoundException {
    return wbsService.findEventsBetween(start, end);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#removeWbs(java.lang.String)
   */
  public final Boolean removeWbs(final String wbsUuid)
      throws RequiredValueException, NoEntityFoundException {
    WorkBreakdownStructure wbs = findWbsByUuid(wbsUuid);
    
    if(null != wbs.getActivities()) {
      for(Activity activity : wbs.getActivities()) {
        removeActivity(activity.getUuid());
      }
    }
    
    return wbsService.delete(wbsUuid);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#saveActivity(net.ljcomputing.sr.domain.Activity, java.lang.String)
   */
  public final Activity saveActivity(final Activity activity,
      final String wbsUuid)
          throws RequiredValueException, NoEntityFoundException {
    WorkBreakdownStructure wbs = wbsService.findById(wbsUuid);
    activity.setWbs(wbs);
    
    Activity domain = activityService.createOrUpdate(activity);
    wbs.addActivity(domain);
    
    wbsService.createOrUpdate(wbs);
    
    return activityService.findById(domain.getUuid());
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findActivityByName(java.lang.String)
   */
  public final Activity findActivityByName(final String activityName)
      throws RequiredValueException, NoEntityFoundException {
    return activityService.findByName(activityName);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findActivityByUuid(java.lang.String)
   */
  public final Activity findActivityByUuid(String activityUuid) 
      throws RequiredValueException, NoEntityFoundException {
    return activityService.findById(activityUuid);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findActivitiesForWbs(java.lang.String)
   */
  public final List<Activity> findActivitiesForWbs(final String wbsUuid)
      throws RequiredValueException, NoEntityFoundException {
    return activityService.findByWbs(wbsUuid);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#removeActivity(java.lang.String)
   */
  public final Boolean removeActivity(String activityUuid)
      throws RequiredValueException, NoEntityFoundException {
    
    for(Event event : findEventsForActivity(activityUuid)) {
      removeEvent(event.getUuid());
    }
    
    return activityService.delete(activityUuid);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#saveEvent(net.ljcomputing.sr.domain.Event, java.lang.String)
   */
  public final Event saveEvent(final Event event, final String activityUuid)
      throws RequiredValueException, NoEntityFoundException {
    Activity activity = activityService.findById(activityUuid);
    event.setActivity(activity);
    
    Event domain = eventService.createOrUpdate(event);
    activity.addEvent(domain);
    
    activityService.createOrUpdate(activity);
    
    return eventService.findById(domain.getUuid());
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findEventsForActivity(java.lang.String)
   */
  public final List<Event> findEventsForActivity(final String activityUuid)
      throws RequiredValueException, NoEntityFoundException {
    return eventService.findByActivity(activityUuid);
  }
  
  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findEventsForToday()
   */
  public final List<Event> findEventsForToday() 
      throws RequiredValueException, NoEntityFoundException {
    return eventService.findEventsForToday();
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#findEventByUuid(java.lang.String)
   */
  public final Event findEventByUuid(final String eventUuid) 
      throws RequiredValueException, NoEntityFoundException {
    return eventService.findById(eventUuid);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#endEvent(java.lang.String)
   */
  public final Event endEvent(final String eventUuid)
      throws RequiredValueException, NoEntityFoundException {
    Event event = eventService.findById(eventUuid);
    event.endTime = new Date();
    
    return eventService.createOrUpdate(event);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#removeEvent(java.lang.String)
   */
  public final Boolean removeEvent(final String eventUuid)
      throws RequiredValueException, NoEntityFoundException {
    return eventService.delete(eventUuid);
  }
}
