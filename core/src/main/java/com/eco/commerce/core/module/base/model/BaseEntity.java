package com.eco.commerce.core.module.base.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author Ray
 * @since 2023/2/14
 */
@Getter
@Setter
@Access(AccessType.FIELD)
@Slf4j
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    @Column(length = 36, nullable = false, unique = true, columnDefinition = "UUID DEFAULT uuid_generate_v4()")
    protected UUID uid = UUID.randomUUID();

    @Column(name = "created_by")
    protected String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    protected Date createdTime;

    @Column(name = "updated_by")
    protected String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    protected Date updatedTime;

    @Column(name = "deleted_by")
    protected String deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_time")
    protected Date deletedTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseEntity)) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return Objects.equal(getId(), that.getId())
                && Objects.equal(getUid(), that.getUid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getUid());
    }

    @Transient
    public String getUidStr() {
        return getUid().toString();
    }

    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    @Transient
    public boolean isDeleted() {
        return deletedTime != null;
    }

}
