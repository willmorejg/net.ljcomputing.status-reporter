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

package net.ljcomputing.sr.entity;

import net.ljcomputing.core.domain.Domain;
import net.ljcomputing.core.entity.AbstractPersistedEntity;
import net.ljcomputing.core.entity.PersistedEntity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Work breakdown structure entity.
 */
@Entity
@Table(name = "wbs")
// @Cacheable
public class WbsEntity extends AbstractPersistedEntity
    implements PersistedEntity, Domain {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -1205688570402312320L;

  /** The description. */
  private String description;

  /** The name. */
  private String name;

  /** The activities. */
  private List<ActivityEntity> activities;

  /**
   * Gets the description.
   *
   * @return the description
   */
  @Column(name = "description")
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Column(name = "name", insertable = true, updatable = false, nullable = false,
      unique = true)
  @NotNull(message = "name may not be null")
  @NotEmpty(message = "name may not be empty")
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the activities.
   *
   * @return the activities
   */
  @OneToMany(mappedBy = "wbs", fetch = FetchType.EAGER)
  @Fetch(FetchMode.JOIN)
  public List<ActivityEntity> getActivities() {
    return activities;
  }

  /**
   * Sets the activities.
   *
   * @param activities the new activities
   */
  public void setActivities(List<ActivityEntity> activities) {
    this.activities = activities;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.ljcomputing.core.entity.AbstractDomain#toString()
   */
  @Override
  public String toString() {
    return "WbsEntity [description=" + description + ", name=" + name
        + ", activities=" + activities + ", getCreatedTs()=" + getCreatedTs()
        + ", getModifiedTs()=" + getModifiedTs() + ", getUuid()=" + getUuid()
        + "]";
  }
}
