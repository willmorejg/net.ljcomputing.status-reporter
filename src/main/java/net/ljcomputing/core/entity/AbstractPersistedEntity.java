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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

/**
 * Abstract implementation of a persisted entity class.
 * 
 * @author James G. Willmore
 *
 */
@MappedSuperclass
public abstract class AbstractPersistedEntity extends AbstractDomain
    implements PersistedEntity {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -5596912472711861850L;

  /** The created ts. */
  private Long createdTs;

  /** The modified ts. */
  private Long modifiedTs;

  /**
   * @see net.ljcomputing.core.entity.PersistedEntity#getCreatedTs()
   */
  @Column(name = "created_ts", nullable = false, unique = false,
      insertable = true, updatable = false)
  @NotNull(message = "createdTS may not be null")
  public Long getCreatedTs() {
    return createdTs;
  }

  /**
   * @see net.ljcomputing.core.entity.PersistedEntity#setCreatedTs(java.lang.Long)
   */
  public void setCreatedTs(Long createdTs) {
    this.createdTs = createdTs;
  }

  /**
   * @see net.ljcomputing.core.entity.PersistedEntity#getModifiedTs()
   */
  @Column(name = "modified_ts", nullable = true, unique = false,
      insertable = false, updatable = true)
  public Long getModifiedTs() {
    return modifiedTs;
  }

  /**
   * @see net.ljcomputing.core.entity.PersistedEntity#setModifiedTs(java.lang.Long)
   */
  public void setModifiedTs(Long modifiedTs) {
    this.modifiedTs = modifiedTs;
  }

  /**
   * Update the created time stamp with the current date.
   */
  @SuppressWarnings("unused")
  @PrePersist
  private void createdAt() {
    this.createdTs = new Date().getTime();
  }

  /**
   * Update the modified time stamp with the current date.
   */
  @SuppressWarnings("unused")
  @PreUpdate
  private void modifiedAt() {
    this.modifiedTs = new Date().getTime();
  }
}
