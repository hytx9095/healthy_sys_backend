package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.UserInfo;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueDTO;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueQueryDTO;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueRegenerateDTO;
import com.naiyin.healthy.model.entity.DoctorDialogue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naiyin.healthy.model.vo.DoctorContact;
import com.naiyin.healthy.model.vo.DoctorDialogueVO;
import com.naiyin.healthy.model.vo.UserContact;
import com.naiyin.healthy.model.vo.UserVO;

import java.util.List;

/**
* @author wang'ren
* @description 针对表【doctor_dialogue】的数据库操作Service
* @createDate 2025-04-23 12:27:34
*/
public interface DoctorDialogueService extends IService<DoctorDialogue> {

    Page<DoctorDialogueVO> getDoctorDialoguePage(DoctorDialogueQueryDTO doctorDialogueQueryDTO);

    void regenerateDialogue(DoctorDialogueRegenerateDTO regenerateDTO);


    void userAsk(DoctorDialogueDTO doctorDialogueDTO);

    void doctorAnswer(DoctorDialogueDTO doctorDialogueDTO);

    QueryWrapper<DoctorDialogue> getQueryWrapper(DoctorDialogueQueryDTO doctorDialogueQueryDTO);

    List<UserContact> getDoctorDialogueUser();

    List<DoctorContact> getDoctorDialogueDoctor();
}
