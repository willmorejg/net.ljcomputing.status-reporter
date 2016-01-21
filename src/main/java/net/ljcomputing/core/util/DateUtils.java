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

package net.ljcomputing.core.util;

import java.util.Properties;

import org.joda.time.DateTime;

/**
 * @author James G. Willmore
 *
 */
public final class DateUtils {

  /**
   * Parses the date string.
   *
   * @param dateAsString the date as string
   * @return the properties
   */
  private static final Properties parseDateString(final String dateAsString) {
    String year = dateAsString.substring(0, 4);
    String month = dateAsString.substring(4, 6);
    String day = dateAsString.substring(6, 8);

    Properties props = new Properties();
    props.setProperty("year", year);
    props.setProperty("month", month);
    props.setProperty("day", day);

    return props;
  }

  /**
   * Gets the property as integer.
   *
   * @param properties the properties
   * @param key the key
   * @return the property as integer
   */
  private static final Integer getPropertyAsInteger(final Properties properties,
      final String key) {
    return Integer.parseInt(properties.getProperty(key));
  }

  /**
   * Date string (in the pattern <i>YYYYMMdd</i>) to start epoch days prior to 
   * the given days prior.
   *
   * @param dateAsString the date as string
   * @param daysPrior the days prior
   * @return the long
   */
  public static final long dateStringToStart(final String dateAsString,
      int daysPrior) {
    Properties props = parseDateString(dateAsString);

    return new DateTime().withYear(getPropertyAsInteger(props, "year"))
        .withMonthOfYear(getPropertyAsInteger(props, "month"))
        .withDayOfMonth(getPropertyAsInteger(props, "day")).minusDays(daysPrior)
        .withTime(0, 0, 0, 0).getMillis();
  }

  /**
   * Date string (in the pattern <i>YYYYMMdd</i>) to start epoch.
   *
   * @param dateAsString the date as string
   * @return the long
   */
  public static final long dateStringToStart(final String dateAsString) {
    return dateStringToStart(dateAsString, 0);
  }

  /**
   * Date string (in the pattern <i>YYYYMMdd</i>) to end epoch.
   *
   * @param dateAsString the date as string
   * @return the long
   */
  public static final long dateStringToEnd(final String dateAsString) {
    Properties props = parseDateString(dateAsString);

    return new DateTime().withYear(getPropertyAsInteger(props, "year"))
        .withMonthOfYear(getPropertyAsInteger(props, "month"))
        .withDayOfMonth(getPropertyAsInteger(props, "day"))
        .withTime(23, 59, 59, 999).getMillis();
  }
}
