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
import net.ljcomputing.gson.annotation.ExcludeFromJson;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Activity entity.
 */
@Entity
@Table(name = "activity")
// @Cacheable
public class ActivityEntity extends AbstractPersistedEntity
    implements PersistedEntity, Domain {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 2274287008476627493L;

  /** The description. */
  private String description;

  /** The name. */
  private String name;

  /** The wbs. */
  @ExcludeFromJson
  private WbsEntity wbs;

  /** The events. */
  private List<EventEntity> events;

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
   * Gets the wbs.
   *
   * @return the wbs
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wbs_uuid", referencedColumnName = "uuid")
  public WbsEntity getWbs() {
    return wbs;
  }

  /**
   * Sets the wbs.
   *
   * @param wbs the new wbs
   */
  public void setWbs(WbsEntity wbs) {
    this.wbs = wbs;
  }

  /**
   * Gets the events.
   *
   * @return the events
   */
  @OneToMany(mappedBy = "activity", fetch = FetchType.EAGER)
  @Fetch(FetchMode.JOIN)
  public List<EventEntity> getEvents() {
    return events;
  }

  /**
   * Sets the events.
   *
   * @param events the new events
   */
  public void setEvents(List<EventEntity> events) {
    this.events = events;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.ljcomputing.core.entity.AbstractDomain#toString()
   */
  @Override
  public String toString() {
    return "ActivityEntity [description=" + description + ", name=" + name
        + ", events=" + events + ", getCreatedTs()=" + getCreatedTs()
        + ", getModifiedTs()=" + getModifiedTs() + ", getUuid()=" + getUuid()
        + "]";
  }
}
