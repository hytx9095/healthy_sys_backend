package com.naiyin.healthy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naiyin.healthy.common.R;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.FilePathConstant;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.common.CommonDeleteDTO;
import com.naiyin.healthy.model.dto.user.*;
import com.naiyin.healthy.model.entity.User;
import com.naiyin.healthy.model.vo.LoginVO;
import com.naiyin.healthy.model.vo.UserVO;
import com.naiyin.healthy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "user-controller")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation("密码登录")
    public R<LoginVO> login(@RequestBody UserLoginFormDTO userLoginFormDTO) {
        LoginVO login = userService.login(userLoginFormDTO);
        return R.success(login);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
//    @GetMapping("/get")
//    public R<LoginVO> getLoginUser() {
//        LoginVO loginVO = userService.getLoginVO();
//        return R.success(loginVO);
//    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public R<Boolean> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return R.success(true);
    }

    @PostMapping("/findPassword")
    @ApiOperation("找回密码")
    public R<Boolean> findPassword(@RequestBody UserFindPasswordDTO userFindPasswordDTO) {
        userService.findPassword(userFindPasswordDTO);
        return R.success(true);
    }

    @PostMapping("/logout")
    @ApiOperation("登出")
    public R logout() {
        return R.success();
    }

    @GetMapping("/get")
    @ApiOperation("获取用户信息")
    public R<UserVO> getUser() {
        UserVO uservo = userService.getUser();
        return R.success(uservo);
    }

    @PostMapping("/get/list")
    @ApiOperation("获取用户信息")
    public R<List<UserVO>> getUserList(@RequestBody UserQueryDTO userQueryDTO) {
        List<UserVO> userVoList = userService.getUserList(userQueryDTO);
        return R.success(userVoList);
    }

    @PostMapping("/get/vo")
    @ApiOperation("获取用户信息")
    public R<UserVO> getUserById(@RequestBody GetUserVODTO getUserVODTO) {
        UserVO uservo = userService.getUserById(getUserVODTO);
        return R.success(uservo);
    }

    @PostMapping("/page/list")
    @ApiOperation("管理员分页获取用户信息")
    public R<Page<User>> getUserPage(@RequestBody UserQueryDTO userQueryDTO) {
        Page<User> userPage = userService.getUserPage(userQueryDTO);
        return R.success(userPage);
    }

    @PostMapping("/judge/doctor")
    @ApiOperation("管理员审核医生")
    public R judgeDoctor(@RequestBody DoctorApplyDTO doctorApplyDTO) {
        userService.judgeDoctor(doctorApplyDTO);
        return R.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新用户")
    public R<Boolean> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUser(userUpdateDTO);
        return R.success(true);
    }

    @PostMapping("/update/role")
    @ApiOperation("更新用户角色")
    public R<Boolean> updateUserRole(@RequestBody UserUpdateRoleDTO userUpdateRoleDTO) {
        userService.updateUserRole(userUpdateRoleDTO);
        return R.success(true);
    }

    @PostMapping("/change/password")
    @ApiOperation("更新用户密码")
    public R<Boolean> changeUserPassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO) {
        userService.changeUserPassword(userChangePasswordDTO);
        return R.success(true);
    }

    @PostMapping("/change/avatar")
    @ApiOperation("更新用户头像")
    public R<Boolean> changeUserAvatar(@RequestParam("image") MultipartFile image) throws IOException {

        if (image == null || image.isEmpty()) {
            return R.error("请选择要上传的头像");
        }
        // 获取用户上场的文件名
        String filename = image.getOriginalFilename();

        // 优化：为了保证图片名称的唯一性 不会被多用户覆盖
        filename = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
        System.out.println(filename);

        String filePath = FilePathConstant.USER_AVATAR_FILE_PATH + filename;
        // 输出到指定磁盘下
        image.transferTo(new File(filePath));

        User user = new User();
        user.setId(UserContext.getUserId());
        user.setUserAvatar(FilePathConstant.USER_AVATAR + filename);
        boolean update = userService.updateById(user);
        if (!update) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "用户头像更新失败");
        }
        return R.success(true);
    }

    @DeleteMapping
    @ApiOperation("删除用户")
    public R<Boolean> deleteUser(@RequestBody CommonDeleteDTO deleteDTO) {
        boolean result = userService.removeById(deleteDTO.getId());
        return R.success(result);
    }

}


