package com.eco.commerce.portal.module.member.service;

import com.eco.commerce.core.module.member.dto.MemberDto;
import com.eco.commerce.core.module.member.model.Member;
import com.eco.commerce.core.module.member.model.MemberTaskRecode;
import com.eco.commerce.core.module.member.model.PlatformTask;
import com.eco.commerce.core.module.member.service.MemberTaskRecodeCoreService;
import com.eco.commerce.core.module.member.service.PlatformTaskCoreService;
import com.eco.commerce.core.utils.DateUtil;
import com.eco.commerce.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @since 2023/2/16
 */

@Slf4j
@Service()
public class MemberTaskRecodeService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlatformTaskCoreService platformTaskCoreService;

    @Autowired
    private MemberTaskRecodeCoreService memberTaskRecodeCoreService;

    public List<MemberTaskRecode> getMemberTaskRecodes(String memberUid) {

        Member member = memberService.findByUid(memberUid);

        Assert.notNull(member, "Cannot found member");

        return memberTaskRecodeCoreService.findMemberTaskRecode(member.getId(), DateUtil.getDayBegin(), DateUtil.getDayEnd());
    }

    @Transactional
    public void completeTask(String memberUid, String taskUid) {

        Assert.notNull(memberUid, "Member uid is null");

        Member member = memberService.findByUid(memberUid);

        Assert.notNull(member, "Cannot found member");

        PlatformTask task = platformTaskCoreService.findByUid(taskUid);

        Assert.notNull(task, "Cannot found task");

        MemberTaskRecode memberTaskRecode = new MemberTaskRecode();
        memberTaskRecode.setMember(member);
        memberTaskRecode.setPlatformTask(task);
        memberTaskRecode.setCompleteTime(new Date());

        memberTaskRecodeCoreService.create(memberTaskRecode);
    }

}
