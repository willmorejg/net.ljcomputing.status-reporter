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

import net.ljcomputing.core.exception.NoEntityFoundException;
import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.core.test.CommonClassUnitTest;
import net.ljcomputing.core.test.CommonUnitTest;
import net.ljcomputing.gson.converter.GsonConverterService;
import net.ljcomputing.sr.StatusReporter;
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.domain.Event;
import net.ljcomputing.sr.domain.WorkBreakdownStructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Status Reporter use case tests.
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
public class StatusReporterUseCaseTests {
  /** The logger. */
  private static Logger logger = LoggerFactory
      .getLogger(StatusReporterUseCaseTests.class);

  /** The common class unit test. */
      @ClassRule
  public static CommonClassUnitTest commonClassUnitTest = new CommonClassUnitTest();

  /** The common unit test. */
  @Rule
  public CommonUnitTest commonUnitTest = new CommonUnitTest();

  /** The gson converter service. */
  @Autowired
  private GsonConverterService gsonConverterService;

  /** The sr service. */
  @Autowired
  private StatusReporterService srService;

  /** The Constant wbs's. */
  private static List<WorkBreakdownStructure> wbsS;

  /** The Constant wbs's - saved. */
  private static List<WorkBreakdownStructure> wbsSaved = new ArrayList<WorkBreakdownStructure>();

  /** The Constant activities. */
  private static List<Activity> activities;

  /** The Constant events. */
  private static List<Event> events;

  /** The Constant total test cases. */
  private static final int totalCases = 20;

  /** The Constant start count. */
  private static final int startCount = 1;

  /**
   * A before class method.
   */
  @BeforeClass
  public static void aBeforeClassMethod() {
    try {
      wbsS = new ArrayList<WorkBreakdownStructure>();
      activities = new ArrayList<Activity>();
      events = new ArrayList<Event>();

      int count = startCount;
      for (int i = 0; i < totalCases; i++) {
        wbsS.add(new WorkBreakdownStructure("WBS" + count++));
      }

      count = startCount;
      for (int i = 0; i < totalCases; i++) {
        activities.add(new Activity("ACT" + count++));
      }

      for (int i = 0; i < totalCases; i++) {
        events.add(new Event());
      }
    } catch (Exception e) {
      logger.error("FAILED to execute before class method : ", e);
    }
  }

  /**
   * Perform an initial batch save of work breakdown structures, activities, 
   * and events. Will create if non-existent, 
   * or update if entities already exist.
   */
  @Test
  public void test0() {
    try {
      for (WorkBreakdownStructure wbs : srService.listAllWbs()) {
        srService.removeWbs(wbs.getUuid());
      }

      for (int i = 0; i < wbsS.size(); i++) {
        logger.info("adding wbs : {}", wbsS.get(i));

        WorkBreakdownStructure savedWbs = srService.saveWbs(wbsS.get(i));
        assertNotNull("saved wbs value was null", savedWbs);

        logger.info("adding activity : {}", activities.get(i));

        Activity savedActivity = srService.saveActivity(activities.get(i),
            savedWbs.getUuid());
        assertNotNull("saved activity value was null", savedActivity);

        Event savedEvent = srService.saveEvent(events.get(i),
            savedActivity.getUuid());
        savedEvent.description = "1st Event for activity "
            + savedActivity.getName();
        srService.saveEvent(savedEvent, savedActivity.getUuid());

        assertNotNull("saved event value was null", savedEvent);

        savedEvent = srService.saveEvent(events.get(i),
            savedActivity.getUuid());
        savedEvent.description = "2nd Event for activity "
            + savedActivity.getName();
        srService.saveEvent(savedEvent, savedActivity.getUuid());

        assertNotNull("saved event value was null", savedEvent);
      }

      wbsSaved.clear();
      wbsSaved = srService.listAllWbs();

      logger.info("test results : {}", gsonConverterService.toJson(wbsSaved));
    } catch (RequiredValueException e) {
      logger.error("failed test : ", e);
      fail("Required value exception");
    } catch (NoEntityFoundException e) {
      logger.error("failed test : ", e);
      fail("No entity found exception");
    }
  }

  /**
   * Update existing entities.
   */
  @Test
  public void test1() {
    try {
      for (WorkBreakdownStructure wbs : wbsSaved) {
        wbs.description = "Updated";
        for (Activity activity : wbs.getActivities()) {
          activity.description = "Updated";
          for (Event event : activity.getEvents()) {
            event.description = event.description + " - updated";
            srService.saveEvent(event, activity.getUuid());
            srService.endEvent(event.getUuid());
          }
          srService.saveActivity(activity, wbs.getUuid());
        }
        srService.saveWbs(wbs);
      }

      logger.info("test results : {}",
          gsonConverterService.toJson(srService.listAllWbs()));

      wbsSaved.clear();
      wbsSaved = srService.listAllWbs();

      logger.info("test results : {}", gsonConverterService.toJson(wbsSaved));
    } catch (RequiredValueException e) {
      logger.error("failed test : ", e);
      fail("Required value exception");
    } catch (NoEntityFoundException e) {
      logger.error("failed test : ", e);
      fail("No entity found exception");
    }
  }

  /**
   * Remove various work breakdown structures, activities, and events.
   */
  @Test
  public void test2() {
    try {
      for (WorkBreakdownStructure wbs : srService.listAllWbs()) {
        int wbsTest = Integer
            .valueOf(wbs.getName().substring(3, wbs.getName().length()));
        if ((wbsTest % 2) == 0) {
          assertTrue("Could not delete wbs",
              srService.removeWbs(wbs.getUuid()));
        }
      }

      for (WorkBreakdownStructure wbs : srService.listAllWbs()) {
        for (Activity activity : srService
            .findActivitiesForWbs(wbs.getUuid())) {
          int activityTest = Integer.valueOf(
              activity.getName().substring(3, activity.getName().length()));
          if ((activityTest % 4) == 0) {
            assertTrue("Could not delete activity",
                srService.removeActivity(activity.getUuid()));
          }
        }
      }

      for (WorkBreakdownStructure wbs : srService.listAllWbs()) {
        for (Activity activity : srService
            .findActivitiesForWbs(wbs.getUuid())) {
          for (Event event : srService
              .findEventsForActivity(activity.getUuid())) {
            int eventTest = Integer.valueOf(event.description.substring(0, 1));
            if (eventTest == 1) {
              assertTrue("Could not delete event", srService.removeEvent(event.getUuid()));
            }
          }
        }
      }

      wbsSaved.clear();
      wbsSaved = srService.listAllWbs();

      logger.info("test results : {}", gsonConverterService.toJson(wbsSaved));
    } catch (RequiredValueException e) {
      logger.error("failed test : ", e);
      fail("Required value exception");
    } catch (NoEntityFoundException e) {
      logger.error("failed test : ", e);
      fail("No entity found exception");
    }
  }
}
