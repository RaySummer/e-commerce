package com.eco.commerce.core.module.base.service;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ray
 * @since 2023/02/14
 **/
public interface BaseCrudService<T1, ID extends Serializable> {

    T1 findById(ID id);

    T1 findByReference(String reference);

    T1 findByUid(UUID uniqueId);

    /**
     * 通过UID 查找, 查找不到 throw NotFoundException
     *
     * @param uniqueId uniqueId
     * @see #findByUidOptional(String)
     */
    T1 findByUid(String uniqueId);


    /**
     * 通过UID 查找, 需要自己判断是否为Null
     *
     * @param uniqueId uniqueId
     * @see #findByUid(String)
     */
    Optional<T1> findByUidOptional(String uniqueId);

    boolean hasUid(String uniqueId);

    List<T1> search();

    Page<T1> search(Pageable pageable);

    List<T1> search(Predicate predicate);

    Page<T1> search(Predicate predicate, Pageable pageable);

    List<T1> search(Predicate predicate, Sort sort);

    List<T1> search(Predicate predicate, OrderSpecifier<?>... orders);

    List<T1> search(Sort sort);

    long count(Predicate predicate);

    T1 createOrUpdate(T1 entity);

    T1 create(T1 entity);

    T1 delete(T1 entity);

    T1 update(T1 entity);

    @Transactional
    List<T1> createAll(Iterable<T1> entities);

    void updateAll(Iterable<T1> entities);

    void deleteAll(Iterable<T1> entities);

    @Transactional
    void createOrUpdateAll(Iterable<T1> entities);
}
