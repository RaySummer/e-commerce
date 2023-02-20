package com.eco.commerce.portal.module.menu.dto.vo;

import com.eco.commerce.core.module.menu.model.PortalMenu;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author Ray
 * @since 2023/2/20
 */
@Getter
@Setter
public class PortalMenuVO implements Serializable {

    private static final long serialVersionUID = -3918678751372589059L;

    private String name;

    private String description;

    private String icon;

    public static PortalMenuVO of(PortalMenu portalMenu) {
        if (portalMenu == null) {
            return null;
        }
        PortalMenuVO vo = new PortalMenuVO();
        BeanUtils.copyProperties(portalMenu, vo);
        return vo;
    }
}
