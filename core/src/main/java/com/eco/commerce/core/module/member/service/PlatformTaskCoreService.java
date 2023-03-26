package com.eco.commerce.core.module.member.service;

import com.eco.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.eco.commerce.core.module.member.model.PlatformTask;
import com.eco.commerce.core.module.member.repository.PlatformTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @since 2023/3/26
 */
@Slf4j
@Service
public class PlatformTaskCoreService extends BaseCrudServiceImpl<PlatformTaskRepository, PlatformTask, Long> {

    public List<PlatformTask> findTask() {
        return baseRepository.findAllByExpireTimeBefore(new Date());
    }
}
