package com.naiyin.healthy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.common.CommonDeleteDTO;
import com.naiyin.healthy.model.dto.doctorInfo.DoctorInfoDTO;
import com.naiyin.healthy.model.dto.doctorInfo.DoctorInfoExamineDTO;
import com.naiyin.healthy.model.dto.doctorInfo.DoctorInfoQueryDTO;
import com.naiyin.healthy.model.dto.doctorInfo.DoctorInfoUpdateDTO;
import com.naiyin.healthy.model.entity.DoctorInfo;
import com.naiyin.healthy.model.vo.DoctorInfoVO;
import com.naiyin.healthy.service.DoctorInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/doctor/info")
@RequiredArgsConstructor
@Api(tags = "doctorInfo-controller")
public class DoctorInfoController {

    private final DoctorInfoService doctorInfoService;

    @GetMapping("/get/{doctorInfoId}")
    @ApiOperation("获取医生信息")
    public R<DoctorInfo> getDoctorInfo(@PathVariable Long doctorInfoId) {
        DoctorInfo doctorInfo = doctorInfoService.getDoctorInfo(doctorInfoId);
        return R.success(doctorInfo);
    }

    @PostMapping("/user/page")
    @ApiOperation("分页获取医生信息")
    public R<IPage<DoctorInfoVO>> getDoctorInfoPageByUser(@RequestBody DoctorInfoQueryDTO doctorInfoDTO) {
        IPage<DoctorInfoVO> page = doctorInfoService.getDoctorInfoPageByUser(doctorInfoDTO);
        return R.success(page);
    }

    @PostMapping("/manage/page")
    @ApiOperation("分页获取医生信息")
    public R<IPage<DoctorInfoVO>> getDoctorInfoPageByManage(@RequestBody DoctorInfoQueryDTO doctorInfoDTO) {
        IPage<DoctorInfoVO> page = doctorInfoService.getDoctorInfoPageByManage(doctorInfoDTO);
        return R.success(page);
    }

    @PostMapping("/add")
    @ApiOperation("添加医生信息")
    public R<Boolean> addDoctorInfo(@RequestBody DoctorInfoDTO doctorInfoDTO) throws ParseException {
        doctorInfoService.addDoctorInfo(doctorInfoDTO);
        return R.success(true);
    }

    @PostMapping("/update")
    @ApiOperation("更新医生信息")
    public R<Boolean> updateDoctorInfo(@RequestBody DoctorInfoUpdateDTO updateDTO) {
        doctorInfoService.updateDoctorInfo(updateDTO);
        return R.success(true);
    }

    @DeleteMapping()
    @ApiOperation("删除医生信息")
    public R<Boolean> deleteDoctorInfo(@RequestBody CommonDeleteDTO commonDeleteDTO) {
        boolean result = doctorInfoService.removeById(commonDeleteDTO.getId());
        if (!result){
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "删除失败");
        }
        return R.success();
    }

    /**
     * 审核健康知识（管理员）
     */
    @PostMapping("/examine")
    @ApiOperation("审核医生信息")
    public R<Boolean> doctorExamine(@RequestBody DoctorInfoExamineDTO doctorInfoExamineDTO) {
        doctorInfoService.examineDoctorInfo(doctorInfoExamineDTO);
        return R.success(true);
    }
}


