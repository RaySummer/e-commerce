package com.eco.commerce.core.module.user.model;

import com.eco.commerce.core.module.base.model.BaseEntitys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Ray
 * @since 2023/2/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "sys_user", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class SysUser extends BaseEntitys {

    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String mobileNumber;
    @Column
    private String account;
    @Column
    private String avatar;
    @Column
    private String password;
    @Column
    private String roleId;

}
