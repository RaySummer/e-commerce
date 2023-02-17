package com.eco.commerce.core.module.member.model;

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
 * @since 2023/2/14
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
@Entity
@Table(name = "member", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uid")
})
public class Member extends BaseEntity {

    @Column
    private String nickName;

    @Column
    private boolean gender;

    @Column
    private String mobileNumber;

    @Column
    private String email;

    @Column
    private String account;

    @Column
    private String avatar;

    @Column
    private String password;

    @Column
    private String backImage;

    @Column
    private String birthday;

    @Column
    private String address;

}
