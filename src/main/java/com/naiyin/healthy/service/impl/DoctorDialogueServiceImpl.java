package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.enums.DoctorDialogueEnum;
import com.naiyin.healthy.enums.DoctorDialogueStatusEnum;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueDTO;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueQueryDTO;
import com.naiyin.healthy.model.dto.doctorDialogue.DoctorDialogueRegenerateDTO;
import com.naiyin.healthy.model.entity.DoctorDialogue;
import com.naiyin.healthy.model.entity.User;
import com.naiyin.healthy.model.vo.DoctorContact;
import com.naiyin.healthy.model.vo.DoctorDialogueVO;
import com.naiyin.healthy.model.vo.UserContact;
import com.naiyin.healthy.service.DoctorDialogueService;
import com.naiyin.healthy.mapper.DoctorDialogueMapper;
import com.naiyin.healthy.service.UserService;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wang'ren
 * @description 针对表【doctor_dialogue】的数据库操作Service实现
 * @createDate 2025-04-23 12:27:34
 */
@Service
public class DoctorDialogueServiceImpl extends ServiceImpl<DoctorDialogueMapper, DoctorDialogue>
        implements DoctorDialogueService {

    @Resource
    private UserService userService;

    @Resource
    private DoctorDialogueMapper doctorDialogueMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<DoctorDialogueVO> getDoctorDialoguePage(DoctorDialogueQueryDTO doctorDialogueQueryDTO) {
        long current = doctorDialogueQueryDTO.getCurrent();
        long size = doctorDialogueQueryDTO.getPageSize();
        if (doctorDialogueQueryDTO.getCurrentRole().equals(DoctorDialogueEnum.USER.getValue())){
            doctorDialogueQueryDTO.setUserId(UserContext.getUserId());
        }
        if (doctorDialogueQueryDTO.getCurrentRole().equals(DoctorDialogueEnum.Doctor.getValue())){
            doctorDialogueQueryDTO.setDoctorId(UserContext.getUserId());
        }
        Page<DoctorDialogue> page = page(
                new Page<>(current, size),
                getQueryWrapper(doctorDialogueQueryDTO));
        Page<DoctorDialogueVO> doctorDialogueVOPage = new Page<>(current, size, page.getTotal());
        List<DoctorDialogue> records = page.getRecords();
        List<DoctorDialogueVO> doctorDialogueVOList;
        if (CollectionUtils.isEmpty(records)) {
            doctorDialogueVOList = new ArrayList<>();
        } else {
            doctorDialogueVOList = records.stream().map(this::getDoctorDialogueVO).collect(Collectors.toList());
        }
        doctorDialogueVOPage.setRecords(doctorDialogueVOList);
        return doctorDialogueVOPage;
    }

    @Override
    public void regenerateDialogue(DoctorDialogueRegenerateDTO regenerateDTO) {
        boolean delete = removeById(regenerateDTO.getAnswerId());
        if (!delete) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "删除失败");
        }
    }

    @Override
    public void userAsk(DoctorDialogueDTO doctorDialogueDTO) {
        DoctorDialogue userDialogue = new DoctorDialogue();
        userDialogue.setUserId(UserContext.getUserId());
        userDialogue.setDoctorId(doctorDialogueDTO.getDoctorId());
        userDialogue.setSpokesman(DoctorDialogueEnum.USER.getValue());
        userDialogue.setContent(doctorDialogueDTO.getContent());
        userDialogue.setStatus(DoctorDialogueStatusEnum.DOCTOR_UNREAD.getValue());
        boolean userSave = save(userDialogue);
        if (!userSave) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "对话添加失败");
        }
    }

    @Override
    public void doctorAnswer(DoctorDialogueDTO doctorDialogueDTO) {
        DoctorDialogue doctorDialogue = new DoctorDialogue();
        doctorDialogue.setUserId(doctorDialogueDTO.getUserId());
        doctorDialogue.setDoctorId(UserContext.getUserId());
        doctorDialogue.setSpokesman(DoctorDialogueEnum.Doctor.getValue());
        doctorDialogue.setContent(doctorDialogueDTO.getContent());
        doctorDialogue.setStatus(DoctorDialogueStatusEnum.USER_UNREAD.getValue());
        boolean userSave = save(doctorDialogue);
        if (!userSave) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "对话添加失败");
        }
    }

    @Override
    public QueryWrapper<DoctorDialogue> getQueryWrapper(DoctorDialogueQueryDTO doctorDialogueQueryDTO) {
        if (doctorDialogueQueryDTO == null) {
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        String sortField = doctorDialogueQueryDTO.getSortField();
        String sortOrder = doctorDialogueQueryDTO.getSortOrder();
        QueryWrapper<DoctorDialogue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(doctorDialogueQueryDTO.getUserId() != null, "user_id", doctorDialogueQueryDTO.getUserId());
        queryWrapper.eq(doctorDialogueQueryDTO.getDoctorId() != null, "doctor_id", doctorDialogueQueryDTO.getDoctorId());
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    @Override
    public List<UserContact> getDoctorDialogueUser() {
        List<UserContact> userInfoList = doctorDialogueMapper.selectUserChatList(UserContext.getUserId());
        return userInfoList;
    }

    @Override
    public List<DoctorContact> getDoctorDialogueDoctor() {
        List<DoctorContact> doctorContactList = doctorDialogueMapper.selectDoctorChatList(UserContext.getUserId());
        return doctorContactList;
    }

    public DoctorDialogueVO getDoctorDialogueVO(DoctorDialogue doctorDialogue) {
        if (doctorDialogue == null) {
            return null;
        }
        DoctorDialogueVO doctorDialogueVO = BeanUtil.copyProperties(doctorDialogue, DoctorDialogueVO.class);
        User user = userService.lambdaQuery().eq(User::getId, doctorDialogue.getUserId()).one();
        doctorDialogueVO.setDoctorName(user.getUsername());
        doctorDialogueVO.setDoctorAvatar(user.getUserAvatar());
        //更新状态
        DoctorDialogue updateDoctorDialogue = new DoctorDialogue();
        updateDoctorDialogue.setId(doctorDialogue.getId());
        updateDoctorDialogue.setStatus(DoctorDialogueStatusEnum.NORMAL.getValue());
        boolean update = updateById(updateDoctorDialogue);
        if (!update) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "更新失败");
        }
        return doctorDialogueVO;
    }
}




