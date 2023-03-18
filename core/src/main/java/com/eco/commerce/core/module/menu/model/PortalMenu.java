package com.eco.commerce.core.module.menu.model;

import com.eco.commerce.core.module.base.model.BaseEntitys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Ray
 * @since 2023/2/20
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "portal_menu", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class PortalMenu extends BaseEntitys {

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String icon;

    @Column
    private Boolean enable;

    @Column
    private String openType;

    @Column
    private Long displayOrder;
}
