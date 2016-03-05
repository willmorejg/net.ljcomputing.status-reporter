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

package net.ljcomputing.core.controler;

import net.ljcomputing.core.exception.NoEntityFoundException;
import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.logging.annotation.InjectLogging;

import org.slf4j.Logger;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Global exception handler for controllers.
 * 
 * @author James G. Willmore
 *
 */
@ControllerAdvice
public class GlobalExceptionController {

  /** The Constant logger. */
  @InjectLogging
  private static Logger logger;

  /** The timestamp format. */
  private final static DateTimeFormatter FORMAT = DateTimeFormat
      .forPattern("MMM d, yyyy h:m:s a");

  /**
   * Gets the current timestamp.
   *
   * @return the current timestamp
   */
  private String getCurrentTimestamp() {
    return FORMAT.print(new DateTime());
  }

  /**
   * Handle all exceptions.
   *
   * @param req
   *            the req
   * @param exception
   *            the exception
   * @return the error info
   */
  @Order(Ordered.LOWEST_PRECEDENCE)
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorInfo handleAllExceptions(HttpServletRequest req,
      Exception exception) {
    logger.error("An error occured during the processing of {}:",
        req.getRequestURL().toString(), exception);

    return new ErrorInfo(getCurrentTimestamp(), HttpStatus.BAD_REQUEST,
        req.getRequestURL().toString(), exception);
  }

//  /**
//   * Handle all access denied exceptions.
//   *
//   * @param req the req
//   * @param exception the exception
//   * @return the error info
//   */
//  @Order(Ordered.HIGHEST_PRECEDENCE)
//  @ExceptionHandler(AccessDeniedException.class)
//  @ResponseStatus(HttpStatus.UNAUTHORIZED)
//  public @ResponseBody ErrorInfo handleAllAccessDeniedExceptions(
//      HttpServletRequest req, Exception exception) {
//    logger.error("An error occured during the processing of {}:",
//        req.getRequestURL().toString(), exception);
//
//    return new ErrorInfo(getCurrentTimestamp(), HttpStatus.UNAUTHORIZED,
//        req.getRequestURL().toString(), exception);
//  }

  /**
   * Handle all null pointer exceptions.
   *
   * @param req the req
   * @param exception the exception
   * @return the error info
   */
  @Order(Ordered.HIGHEST_PRECEDENCE)
  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorInfo handleAllNullPointerExceptions(
      HttpServletRequest req, Exception exception) {
    logger.error("The data sent for processing had errors {}:",
        req.getRequestURL().toString(), exception);

    return new ErrorInfo(getCurrentTimestamp(), HttpStatus.BAD_REQUEST,
        req.getRequestURL().toString(),
        new Exception("An invalid value was sent or requested."));
  }

  /**
   * Handle all required value exceptions.
   *
   * @param req the req
   * @param exception the exception
   * @return the error info
   */
  @Order(Ordered.HIGHEST_PRECEDENCE)
  @ExceptionHandler(RequiredValueException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorInfo handleAllRequiredValueExceptions(
      HttpServletRequest req, Exception exception) {
    logger.warn("A required value is missing : {}:",
        req.getRequestURL().toString());

    return new ErrorInfo(getCurrentTimestamp(), HttpStatus.BAD_REQUEST,
        req.getRequestURL().toString(), exception);
  }

  /**
   * Handle all no entity found exceptions.
   *
   * @param req the req
   * @param exception the exception
   * @return the error info
   */
  @Order(Ordered.HIGHEST_PRECEDENCE)
  @ExceptionHandler(NoEntityFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public @ResponseBody ErrorInfo handleAllNoEntityFoundExceptions(
      HttpServletRequest req, Exception exception) {
    logger.warn("No entity found : {}:", req.getRequestURL().toString());

    return new ErrorInfo(getCurrentTimestamp(), HttpStatus.NOT_FOUND,
        req.getRequestURL().toString(), exception);
  }

  /**
   * Handle all constraint violation exceptions.
   *
   * @param req the req
   * @param exception the exception
   * @return the error info
   */
  @Order(Ordered.HIGHEST_PRECEDENCE)
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorInfo handleAllConstraintViolationExceptions(
      HttpServletRequest req, Exception exception) {
    ConstraintViolationException cve = (ConstraintViolationException) exception;

    logger.warn("A required value is missing : {}:",
        req.getRequestURL().toString());

    return new ErrorInfo(getCurrentTimestamp(), HttpStatus.BAD_REQUEST,
        req.getRequestURL().toString(), cve);
  }

  /**
   * Handle all data integrity violation exceptions.
   *
   * @param req
   *            the req
   * @param exception
   *            the exception
   * @return the error info
   */
  @Order(Ordered.HIGHEST_PRECEDENCE)
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public @ResponseBody ErrorInfo handleAllDataIntegrityViolationExceptions(
      HttpServletRequest req, Exception exception) {
    logger.error("The data sent for processing had errors {}:",
        req.getRequestURL().toString(), exception);

    if (exception.getMessage().contains("Unique property")) {
      return new ErrorInfo(getCurrentTimestamp(), HttpStatus.CONFLICT,
          req.getRequestURL().toString(),
          new Exception("The saved value already exists."));
    }

    return new ErrorInfo(getCurrentTimestamp(), HttpStatus.CONFLICT,
        req.getRequestURL().toString(), exception);
  }
}
