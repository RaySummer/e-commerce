package com.eco.commerce.core.module.menu.service;

import com.eco.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.eco.commerce.core.module.menu.model.PortalMenu;
import com.eco.commerce.core.module.menu.repository.PortalMenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @since 2023/2/20
 */
@Slf4j
@Service
public class PortalMenuCoreService extends BaseCrudServiceImpl<PortalMenuRepository, PortalMenu, Long> {

    public PortalMenu findMenu() {
        return baseRepository.findPortalMenu();
    }
}
