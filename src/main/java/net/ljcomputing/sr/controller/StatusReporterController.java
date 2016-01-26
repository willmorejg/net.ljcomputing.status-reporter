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

import net.ljcomputing.core.controler.ResponseMessage;
import net.ljcomputing.logging.annotation.LogEvent;
import net.ljcomputing.logging.annotation.LogResponse;
import net.ljcomputing.sr.domain.Activity;
import net.ljcomputing.sr.domain.WorkBreakdownStructure;
import net.ljcomputing.sr.service.StatusReporterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
  @LogEvent(showArgs = true)
  @LogResponse
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
  @LogEvent(showArgs = true)
  @LogResponse
  public WorkBreakdownStructure getWbsByUuid(@PathVariable String uuid)
      throws Exception {
    return srService.findWbsByUuid(uuid);
  }

  /**
   * Creates or updates the work breakdown structure.
   *
   * @param wbs the wbs
   * @return true, if successful
   * @throws Exception the exception
   */
  @RequestMapping(value = "/wbs", method = RequestMethod.POST)
  @LogEvent(showArgs = true)
  @LogResponse
  public WorkBreakdownStructure createOrUpdateWbs(
      @RequestBody WorkBreakdownStructure wbs) throws Exception {
    return srService.saveWbs(wbs);
  }

  /**
   * Delete the work breakdown structure.
   *
   * @param uuid the uuid
   * @return the response message
   * @throws Exception the exception
   */
  @RequestMapping(value = "/wbs/{uuid}", method = RequestMethod.DELETE)
  @LogEvent(showArgs = true)
  @LogResponse
  public ResponseMessage deleteWbs(@PathVariable String uuid) throws Exception {
    srService.removeWbs(uuid);

    return new ResponseMessage(true,
        "Successfully deleted work breakdown structure.");
  }

  /**
   * Creates or update an activity.
   *
   * @param wbsUuid the wbs uuid
   * @param activity the activity
   * @return the activity
   * @throws Exception the exception
   */
  @RequestMapping(value = "/wbs/{wbsUuid}/activity",
      method = RequestMethod.POST)
  @LogEvent(showArgs = true)
  @LogResponse
  public Activity createOrUpdateActivity(@PathVariable String wbsUuid,
      @RequestBody Activity activity) throws Exception {
    return srService.saveActivity(activity, wbsUuid);
  }

  /**
   * Gets the activity by uuid.
   *
   * @param uuid the uuid
   * @return the activity by uuid
   * @throws Exception the exception
   */
  @RequestMapping(value = "/activity/{uuid}", method = RequestMethod.GET)
  @LogEvent(showArgs = true)
  @LogResponse
  public Activity getActivityByUuid(@PathVariable String uuid)
      throws Exception {
    return srService.findActivityByUuid(uuid);
  }

  /**
   * Delete activity.
   *
   * @param uuid the uuid
   * @return the response message
   * @throws Exception the exception
   */
  @RequestMapping(value = "/activity/{uuid}", method = RequestMethod.DELETE)
  @LogEvent(showArgs = true)
  @LogResponse
  public ResponseMessage deleteActivity(@PathVariable String uuid)
      throws Exception {
    srService.removeActivity(uuid);

    return new ResponseMessage(true, "Successfully deleted activity.");
  }

}
