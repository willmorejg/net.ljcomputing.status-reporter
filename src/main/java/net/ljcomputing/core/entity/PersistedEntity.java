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

package net.ljcomputing.core.entity;

import net.ljcomputing.core.domain.Node;

import java.io.Serializable;

/**
 * Interface for entity classes. The interface ensures that there will be an id
 * attribute for an entity.
 * 
 * @author James G. Willmore
 *
 */
public interface PersistedEntity extends Serializable, Node {
  /**
   * Gets the created time stamp as a long value.
   *
   * @return the created time stamp as a long value
   */
  Long getCreatedTs();

  /**
   * Gets the modified ts.
   *
   * @return the modified ts
   */
  Long getModifiedTs();

  /**
   * Sets the created time stamp as a long value.
   *
   * @param createdTs the created time stamp as a long value
   */
  void setCreatedTs(Long createdTs);

  /**
   * Sets the modified ts.
   *
   * @param modifiedTs the new modified ts
   */
  void setModifiedTs(Long modifiedTs);

  /**
   * Creates the indexed value.
   */
  // void createIndexedValue();

  /**
   * Gets the indexed value.
   *
   * @return the indexed value
   */
  // String getIndexedValue();

  /**
   * Indicates if entity has all required values.
   *
   * @return the boolean
   */
  // Boolean isValid();

  /**
   * Gets the domain representation of the entity.
   *
   * @return the domain
   */
  // public Domain getDomain();
}
