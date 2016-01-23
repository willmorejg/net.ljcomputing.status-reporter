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

package net.ljcomputing.core.service;

import net.ljcomputing.core.domain.Domain;
import net.ljcomputing.core.entity.PersistedEntity;
import net.ljcomputing.core.exception.NoEntityFoundException;
import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.core.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Domain service interface.
 * 
 * @author James G. Willmore
 *
 */
public interface DomainEntityService<T extends Domain, S extends PersistedEntity, ID extends Serializable, R extends BaseRepository<S, ID>> {

  /**
   * Creates, or updates, the domain.
   *
   * @param domain the domain
   * @return the t
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
  T createOrUpdate(final T domain) throws RequiredValueException, NoEntityFoundException;

  /**
   * Delete entity by id.
   *
   * @param id the id
   * @return true, if successful
   */
  boolean delete(final ID id);

  /**
   * Delete entity by domain.
   *
   * @param domain the domain
   * @return true, if successful
   */
  boolean delete(final T domain);

  /**
   * Delete entities by domains.
   *
   * @param domains the domains
   * @return true, if successful
   */
  boolean delete(final List<T> domains);

  /**
   * Delete all entities.
   *
   * @return true, if successful
   */
  boolean deleteAll();

  /**
   * Find all domains in a associated repository.
   *
   * @return the list
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
  List<T> findAll() throws RequiredValueException, NoEntityFoundException;

  /**
   * Find a domain by id.
   *
   * @param id the id
   * @return the domain
   * @throws RequiredValueException the required value exception
   * @throws NoEntityFoundException the no entity found exception
   */
  T findById(final ID id) throws RequiredValueException, NoEntityFoundException;
}
