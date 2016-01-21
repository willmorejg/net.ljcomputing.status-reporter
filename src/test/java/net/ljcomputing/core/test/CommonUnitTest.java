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

package net.ljcomputing.core.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.google.common.base.Stopwatch;

/**
 * Class providing uniform unit test functionality.
 * 
 * @author James G. Willmore
 *
 */
public class CommonUnitTest extends TestWatcher implements TestRule {

  /** The logger. */
  private static Logger logger = LoggerFactory.getLogger(CommonUnitTest.class);

  /** The test stopwatch. */
  private final Stopwatch testStopwatch = Stopwatch.createUnstarted();

  /**
   * @see org.junit.rules.TestWatcher#starting(org.junit.runner.Description)
   */
  protected void starting(Description description) {
    logger.info("===== BEGIN {} [{}] =====", description.getMethodName(),
        description.getClassName());
    testStopwatch.start();
  }

  /**
   * @see org.junit.rules.TestWatcher#finished(org.junit.runner.Description)
   */
  protected void finished(Description description) {
    testStopwatch.stop();

    long elapsed = testStopwatch.elapsed(TimeUnit.MILLISECONDS);

    logger.info("Elapsed time: {} ms", elapsed);
    logger.info("=====  END  {} [{}] =====", description.getMethodName(),
        description.getClassName());
  }
}
