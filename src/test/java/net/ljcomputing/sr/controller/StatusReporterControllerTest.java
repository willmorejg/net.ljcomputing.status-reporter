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

package net.ljcomputing.sr.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.core.test.CommonClassUnitTest;
import net.ljcomputing.core.test.CommonUnitTest;
import net.ljcomputing.core.test.ExpectedMockResults;
import net.ljcomputing.gson.converter.GsonConverterService;
import net.ljcomputing.logging.annotation.InjectLogging;
import net.ljcomputing.sr.StatusReporter;
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.domain.WorkBreakdownStructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Status Reporter controller JUnit tests.
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
public class StatusReporterControllerTest {
  /** The logger. */
  @InjectLogging
  private static Logger logger = LoggerFactory
      .getLogger(StatusReporterControllerTest.class);

  /** The common class unit test. */
      @ClassRule
  public static CommonClassUnitTest commonClassUnitTest = new CommonClassUnitTest();

  /** The common unit test. */
  @Rule
  public CommonUnitTest commonUnitTest = new CommonUnitTest();

  /** The mock mvc. */
  private MockMvc mockMvc;

  /** The context. */
  @Autowired
  private WebApplicationContext context;

  @Autowired
  private GsonConverterService gsonService;

  /** The Status Reporter controller. */
  @InjectMocks
  private StatusReporterController srController;

  /** The expected builder. */
  @Autowired
  private ExpectedMockResults.Builder expectedBuilder;

  /** The expected results. */
  private static ExpectedMockResults expectedResults;

  /** The wbs. */
  private static WorkBreakdownStructure wbs;

  @BeforeClass
  public static void beforeClass() {
    try {
      wbs = new WorkBreakdownStructure("TEST WBS");
      wbs.description = "Testing WBS";

      Activity activity = new Activity("TEST ACTIVITY");
      activity.description = "Testing activity";

      wbs.addActivity(activity);
    } catch (RequiredValueException e) {
      logger.error("failed to create wbs - other tests will fail : ", e);
    }
  }

  /**
   * Sets up the JUnit test.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    if (null == expectedResults) {
      expectedBuilder.url("/sr/wbs");
      expectedBuilder.postedRequestBody(wbs);

      expectedResults = expectedBuilder.build();
    }
  }

  @Test
  public void test000CreateWbs() {
    try {
      MockHttpServletRequestBuilder requestBuilder = post(
          expectedResults.getUrl());
      requestBuilder.contentType(MediaType.APPLICATION_JSON);
      requestBuilder.content(expectedResults.getJsonPayload());

      ResultActions result = mockMvc.perform(requestBuilder);
      MvcResult mvcResult = result.andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();

      assertTrue("failed to create wbs",
          response.getStatus() >= 200 && response.getStatus() <= 299);

      String jsonResponse = response.getContentAsString();

      expectedResults.updatePostedRequestBody(jsonResponse);
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }

  @Test
  public void test010CreateActivity() {
    try {
      String wbsUuid = expectedResults.getPostedRequestBody().getUuid();
      Activity activity = wbs.getActivities().get(0);
      String activityJson = gsonService.toJson(activity);
      String url = expectedResults.getUrl() + "/" + wbsUuid + "/activity";

      MockHttpServletRequestBuilder requestBuilder = post(url);
      requestBuilder.contentType(MediaType.APPLICATION_JSON);
      requestBuilder.content(activityJson);

      ResultActions result = mockMvc.perform(requestBuilder);
      MvcResult mvcResult = result.andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();

      assertTrue("failed to create activity",
          response.getStatus() >= 200 && response.getStatus() <= 299);

      logger.debug(response.getContentAsString());

      url = expectedResults.getUrl() + "/" + wbsUuid;
      requestBuilder = get(url);
      result = mockMvc.perform(requestBuilder);
      mvcResult = result.andReturn();
      response = mvcResult.getResponse();
      String jsonResponse = response.getContentAsString();
      expectedResults.updatePostedRequestBody(jsonResponse);
      logger.debug("expectedResults.getPostedRequestBody() : {}",
          expectedResults.getPostedRequestBody());
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }

  @Test
  public void test014GetActivityByUuid() {
    try {
      WorkBreakdownStructure persistedWbs = (WorkBreakdownStructure) expectedResults
          .getPostedRequestBody();
      Activity activity = persistedWbs.getActivities().get(0);
      String uuid = activity.getUuid();
      String url = "/sr/" + persistedWbs.getUuid() + "/activity/" + uuid;

      MockHttpServletRequestBuilder requestBuilder = get(url);
      requestBuilder.contentType(MediaType.APPLICATION_JSON);

      ResultActions result = mockMvc.perform(requestBuilder);
      MvcResult mvcResult = result.andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();

      assertTrue("failed to get activity by uuid",
          response.getStatus() >= 200 && response.getStatus() <= 299);

      logger.debug(response.getContentAsString());
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }

  /**
   * Get all the work breakdown structures.
   */
  @Test
  public void test080GetAllWbs() {
    try {
      MockHttpServletRequestBuilder requestBuilder = get(
          expectedResults.getUrl());
      requestBuilder.contentType(MediaType.APPLICATION_JSON);

      ResultActions result = mockMvc.perform(requestBuilder);
      MvcResult mvcResult = result.andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();

      assertTrue("failed to get all wbs",
          response.getStatus() >= 200 && response.getStatus() <= 299);

      logger.debug(response.getContentAsString());
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }

  @Test
  public void test097DeleteActivity() {
    try {
      WorkBreakdownStructure persistedWbs = (WorkBreakdownStructure) expectedResults
          .getPostedRequestBody();
      Activity activity = persistedWbs.getActivities().get(0);
      String uuid = activity.getUuid();
      String url = "/sr/" + persistedWbs.getUuid() + "/activity/" + uuid;

      MockHttpServletRequestBuilder requestBuilder = delete(url);
      requestBuilder.contentType(MediaType.APPLICATION_JSON);

      ResultActions result = mockMvc.perform(requestBuilder);
      MvcResult mvcResult = result.andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();

      assertTrue("failed to delete activity by uuid",
          response.getStatus() >= 200 && response.getStatus() <= 299);

      logger.debug(response.getContentAsString());
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }


  @Test
  public void test100GetAllWbs() {
    try {
      MockHttpServletRequestBuilder requestBuilder = get(
          expectedResults.getUrl());
      requestBuilder.contentType(MediaType.APPLICATION_JSON);

      ResultActions result = mockMvc.perform(requestBuilder);
      MvcResult mvcResult = result.andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();

      assertTrue("failed to get all wbs",
          response.getStatus() >= 200 && response.getStatus() <= 299);

      logger.debug(response.getContentAsString());
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }

  @Test
  public void test199DeleteWbs() {
    try {
      String uuid = expectedResults.getDomainUuid();
      MockHttpServletRequestBuilder requestBuilder = delete(
          expectedResults.getUrl() + "/" + uuid);
      ResultActions result = mockMvc.perform(requestBuilder);
      MvcResult mvcResult = result.andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();

      assertTrue("failed to delete wbs",
          response.getStatus() >= 200 && response.getStatus() <= 299);
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }

}
