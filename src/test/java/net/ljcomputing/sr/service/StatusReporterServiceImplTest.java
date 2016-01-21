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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import net.ljcomputing.core.test.CommonClassUnitTest;
import net.ljcomputing.core.test.CommonUnitTest;
import net.ljcomputing.gson.converter.GsonConverterService;
import net.ljcomputing.logging.annotation.InjectLogging;
import net.ljcomputing.sr.StatusReporter;
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.domain.Event;
import net.ljcomputing.sr.domain.WorkBreakdownStructure;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Status Reporter service tests.
 * 
 * @author James G. Willmore
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { StatusReporter.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class StatusReporterServiceImplTest {
  /** The logger. */
  @InjectLogging
  protected static Logger logger;

  /** The common class unit test. */
  @ClassRule
  public static CommonClassUnitTest commonClassUnitTest = new CommonClassUnitTest();

  /** The common unit test. */
  @Rule
  public CommonUnitTest commonUnitTest = new CommonUnitTest();

  /** The gson converter service. */
  @Autowired
  private GsonConverterService gsonConverterService;

  /** The wbs name. */
  private static String wbsName = "WBS1";

  /** The activity name. */
  private static String activityName = "ACT1";

  /** The ticket number. */
  private static String ticketNumber = "TICK-123456";

  /** The sr service. */
  @Autowired
  private StatusReporterService srService;

  @Test
  public void test1AddWorkBreakdownStructure() {
    try {
      WorkBreakdownStructure wbs = new WorkBreakdownStructure(wbsName);

      wbs = srService.saveWbs(wbs);

      logger.info("wbs : {}", gsonConverterService.toJson(wbs));
      logger.info("ALL wbs : {}",
          gsonConverterService.toJson(srService.listAllWbs()));
    } catch (Exception e) {
      logger.error("Tested failed : ", e);
      fail();
    }
  }

  @Test
  public void test2FindWbsByName() {
    try {
      WorkBreakdownStructure wbs = srService.findWbsByName(wbsName);
      assertNotNull("wbs is null", wbs);
      logger.info("wbs : {}", gsonConverterService.toJson(wbs));
    } catch (Exception e) {
      logger.error("Tested failed : ", e);
      fail();
    }
  }

  @Test
  public void test3AddActivitiy() {
    try {
      WorkBreakdownStructure wbs = srService.findWbsByName(wbsName);

      assertNotNull("wbs is null", wbs);

      Activity activity = new Activity(activityName);
      activity = srService.saveActivity(activity, wbs.getUuid());
      activity = srService.findActivityByName(activityName);

      assertNotNull("activity is null", activity);
      logger.info("activity : {}", gsonConverterService.toJson(activity));

      activity.description = "HA!";

      activity = srService.saveActivity(activity, wbs.getUuid());

      logger.info("activities by wbs : {}",
          srService.findActivitiesForWbs(wbs.getUuid()));

      logger.info("ALL wbs : {}",
          gsonConverterService.toJson(srService.listAllWbs()));
    } catch (Exception e) {
      logger.error("Tested failed : ", e);
      fail();
    }
  }

  @Test
  public void test4FindActivtiesByName() {
    try {
      Activity activity = srService.findActivityByName(activityName);
      assertNotNull("activity is null", activity);
      logger.info("activity : {}", gsonConverterService.toJson(activity));
      logger.info("ALL wbs : {}",
          gsonConverterService.toJson(srService.listAllWbs()));
    } catch (Exception e) {
      logger.error("Tested failed : ", e);
      fail();
    }
  }

  @Test
  public void test5AddEvent() {
    try {
      Activity activity = srService.findActivityByName(activityName);
      assertNotNull("activity is null", activity);

      Event event = new Event();
      event.ticketNumber = ticketNumber;
      event = srService.saveEvent(event, activity.getUuid());
      event = srService.endEvent(event.getUuid());
      event.description = "It worked";
      event = srService.saveEvent(event, activity.getUuid());

      logger.info("ALL wbs : {}",
          gsonConverterService.toJson(srService.listAllWbs()));
    } catch (Exception e) {
      logger.error("Tested failed : ", e);
      fail();
    }
  }

  @Test
  public void test6FindEventsByActivity() {
    try {
      Activity activity = srService.findActivityByName(activityName);
      assertNotNull("activity is null", activity);

      List<Event> events = srService.findEventsForActivity(activity.getUuid());
      assertNotNull("event is null", events);
      logger.info("event : {}", gsonConverterService.toJson(events));
    } catch (Exception e) {
      logger.error("Tested failed : ", e);
      fail();
    }
  }

  @Test
  public void test7RemoveEvent() {
    try {
      WorkBreakdownStructure wbs = srService.findWbsByName(wbsName);
      assertNotNull("wbs is null", wbs);
      logger.info("wbs : {}", gsonConverterService.toJson(wbs));

      Activity activity = wbs.getActivities().get(0);
      assertNotNull("activity is null", activity);
      logger.info("activity : {}", gsonConverterService.toJson(activity));

      assertNotNull("events are null", activity.getEvents());

      for (Event event : activity.getEvents()) {
        logger.debug("deleteing event : {}", event);
        assertTrue("FAILED to delete event",
            srService.removeEvent(event.getUuid()));
      }

      logger.info("wbs : {}", gsonConverterService.toJson(wbs));
    } catch (Exception e) {
      logger.error("Tested failed : ", e);
      fail();
    }
  }

  @Test
  public void test8RemoveActivitiy() {
    try {
      WorkBreakdownStructure wbs = srService.findWbsByName(wbsName);
      assertNotNull("wbs is null", wbs);
      logger.info("wbs : {}", gsonConverterService.toJson(wbs));

      assertNotNull("activities are null", wbs.getActivities());

      for (Activity activity : wbs.getActivities()) {
        logger.debug("deleting activity : {}", activity);
        assertTrue("FAILED to delete activity",
            srService.removeActiviy(activity.getUuid()));
      }

      logger.info("wbs : {}", gsonConverterService.toJson(wbs));
    } catch (Exception e) {
      logger.error("Tested failed : ", e);
      fail();
    }
  }

  @Test
  public void test9RemoveWorkBreakdownStructure() {
    try {
      WorkBreakdownStructure wbs = srService.findWbsByName(wbsName);
      assertNotNull("wbs is null", wbs);
      logger.info("wbs : {}", gsonConverterService.toJson(wbs));

      assertTrue("FAILED to delete wbs", srService.removeWbs(wbs.getUuid()));

      logger.info("ALL wbs : {}",
          gsonConverterService.toJson(srService.listAllWbs()));
    } catch (Exception e) {
      logger.error("Tested failed : ", e);
      fail();
    }
  }
}
