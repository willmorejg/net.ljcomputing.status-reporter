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

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.core.test.ExpectedMockResults;
import net.ljcomputing.logging.annotation.InjectLogging;
import net.ljcomputing.sr.StatusReporter;
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
import org.junit.FixMethodOrder;
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

  /** The mock mvc. */
  private MockMvc mockMvc;

  /** The context. */
  @Autowired
  private WebApplicationContext context;

  /** The Status Reporter controller. */
  @InjectMocks
  private StatusReporterController srController;
  
  @Autowired
  private ExpectedMockResults.Builder expectedBuilder;

  private static ExpectedMockResults expectedResults;

  /** The wbs. */
  private static WorkBreakdownStructure wbs;

  @BeforeClass
  public static void beforeClass() {
    try {
      wbs = new WorkBreakdownStructure("TEST WBS");
      wbs.description = "Testing WBS";
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
    
    if(null == expectedResults) {
      expectedBuilder.url("/sr/wbs");
      expectedBuilder.postedRequestBody(wbs);

      expectedResults = expectedBuilder.build();
    }
  }

  @Test
  public void test0CreateWbs() {
    try {
      MockHttpServletRequestBuilder requestBuilder = post(
          expectedResults.getUrl());
      requestBuilder.contentType(MediaType.APPLICATION_JSON);
      requestBuilder.content(expectedResults.getJsonPayload());

      ResultActions result = mockMvc.perform(requestBuilder);
      MvcResult mvcResult = result.andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();

      result.andExpect(status().isOk());

      String jsonResponse = response.getContentAsString();

      expectedResults.updatePostedRequestBody(jsonResponse);
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }

  /**
   * Get all the work breakdown structures.
   */
  @Test
  public void test1GetAllWbs() {
    try {
      ResultActions result = mockMvc.perform(get(expectedResults.getUrl()));

      MvcResult mvcResult = result.andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();

      logger.debug(response.getContentAsString());
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }

  @Test
  public void test99DeleteAllWbs() {
    try {
      String uuid = expectedResults.getDomainUuid();
      /*ResultActions result = */mockMvc.perform(delete(expectedResults.getUrl() + "/" + uuid));

      //MvcResult mvcResult = result.andReturn();
      //MockHttpServletResponse response = mvcResult.getResponse();
    } catch (Exception e) {
      logger.error("test failed : ", e);
      fail(e.toString());
    }
  }

}
