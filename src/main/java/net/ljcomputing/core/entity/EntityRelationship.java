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

/**
 * Interface defining a relationship between two entities.
 *
 * @author James G. Willmore
 * @param <T> the start entity of the relationship
 * @param <S> the end entity of the relationship
 */
public interface EntityRelationship<T extends PersistedEntity, S extends PersistedEntity>
    extends PersistedEntity {

  /**
   * Gets the relationship type.
   *
   * @return the type
   */
  String getType();
}
