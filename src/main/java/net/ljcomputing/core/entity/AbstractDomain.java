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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * Abstract implementation of a domain class.
 * 
 * @author James G. Willmore
 *
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class AbstractDomain implements Node {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -5596912472711861850L;

  /** The uuid. */
  private String uuid;

  /**
   * 
   * @see net.ljcomputing.core.domain.Domain#getUuid()
   */
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @Column(name = "uuid", nullable = false, unique = true, length = 36,
      insertable = true, updatable = false)
  public String getUuid() {
    return uuid;
  }

  /**
   * 
   * @see net.ljcomputing.core.domain.Domain#setUuid(java.lang.String)
   */
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AbstractDomain [uuid=" + uuid + "]";
  }
}
