package com.eco.commerce.core.module.base.service.impl;

import com.eco.commerce.core.constants.Constants;
import com.eco.commerce.core.module.base.model.BaseEntity;
import com.google.common.collect.Lists;
import com.eco.commerce.core.module.base.repository.BaseRepository;
import com.eco.commerce.core.module.base.service.BaseCrudService;
import com.eco.commerce.core.utils.WebThreadLocal;
import com.querydsl.codegen.Keywords;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ray
 * @since 2023/02/14
 **/
@Transactional
public abstract class BaseCrudServiceImpl<X extends BaseRepository<T1, ID>, T1 extends BaseEntity, ID extends Serializable> implements BaseCrudService<T1, ID> {

    @Autowired
    protected X baseRepository;

    private final Class<T1> entityClazz;


    /**
     * QueryDsl path builder
     */
    private final PathBuilder<T1> entityPath;

    /**
     *
     */
    @SuppressWarnings("unchecked")
    protected BaseCrudServiceImpl() {
        entityClazz = (Class<T1>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        entityPath = new PathBuilder<>(entityClazz, getEntityPathAlias(entityClazz));
    }

    /**
     * get querydsl entity path alias
     *
     * @param entityClass 实体类
     * @return 查询实体路径别名
     */
    private String getEntityPathAlias(Class<T1> entityClass) {
        Collection<String> keywords = new HashSet<>();
        Collections.addAll(keywords, Keywords.JPA.toArray(new String[0]));
        Collections.addAll(keywords, Keywords.JDO.toArray(new String[0]));

        String simpleName = entityClass.getSimpleName();
        String alias = StringUtils.uncapitalize(simpleName);

        if (keywords.contains(simpleName.toUpperCase())) {
            alias = alias + "1";
        }
        return alias;

    }

    @Override
    @Transactional(readOnly = true)
    public T1 findById(ID id) {
        return baseRepository.findOneByIdAndDeletedTimeNull(id);
    }

    @Override
    @Transactional(readOnly = true)
    public T1 findByReference(String reference) {
        return baseRepository.findOneByReferenceAndDeletedTimeNull(reference);
    }

    @Override
    @Transactional(readOnly = true)
    public T1 findByUid(UUID uid) {
        return baseRepository.findOneByUidAndDeletedTimeNull(uid);
    }

    @Override
    @Transactional(readOnly = true)
    public T1 findByUid(String uid) {
        T1 t = baseRepository.findOneByUidAndDeletedTimeNull(UUID.fromString(uid));
        if (t == null) {
//            throw new NotFoundException(entityClazz);
        }
        return t;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T1> findByUidOptional(String uid) {
        T1 t = baseRepository.findOneByUidAndDeletedTimeNull(UUID.fromString(uid));
        return Optional.ofNullable(t);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasUid(String uid) {
        if (StringUtils.isBlank(uid)) {
            return false;
        }

        return baseRepository.countOneByUidAndDeletedTimeNull(UUID.fromString(uid)) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T1> search() {
        return baseRepository.findAllByDeletedTimeNull();
    }

    /**
     * 分页，查询所有
     *
     * @param pageable 分页参数
     * @return 分页数据
     */
    @Override
    @Transactional(readOnly = true)
    public Page<T1> search(Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        addDeleteCondition(builder);
        return search(builder, pageable);
    }

    /**
     * 不带分页查询
     *
     * @param predicate 查询参数
     * @return 查询结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<T1> search(Predicate predicate) {
        BooleanBuilder builder = new BooleanBuilder();
        addDeleteCondition(builder);
        builder.and(predicate);
        return Lists.newArrayList(baseRepository.findAll(builder));
    }

    /**
     * 分页查询
     *
     * @param predicate 查询参数
     * @param pageable  分页参数
     * @return 分页数据
     */
    @Override
    @Transactional(readOnly = true)
    public Page<T1> search(Predicate predicate, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        addDeleteCondition(builder);
        builder.and(predicate);
        return baseRepository.findAll(builder, pageable);
    }

    /**
     * 纯排序查询
     *
     * @param predicate 查询参数
     * @param sort      排序参数
     * @return 查询结果列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<T1> search(Predicate predicate, Sort sort) {
        BooleanBuilder builder = new BooleanBuilder();
        addDeleteCondition(builder);
        builder.and(predicate);
        return Lists.newArrayList(baseRepository.findAll(builder, sort));
    }

    @Override
    public List<T1> search(Predicate predicate, OrderSpecifier<?>... orders) {
        BooleanBuilder builder = new BooleanBuilder();
        addDeleteCondition(builder);
        builder.and(predicate);
        return Lists.newArrayList(baseRepository.findAll(builder, orders));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T1> search(Sort sort) {
        BooleanBuilder builder = new BooleanBuilder();
        addDeleteCondition(builder);
        return Lists.newArrayList(baseRepository.findAll(builder, sort));
    }

    @Override
    @Transactional(readOnly = true)
    public long count(Predicate predicate) {
        BooleanBuilder builder = new BooleanBuilder();
        addDeleteCondition(builder);
        builder.and(predicate);
        return baseRepository.count(builder);
    }

    @Override
    @Transactional
    public T1 createOrUpdate(T1 entity) {
        if (entity.getId() == null) {
            return create(entity);
        } else {
            return update(entity);
        }
    }

    @Override
    @Transactional
    public T1 create(T1 entity) {
        setCreateMsg(entity);
        return baseRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public T1 delete(T1 entity) {
        setDeleteMsg(entity);
        return baseRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public T1 update(T1 entity) {
        setUpdateMsg(entity);
        return baseRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public List<T1> createAll(Iterable<T1> entities) {
        for (T1 entity : entities) {
            setCreateMsg(entity);
        }
        List<T1> all = baseRepository.saveAll(entities);
        baseRepository.flush();
        return all;
    }

    @Override
    @Transactional
    public void updateAll(Iterable<T1> entities) {
        for (T1 entity : entities) {
            setUpdateMsg(entity);
        }
        baseRepository.saveAll(entities);
        baseRepository.flush();
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<T1> entities) {
        for (T1 entity : entities) {
            setDeleteMsg(entity);
        }
        baseRepository.saveAll(entities);
        baseRepository.flush();
    }


    @Override
    @Transactional
    public void createOrUpdateAll(Iterable<T1> entities) {
        for (T1 entity : entities) {
            if (entity.getId() == null) {
                setCreateMsg(entity);
            } else {
                setUpdateMsg(entity);
            }
        }
        baseRepository.saveAll(entities);
        baseRepository.flush();
    }

    void addDeleteCondition(BooleanBuilder builder) {
        builder.and(entityPath.get(Constants.FIELD_DELETED_TIME).isNull());
    }


    protected void setCreateMsg(T1 e) {
        if (e.getCreatedTime() == null) {
            e.setCreatedTime(WebThreadLocal.getOperatorTime());
        }
        if (StringUtils.isBlank(e.getCreatedBy())) {
            e.setCreatedBy(WebThreadLocal.getOperatorName());
        }
    }

    protected void setUpdateMsg(T1 e) {
        e.setUpdatedTime(WebThreadLocal.getOperatorTime());
        e.setUpdatedBy(WebThreadLocal.getOperatorName());
    }

    public void setDeleteMsg(T1 e) {
        e.setDeletedTime(WebThreadLocal.getOperatorTime());
        e.setDeletedBy(WebThreadLocal.getOperatorName());
    }

}
