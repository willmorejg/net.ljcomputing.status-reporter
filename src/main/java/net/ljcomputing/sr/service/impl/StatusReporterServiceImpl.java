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
import net.ljcomputing.logging.annotation.InjectLogging;
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.domain.Event;
import net.ljcomputing.sr.domain.WorkBreakdownStructure;
import net.ljcomputing.sr.service.ActivityDomainEntityService;
import net.ljcomputing.sr.service.EventDomainEntityService;
import net.ljcomputing.sr.service.StatusReporterService;
import net.ljcomputing.sr.service.WbsDomainEntityService;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Status Reporter service implementation.
 * 
 * @author James G. Willmore
 *
 */
@Service
public class StatusReporterServiceImpl implements StatusReporterService {

  /** The logger. */
  @InjectLogging
  private static Logger logger;

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
   * @see net.ljcomputing.sr.service.StatusReporterService#findWbsByName(java.lang.String)
   */
  public final WorkBreakdownStructure findWbsByName(final String wbsName)
      throws RequiredValueException, NoEntityFoundException {
    return wbsService.findByName(wbsName);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#removeWbs(java.lang.String)
   */
  public final Boolean removeWbs(final String wbsUuid)
      throws RequiredValueException, NoEntityFoundException {
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
   * @see net.ljcomputing.sr.service.StatusReporterService#findActivitiesForWbs(java.lang.String)
   */
  public final List<Activity> findActivitiesForWbs(final String wbsUuid)
      throws RequiredValueException, NoEntityFoundException {
    return activityService.findByWbs(wbsUuid);
  }

  /**
   * @see net.ljcomputing.sr.service.StatusReporterService#removeActiviy(java.lang.String)
   */
  public final Boolean removeActiviy(String activityUuid)
      throws RequiredValueException, NoEntityFoundException {
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
