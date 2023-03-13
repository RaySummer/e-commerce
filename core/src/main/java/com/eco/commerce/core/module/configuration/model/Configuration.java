package com.eco.commerce.core.module.configuration.model;

import com.eco.commerce.core.module.base.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Ray
 * @since 2023/2/21
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "configuration", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class Configuration extends BaseEntity {

    @Column
    private String key;

    @Column
    private String value;

    @Column
    private String description;

    @Column
    private String platform;
}
