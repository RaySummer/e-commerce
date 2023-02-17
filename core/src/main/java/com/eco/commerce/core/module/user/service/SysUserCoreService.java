package com.eco.commerce.core.module.user.service;

import com.eco.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.eco.commerce.core.module.user.model.SysUser;
import com.eco.commerce.core.module.user.repository.SysUserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @since 2023/2/15
 */
@Service
public class SysUserCoreService extends BaseCrudServiceImpl<SysUserRepository, SysUser, Long> {
}
