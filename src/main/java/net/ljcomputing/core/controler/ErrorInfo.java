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

import org.springframework.http.HttpStatus;

/**
 * Error information.
 * 
 * @author James G. Willmore
 */
public class ErrorInfo {
	
	/** The timestamp. */
	public final String timestamp;
	
	/** The status. */
	public final String status;
	
	/** The error. */
	public final String error;
	
	/** The message. */
	public final String message;

	/** The path. */
	public final String path;

	/**
	 * Instantiates a new error info.
	 *
	 * @param timestamp the timestamp
	 * @param status the status
	 * @param path the path
	 * @param exception the exception
	 */
	public ErrorInfo(String timestamp, HttpStatus status, String path, Exception exception) {
		this.timestamp = timestamp;
		this.status = Integer.valueOf(status.value()).toString();
		this.error = status.getReasonPhrase();
		this.path = path;

		if (null != exception.getLocalizedMessage()) {
			this.message = exception.getLocalizedMessage();
		} else if (null != exception.getMessage()) {
			this.message = exception.getMessage();
		} else {
			this.message = "An error occured during processing: " + exception.toString();
		}
	}
}
