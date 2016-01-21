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
import net.ljcomputing.gson.converter.GsonConverterService;
import net.ljcomputing.logging.annotation.InjectLogging;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;

/**
 * Abstract domain service implementation.
 *
 * @author James G. Willmore
 * @param <T> the Domain type
 * @param <S> the PersistedEntity type
 * @param <ID> the ID (entity id) type
 * @param <R> the repository type
 */
public abstract class AbstractDomainEntityServiceImpl<T extends Domain, S extends PersistedEntity, ID extends Serializable, R extends BaseRepository<S, ID>>
    implements DomainEntityService<T, S, ID, R> {

  /** The logger. */
  @InjectLogging
  private static Logger logger;

  /** The repository. */
  @Autowired
  protected R repository;

  /** The strategy. */
  @Autowired
  protected DomainEntityConverterStrategy<T, S> strategy;

  /** The GSON converter service. */
  @Autowired
  protected GsonConverterService gsonConverterService;

  @Autowired
  protected Mapper mapper;

  /**
   * @see net.ljcomputing.core.service.DomainEntityService#createOrUpdate(net.ljcomputing.core.domain.Domain)
   */
  @Transactional
  public T createOrUpdate(final T domain)
      throws RequiredValueException, NoEntityFoundException {
    S entity = strategy.domainToEntity(domain);

    if (null != entity.getUuid()) {
      @SuppressWarnings("unchecked")
      S existing = repository.getOne((ID) entity.getUuid());

      if (null != existing) {
        entity.setCreatedTs(existing.getCreatedTs());
      }
    }

    return strategy.entityToDomain(repository.saveAndFlush(entity));
  }

  /**
   * @see net.ljcomputing.core.service.DomainEntityService#delete(java.io.Serializable)
   */
  @Transactional
  public boolean delete(ID id) {
    repository.delete(id);
    repository.flush();
    return true;
  }

  /**
   * @see net.ljcomputing.core.service.DomainEntityService#delete(net.ljcomputing.core.domain.Domain)
   */
  @SuppressWarnings("unchecked")
  public boolean delete(final T domain) {
    return delete((ID) domain.getUuid());
  }

  /**
   * @see net.ljcomputing.core.service.DomainEntityService#deleteAll()
   */
  @Transactional
  public boolean deleteAll() {
    repository.deleteAll();
    repository.flush();
    return true;
  }

  /**
   * @see net.ljcomputing.core.service.DomainEntityService#findAll()
   */
  @Transactional(readOnly = true)
  public List<T> findAll()
      throws RequiredValueException, NoEntityFoundException {
    List<T> list = new ArrayList<T>();

    for (S entity : repository.findAll()) {
      list.add(strategy.entityToDomain(entity));
    }

    return list;
  }

  /**
   * @see net.ljcomputing.core.service.DomainEntityService#findById(java.io.Serializable)
   */
  @Transactional(readOnly = true)
  public T findById(final ID id)
      throws RequiredValueException, NoEntityFoundException {
    return strategy.entityToDomain(repository.findOne(id));
  }
}
