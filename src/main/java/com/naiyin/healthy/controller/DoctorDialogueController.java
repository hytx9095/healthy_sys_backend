package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.common.UserInfo;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.ParamException;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueDTO;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueDeleteDTO;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueQueryDTO;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueRegenerateDTO;
import com.naiyin.healthy.model.entity.DoctorDialogue;
import com.naiyin.healthy.model.vo.DoctorContact;
import com.naiyin.healthy.model.vo.DoctorDialogueVO;
import com.naiyin.healthy.model.vo.UserContact;
import com.naiyin.healthy.model.vo.UserVO;
import com.naiyin.healthy.service.DoctorDialogueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor/dialogue")
@RequiredArgsConstructor
@Api(tags = "doctorDialogue-controller")
public class DoctorDialogueController {

    private final DoctorDialogueService doctorDialogueService;

    @PostMapping("/ask")
    @ApiOperation("用户提问")
    public R<Boolean> ask(@RequestBody DoctorDialogueDTO doctorDialogueDTO) {
        doctorDialogueService.userAsk(doctorDialogueDTO);
        return R.success(true);
    }

    @PostMapping("/answer")
    @ApiOperation("医生回答")
    public R<Boolean> answer(@RequestBody DoctorDialogueDTO doctorDialogueDTO) {
        doctorDialogueService.doctorAnswer(doctorDialogueDTO);
        return R.success(true);
    }

    @PostMapping("/get/page")
    @ApiOperation("获取对话")
    public R<Page<DoctorDialogueVO>> getDoctorDialoguePage(@RequestBody DoctorDialogueQueryDTO doctorDialogueQueryDTO) {
        Page<DoctorDialogueVO> page = doctorDialogueService.getDoctorDialoguePage(doctorDialogueQueryDTO);
        return R.success(page);
    }

    @PostMapping("/doctor/get/user")
    @ApiOperation("获取对话用户列表")
    public R<List<UserContact>> getDoctorDialogueUser() {
        List<UserContact> userInfoList = doctorDialogueService.getDoctorDialogueUser();
        return R.success(userInfoList);
    }

    @PostMapping("/doctor/get/doctor")
    @ApiOperation("获取对话医生列表")
    public R<List<DoctorContact>> getDoctorDialogueDoctor() {
        List<DoctorContact> doctorContacts = doctorDialogueService.getDoctorDialogueDoctor();
        return R.success(doctorContacts);
    }

    @PostMapping("/regenerate")
    @ApiOperation("重新生成")
    public R<Boolean> regenerateDialogue(@RequestBody DoctorDialogueRegenerateDTO regenerateDTO) {
        if (regenerateDTO.getAnswerId() == null){
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        if (regenerateDTO.getQuestionId() == null){
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        doctorDialogueService.regenerateDialogue(regenerateDTO);
        return R.success(true);
    }

    @DeleteMapping
    @ApiOperation("删除助手对话")
    public R<Boolean> deleteDoctorDialogue(@RequestBody DoctorDialogueDeleteDTO deleteDTO) {
        if (deleteDTO.getDialogueId() == null){
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "参数错误");
        }
        boolean result = doctorDialogueService.removeById(deleteDTO.getDialogueId());
        return R.success(result);
    }

}


