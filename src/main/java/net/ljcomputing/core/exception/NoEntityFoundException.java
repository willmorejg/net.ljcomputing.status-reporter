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

package net.ljcomputing.core.exception;

/**
 * No entity found exception; thrown when a domain cannot be mapped with an 
 * existing entity during the conversion process.
 * 
 * @author James G. Willmore
 *
 */
public class NoEntityFoundException extends Exception {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 3278933387985568243L;

  /**
   * Instantiates a new required value exception.
   */
  public NoEntityFoundException() {
    super("No existing record found.");
  }

  /**
   * Instantiates a new required value exception.
   *
   * @param message the message
   */
  public NoEntityFoundException(String message) {
    super(message);
  }

  /**
   * Instantiates a new required value exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public NoEntityFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new required value exception.
   *
   * @param message the message
   * @param cause the cause
   * @param enableSuppression the enable suppression
   * @param writableStackTrace the writable stack trace
   */
  public NoEntityFoundException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Instantiates a new required value exception.
   *
   * @param cause the cause
   */
  public NoEntityFoundException(Throwable cause) {
    super(cause);
  }

}
