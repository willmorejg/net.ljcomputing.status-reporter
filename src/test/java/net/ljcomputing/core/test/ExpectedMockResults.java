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
 * @author James G. Willmore
 *
 */
public class ExpectedMockResults {
  private GsonConverterService gsonService;

  private String url;
  private String expectedPath;
  private String expectedValue;
  private Domain postedRequestBody;
  private String postedPathVariable;

  ExpectedMockResults() {
  }

  private ExpectedMockResults(Builder builder) {
    this.gsonService = builder.gsonService;
    this.url = builder.url;
    this.expectedPath = builder.expectedPath;
    this.expectedValue = builder.expectedValue;
    this.postedRequestBody = builder.postedRequestBody;
    this.postedPathVariable = builder.postedPathVariable;
  }

  private String jsonPayload() {
    if (null != postedRequestBody) {
      return gsonService.toJson(postedRequestBody);
    }

    return "";
  }

  private void jsonPayload(String json, Class<? extends Domain> klass) {
    if (null != json) {
      postedRequestBody = (Domain) gsonService.fromJson(json, klass);
    }
  }

  public String getUrl() {
    return url;
  }

  public String getExpectedPath() {
    return expectedPath;
  }

  public String getExpectedValue() {
    return expectedValue;
  }

  public Domain getPostedRequestBody() {
    return postedRequestBody;
  }

  public void updatePostedRequestBody(String json) {
    jsonPayload(json, postedRequestBody.getClass());
  }

  public String getDomainUuid() {
    if (null != postedRequestBody && null != postedRequestBody.getUuid()
        && !postedRequestBody.getUuid().trim().equals("")) {
      return postedRequestBody.getUuid();
    }

    return "";
  }

  public String getPostedPathVariable() {
    return postedPathVariable;
  }

  public String getJsonPayload() {
    return jsonPayload();
  }

  @Override
  public String toString() {
    return "ExpectedMockResults [url=" + url + ", expectedPath=" + expectedPath
        + ", expectedValue=" + expectedValue + ", postedRequestBody="
        + postedRequestBody + ", postedPathVariable=" + postedPathVariable
        + ", gsonService=" + gsonService + "]";
  }

  @Component
  public static class Builder {
    @Autowired
    private GsonConverterService gsonService;

    private String url;
    private String expectedPath;
    private String expectedValue;
    private Domain postedRequestBody;
    private String postedPathVariable;

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder expectedPath(String expectedPath) {
      this.expectedPath = expectedPath;
      return this;
    }

    public Builder expectedValue(String expectedValue) {
      this.expectedValue = expectedValue;
      return this;
    }

    public Builder postedRequestBody(Domain postedRequestBody) {
      this.postedRequestBody = postedRequestBody;
      return this;
    }

    public Builder postedPathVariable(String postedPathVariable) {
      this.postedPathVariable = postedPathVariable;
      return this;
    }

    public ExpectedMockResults build() {
      return new ExpectedMockResults(this);
    }
  }
}
