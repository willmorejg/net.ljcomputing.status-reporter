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

package net.ljcomputing.core.strategy;

import net.ljcomputing.core.domain.Domain;
import net.ljcomputing.core.entity.PersistedEntity;
import net.ljcomputing.core.exception.NoEntityFoundException;
import net.ljcomputing.core.exception.RequiredValueException;
import net.ljcomputing.core.service.DomainEntityConverterStrategy;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;

//import org.modelmapper.ModelMapper;

/**
 * Abstract implementation of a domain entity converter strategy.
 *
 * @param <T> the domain generic type
 * @param <S> the entity generic type
 */
public abstract class AbstractDomainEntityStrategy<T extends Domain, S extends PersistedEntity>
    implements DomainEntityConverterStrategy<T, S> {

  // /** ModelMapper instance. */
  // @Autowired
  // protected ModelMapper mapper;

  @Autowired
  protected Mapper mapper;

  /**
   * @see net.ljcomputing.core.service.DomainEntityConverterStrategy#entityToDomain(java.util.List)
   */
  public List<T> entityToDomain(List<S> entities)
      throws RequiredValueException, NoEntityFoundException {
    List<T> domains = new ArrayList<T>();

    if (null != entities) {
      for (S entity : entities) {
        domains.add(entityToDomain(entity));
      }
    }

    return domains;
  }

  public abstract T entityToDomain(S entity) throws RequiredValueException, NoEntityFoundException;

  /**
   * @see net.ljcomputing.core.service.DomainEntityConverterStrategy#domainToEntity(java.util.List)
   */
  public List<S> domainToEntity(List<T> domains) throws RequiredValueException {
    List<S> entities = new ArrayList<S>();

    if (null != domains) {
      for (T domain : domains) {
        entities.add(domainToEntity(domain));
      }
    }

    return entities;
  }

  /**
   * @see net.ljcomputing.core.service.DomainEntityConverterStrategy#domainToEntity(java.lang.Object)
   */
  public abstract S domainToEntity(T domain) throws RequiredValueException;
}
