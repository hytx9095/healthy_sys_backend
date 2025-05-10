package com.naiyin.healthy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naiyin.healthy.model.dto.user.*;
import com.naiyin.healthy.model.entity.User;
import com.naiyin.healthy.model.vo.LoginVO;
import com.naiyin.healthy.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends IService<User> {

    LoginVO login(UserLoginFormDTO userLoginFormDTO);

    void register(UserRegisterDTO userRegisterDTO);

    void updateUser(UserUpdateDTO userUpdateDTO);

    UserVO getUser();
    UserVO getUserById(GetUserVODTO getUserVODTO);

    void findPassword(UserFindPasswordDTO userFindPasswordDTO);

    void changeUserPassword(UserChangePasswordDTO userChangePasswordDTO);

    Page<User> getUserPage(UserQueryDTO userQueryDTO);

    QueryWrapper<User> getQueryWrapper(UserQueryDTO userQueryDTO);

    void judgeDoctor(DoctorApplyDTO doctorApplyDTO);

    LoginVO getLoginVO();


    void updateUserRole(UserUpdateRoleDTO userUpdateRoleDTO);

    List<UserVO> getUserList(UserQueryDTO userQueryDTO);
}
