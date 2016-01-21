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
  private static List<WorkBreakdownStructure> wbsSaved;

  /** The Constant activities. */
  private static List<Activity> activities;

  /** The Constant events. */
  private static List<Event> events;

  @BeforeClass
  public static void aBeforClassMethod() {
    try {
      wbsS = new ArrayList<WorkBreakdownStructure>();
      activities = new ArrayList<Activity>();
      events = new ArrayList<Event>();

      int count = 1;
      wbsS.add(new WorkBreakdownStructure("WBS" + count++));
      wbsS.add(new WorkBreakdownStructure("WBS" + count++));
      wbsS.add(new WorkBreakdownStructure("WBS" + count++));
      wbsS.add(new WorkBreakdownStructure("WBS" + count++));
      wbsS.add(new WorkBreakdownStructure("WBS" + count++));

      count = 1;
      activities.add(new Activity("ACT" + count++));
      activities.add(new Activity("ACT" + count++));
      activities.add(new Activity("ACT" + count++));
      activities.add(new Activity("ACT" + count++));
      activities.add(new Activity("ACT" + count++));

      events.add(new Event());
      events.add(new Event());
      events.add(new Event());
      events.add(new Event());
      events.add(new Event());
    } catch (Exception e) {
      logger.error("FAILED to execute before class method : ", e);
    }
  }

  @Test
  public void test0() {
    try {
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
        savedEvent.description = "Event for activity "
            + savedActivity.getName();
        srService.saveEvent(savedEvent, savedActivity.getUuid());

        assertNotNull("saved event value was null", savedEvent);
      }

      List<WorkBreakdownStructure> results = srService.listAllWbs();

      for (WorkBreakdownStructure wbs : results) {
        List<Activity> savedActivities = srService
            .findActivitiesForWbs(wbs.getUuid());
        for (Activity activity : savedActivities) {
          logger.debug("     activity : {}", activity);
          List<Event> savedEvents = srService
              .findEventsForActivity(activity.getUuid());
          for (Event event : savedEvents) {
            logger.debug("          event : {}", event);
          }
        }
      }

      assertNotNull("gsonConverterService is null", gsonConverterService);

      logger.info("test results : {}",
          gsonConverterService.toJson(srService.listAllWbs()));

      wbsSaved = new ArrayList<WorkBreakdownStructure>();
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
  
  @Test
  public void test1() {
    try {
      for(WorkBreakdownStructure wbs : wbsSaved) {
        wbs.description = "Updated";
        for(Activity activity : wbs.getActivities()) {
          activity.description = "Updated";
          for(Event event : activity.getEvents()) {
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

      wbsSaved = new ArrayList<WorkBreakdownStructure>();
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
