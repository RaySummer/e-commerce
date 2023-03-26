package com.eco.commerce.core.module.member.model;

import com.eco.commerce.core.module.base.model.BaseEntitys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Ray
 * @since 2023/3/26
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "platform_task", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class PlatformTask extends BaseEntitys {

    @Column
    private String taskName;

    @Column
    private Date expireTime;

    @Column
    private Long displayOrder;

}
