package com.eco.commerce.core.module.menu.repository;

import com.eco.commerce.core.module.base.repository.BaseRepository;
import com.eco.commerce.core.module.member.model.Member;
import com.eco.commerce.core.module.menu.model.PortalMenu;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Ray
 * @since 2023/2/20
 */
public interface PortalMenuRepository extends BaseRepository<PortalMenu, Long> {

    @Query(value = "from PortalMenu where deletedBy is null and enable = true")
    PortalMenu findPortalMenu();
}
