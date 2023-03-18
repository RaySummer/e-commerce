package com.eco.commerce.core.module.base.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Access(AccessType.FIELD)
@Slf4j
@MappedSuperclass
public class BaseEntitys {

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
        if (!(o instanceof BaseEntitys)) {
            return false;
        }
        BaseEntitys that = (BaseEntitys) o;
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
