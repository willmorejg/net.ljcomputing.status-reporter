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

import net.ljcomputing.logging.annotation.InjectLogging;
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.domain.WorkBreakdownStructure;
import net.ljcomputing.sr.service.StatusReporterService;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Main Status Reporter controller.
 * 
 * @author James G. Willmore
 *
 */
@RestController
@RequestMapping("/sr")
public class StatusReporterController {

  /** The logger. */
  @SuppressWarnings("unused")
  @InjectLogging
  private static Logger logger;

  /** The status reporter service. */
  @Autowired
  private StatusReporterService srService;

  /**
   * Gets the all work breakdown structures.
   *
   * @return the all wbs
   * @throws Exception the exception
   */
  @RequestMapping(value = "/wbs", method = RequestMethod.GET)
  public List<WorkBreakdownStructure> getAllWbs() throws Exception {
    return srService.listAllWbs();
  }

  /**
   * Gets the work breakdown structure by uuid.
   *
   * @param uuid the uuid
   * @return the wbs by uuid
   * @throws Exception the exception
   */
  @RequestMapping(value = "/wbs/{uuid}", method = RequestMethod.GET)
  public WorkBreakdownStructure getWbsByUuid(@PathVariable String uuid)
      throws Exception {
    return srService.findWbsByUuid(uuid);
  }

  /**
   * Gets the activity by uuid.
   *
   * @param uuid the uuid
   * @return the activity by uuid
   * @throws Exception the exception
   */
  @RequestMapping(value = "/wbs/activity/{uuid}", method = RequestMethod.GET)
  public Activity getActivityByUuid(@PathVariable String uuid)
      throws Exception {
    return srService.findActivityByUuid(uuid);
  }
}
