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
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.google.common.base.Stopwatch;

// TODO: Auto-generated Javadoc
/**
 * Class providing uniform unit test functionality.
 * 
 * @author James G. Willmore
 *
 */
public class CommonClassUnitTest implements TestRule {

  /** The logger. */
  private static Logger logger = LoggerFactory
      .getLogger(CommonClassUnitTest.class);

  /**
   * Apply.
   *
   * @param base the base
   * @param description the description
   * @return the statement
   * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement, org.junit.runner.Description)
   */
  @Override
  public Statement apply(final Statement base, final Description description) {
    return new CommonUnitTestStatement(base);
  }

  /**
   * The Class CommonUnitTestStatement.
   */
  public static class CommonUnitTestStatement extends Statement {

    /** The statement. */
    private final Statement statement;

    /** The class stopwatch. */
    private final Stopwatch classStopwatch = Stopwatch.createUnstarted();

    /**
     * Instantiates a new common unit test statement.
     *
     * @param statement the statement
     */
    public CommonUnitTestStatement(Statement statement) {
      this.statement = statement;
    }

    /**
     * Evaluate.
     *
     * @throws Throwable the throwable
     * @see org.junit.runners.model.Statement#evaluate()
     */
    @Override
    public void evaluate() throws Throwable {
      logger.info("<<<<< == START TEST CLASS == >>>>>");
      classStopwatch.start();

      try {
        statement.evaluate();
      } finally {
        classStopwatch.stop();

        long elapsed = classStopwatch.elapsed(TimeUnit.MILLISECONDS);

        //TODO ugly - should refactor
        logger.info("Elapsed time of test class: {}.{} seconds ({} ms)",
            (TimeUnit.MILLISECONDS.toSeconds(elapsed)
                % TimeUnit.MINUTES.toSeconds(1)),
            (TimeUnit.MILLISECONDS.toMillis(elapsed)
                % TimeUnit.MILLISECONDS.toMillis(1000)),
            elapsed);

        logger.info("<<<<< ==  END  TEST CLASS == >>>>>");
      }
    }
  }
}
