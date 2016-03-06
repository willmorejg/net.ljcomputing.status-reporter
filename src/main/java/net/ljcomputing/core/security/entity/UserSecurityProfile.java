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

package net.ljcomputing.core.security.entity;

import net.ljcomputing.core.domain.Domain;
import net.ljcomputing.core.entity.AbstractPersistedEntity;
import net.ljcomputing.core.entity.PersistedEntity;
import net.ljcomputing.core.security.domain.UserRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Represents an end user's security profile entity.
 * 
 * @author James G. Willmore
 *
 */
@Entity
@Table(name = "user_security_profile")
// @Cacheable
public class UserSecurityProfile extends AbstractPersistedEntity
    implements PersistedEntity, Domain {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -4211914630958128757L;

  /** The email. */
  private String email;

  /** The password hash. */
  private String passwordHash;

  /** The role. */
  private UserRole role;
  
  /** The expired. */
  private Boolean expired;
  
  /** The locked. */
  private Boolean locked;
  
  /** The enabled. */
  private Boolean enabled;

  /**
   * Gets the email.
   *
   * @return the email
   */
  @Column(name = "email", nullable = false, unique = true)
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email.
   *
   * @param email the new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the password hash.
   *
   * @return the password hash
   */
  @Column(name = "password_hash", nullable = false)
  public String getPasswordHash() {
    return passwordHash;
  }

  /**
   * Sets the password hash.
   *
   * @param passwordHash the new password hash
   */
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  /**
   * Gets the role.
   *
   * @return the role
   */
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  public UserRole getRole() {
    return role;
  }

  /**
   * Sets the role.
   *
   * @param role the new role
   */
  public void setRole(UserRole role) {
    this.role = role;
  }

  /**
   * Gets the expired.
   *
   * @return the expired
   */
  @Column(name = "expired", nullable = false)
  public Boolean getExpired() {
    return expired;
  }

  /**
   * Sets the expired.
   *
   * @param expired the new expired
   */
  public void setExpired(Boolean expired) {
    this.expired = expired;
  }

  /**
   * Gets the locked.
   *
   * @return the locked
   */
  @Column(name = "locked", nullable = false)
  public Boolean getLocked() {
    return locked;
  }

  /**
   * Sets the locked.
   *
   * @param locked the new locked
   */
  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  /**
   * Gets the enabled.
   *
   * @return the enabled
   */
  @Column(name = "enabled", nullable = false)
  public Boolean getEnabled() {
    return enabled;
  }

  /**
   * Sets the enabled.
   *
   * @param enabled the new enabled
   */
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * @see net.ljcomputing.core.entity.AbstractDomain#toString()
   */
  @Override
  public String toString() {
    return "UserSecurityProfile [email=" + email + ", passwordHash="
        + passwordHash + ", role=" + role + ", expired=" + expired + ", locked="
        + locked + ", enabled=" + enabled + ", " + super.toString()
        + "]";
  }

}
