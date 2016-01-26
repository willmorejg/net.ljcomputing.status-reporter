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

import net.ljcomputing.core.domain.Domain;
import net.ljcomputing.gson.converter.GsonConverterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class for the expected mocked results.
 *
 * @author James G. Willmore
 */
public class ExpectedMockResults {
  
  /** The gson service. */
  private GsonConverterService gsonService;

  /** The url. */
  private String url;
  
  /** The expected path. */
  private String expectedPath;
  
  /** The expected value. */
  private String expectedValue;
  
  /** The posted request body. */
  private Domain postedRequestBody;
  
  /** The posted path variable. */
  private String postedPathVariable;

  /**
   * Instantiates a new expected mock results.
   */
  ExpectedMockResults() {
  }

  /**
   * Instantiates a new expected mock results.
   *
   * @param builder the builder
   */
  private ExpectedMockResults(Builder builder) {
    this.gsonService = builder.gsonService;
    this.url = builder.url;
    this.expectedPath = builder.expectedPath;
    this.expectedValue = builder.expectedValue;
    this.postedRequestBody = builder.postedRequestBody;
    this.postedPathVariable = builder.postedPathVariable;
  }

  /**
   * Json payload.
   *
   * @return the string
   */
  private String jsonPayload() {
    if (null != postedRequestBody) {
      return gsonService.toJson(postedRequestBody);
    }

    return "";
  }

  /**
   * Json payload.
   *
   * @param json the json
   * @param klass the klass
   */
  private void jsonPayload(String json, Class<? extends Domain> klass) {
    if (null != json) {
      postedRequestBody = (Domain) gsonService.fromJson(json, klass);
    }
  }

  /**
   * Gets the url.
   *
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Gets the expected path.
   *
   * @return the expected path
   */
  public String getExpectedPath() {
    return expectedPath;
  }

  /**
   * Gets the expected value.
   *
   * @return the expected value
   */
  public String getExpectedValue() {
    return expectedValue;
  }

  /**
   * Gets the posted request body.
   *
   * @return the posted request body
   */
  public Domain getPostedRequestBody() {
    return postedRequestBody;
  }

  /**
   * Update posted request body.
   *
   * @param json the json
   */
  public void updatePostedRequestBody(String json) {
    jsonPayload(json, postedRequestBody.getClass());
  }

  /**
   * Gets the domain uuid.
   *
   * @return the domain uuid
   */
  public String getDomainUuid() {
    if (null != postedRequestBody && null != postedRequestBody.getUuid()
        && !postedRequestBody.getUuid().trim().equals("")) {
      return postedRequestBody.getUuid();
    }

    return "";
  }

  /**
   * Gets the posted path variable.
   *
   * @return the posted path variable
   */
  public String getPostedPathVariable() {
    return postedPathVariable;
  }

  /**
   * Gets the json payload.
   *
   * @return the json payload
   */
  public String getJsonPayload() {
    return jsonPayload();
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ExpectedMockResults [url=" + url + ", expectedPath=" + expectedPath
        + ", expectedValue=" + expectedValue + ", postedRequestBody="
        + postedRequestBody + ", postedPathVariable=" + postedPathVariable
        + ", gsonService=" + gsonService + "]";
  }

  /**
   * Builder for the expected mocked results.
   */
  @Component
  public static class Builder {
    
    /** The gson service. */
    @Autowired
    private GsonConverterService gsonService;

    /** The url. */
    private String url;
    
    /** The expected path. */
    private String expectedPath;
    
    /** The expected value. */
    private String expectedValue;
    
    /** The posted request body. */
    private Domain postedRequestBody;
    
    /** The posted path variable. */
    private String postedPathVariable;

    /**
     * Url.
     *
     * @param url the url
     * @return the builder
     */
    public Builder url(String url) {
      this.url = url;
      return this;
    }

    /**
     * Expected path.
     *
     * @param expectedPath the expected path
     * @return the builder
     */
    public Builder expectedPath(String expectedPath) {
      this.expectedPath = expectedPath;
      return this;
    }

    /**
     * Expected value.
     *
     * @param expectedValue the expected value
     * @return the builder
     */
    public Builder expectedValue(String expectedValue) {
      this.expectedValue = expectedValue;
      return this;
    }

    /**
     * Posted request body.
     *
     * @param postedRequestBody the posted request body
     * @return the builder
     */
    public Builder postedRequestBody(Domain postedRequestBody) {
      this.postedRequestBody = postedRequestBody;
      return this;
    }

    /**
     * Posted path variable.
     *
     * @param postedPathVariable the posted path variable
     * @return the builder
     */
    public Builder postedPathVariable(String postedPathVariable) {
      this.postedPathVariable = postedPathVariable;
      return this;
    }

    /**
     * Builds the expected mock results.
     *
     * @return the expected mock results
     */
    public ExpectedMockResults build() {
      return new ExpectedMockResults(this);
    }
  }
}
