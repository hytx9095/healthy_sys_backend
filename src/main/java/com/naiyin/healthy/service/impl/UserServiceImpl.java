package com.naiyin.healthy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.constant.CommonConstant;
import com.naiyin.healthy.constant.EmailConstant;
import com.naiyin.healthy.constant.RegexConstant;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.enums.UserRoleEnum;
import com.naiyin.healthy.exception.AddException;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.exception.ParamException;
import com.naiyin.healthy.mapper.UserMapper;
import com.naiyin.healthy.model.dto.user.*;
import com.naiyin.healthy.model.entity.User;
import com.naiyin.healthy.model.vo.LoginVO;
import com.naiyin.healthy.model.vo.UserVO;
import com.naiyin.healthy.service.UserService;
import com.naiyin.healthy.util.EmailUtils;
import com.naiyin.healthy.util.JwtUtils;
import com.naiyin.healthy.util.SqlUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public LoginVO login(UserLoginFormDTO userLoginFormDTO) {

        User user = lambdaQuery().eq(User::getUserAccount, userLoginFormDTO.getUserAccount()).one();

        if (user == null) {
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户不存在");
        }

        String encryptPassword = DigestUtil.md5Hex(userLoginFormDTO.getUserPassword());
        if (!user.getUserPassword().equals(encryptPassword)) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "密码错误");
        }
        LoginVO loginVO = BeanUtil.copyProperties(user, LoginVO.class);
        loginVO.setToken(JwtUtils.createToken(user.getId()));
        return loginVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterDTO userRegisterDTO) {

        String userAccount = userRegisterDTO.getUserAccount();
        String userPassword = userRegisterDTO.getUserPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();
        if (!StrUtil.isAllNotBlank(userAccount, userPassword, checkPassword)) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR);
        }
        if (!userPassword.equals(checkPassword)) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "两次密码不一致");
        }
        if (!ReUtil.isMatch(RegexConstant.USER_ACCOUNT, userAccount)) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "账号格式不符合要求");
        }
        if (!ReUtil.isMatch(RegexConstant.PASSWORD, userPassword)) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "密码格式不符合要求");
        }
        User user = lambdaQuery().eq(User::getUserAccount, userAccount).one();

        if (user != null) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "账号已存在");
        }

        //加密用户密码
        String encryptPassword = DigestUtil.md5Hex(userPassword);

        User register = new User();
        register.setUserAccount(userAccount);
        register.setUserPassword(encryptPassword);
        boolean save = save(register);
        if (!save) {
            throw new AddException(SysErrorEnum.OPERATION_ERROR, "用户添加失败");
        }
        register.setUsername("用户" + register.getId());
        boolean update = updateById(register);
        if (!update) {
            throw new AddException(SysErrorEnum.OPERATION_ERROR, "用户添加失败");
        }
    }

    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {

        String telephone = userUpdateDTO.getTelephone();
        String email = userUpdateDTO.getEmail();

        if (!ReUtil.isMatch(RegexConstant.TELEPHONE, telephone) && StrUtil.isNotBlank(telephone)) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "手机号码格式不符合要求");
        }
        if (!ReUtil.isMatch(RegexConstant.EMAIL, email) && StrUtil.isNotBlank(email)) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "邮箱格式不符合要求");
        }
        User user = BeanUtil.copyProperties(userUpdateDTO, User.class);
        user.setId(UserContext.getUserId());
        boolean result = updateById(user);
        if (!result) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "用户信息更新失败");
        }
    }

    @Override
    public void changeUserPassword(UserChangePasswordDTO userChangePasswordDTO) {
        if (StrUtil.isBlank(userChangePasswordDTO.getOldPassword())) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "旧密码为空");
        }
        if (StrUtil.isBlank(userChangePasswordDTO.getNewPassword())) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "新密码为空");
        }
        if (StrUtil.isBlank(userChangePasswordDTO.getConfirmPassword())) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "确认密码为空");
        }
        String encryptOldPassword = DigestUtil.md5Hex(userChangePasswordDTO.getOldPassword());
        User selectUser = lambdaQuery().eq(User::getId, UserContext.getUserId()).one();

        if (!selectUser.getUserPassword().equals(encryptOldPassword)){
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "旧密码错误");
        }

        if (!userChangePasswordDTO.getNewPassword().equals(userChangePasswordDTO.getConfirmPassword())){
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "两次密码不一致");
        }
        if (!ReUtil.isMatch(RegexConstant.PASSWORD, userChangePasswordDTO.getNewPassword())) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "密码格式不符合要求");
        }
        String encryptNewPassword = DigestUtil.md5Hex(userChangePasswordDTO.getNewPassword());
        User user = new User();
        user.setId(UserContext.getUserId());
        user.setUserPassword(encryptNewPassword);
        boolean result = updateById(user);
        if (!result) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "用户密码修改失败");
        }
    }

    @Override
    public Page<User> getUserPage(UserQueryDTO userQueryDTO) {
        Page<User> userPage = page(new Page<>(userQueryDTO.getCurrent(), userQueryDTO.getPageSize())
                , getQueryWrapper(userQueryDTO));
        userPage.getRecords().forEach(user -> {
            user.setUserPassword(null);
        });
        return userPage;
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryDTO userQueryDTO) {
        if (userQueryDTO == null) {
            throw new CommonException(SysErrorEnum.PARAM_ERROR, "请求参数为空");
        }
        String userName = userQueryDTO.getUsername();
        String userRole = userQueryDTO.getRole();
        String sortField = userQueryDTO.getSortField();
        String sortOrder = userQueryDTO.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(userRole) && userRole.equals("notBan")){
            queryWrapper.ne("role", UserRoleEnum.BAN.getValue());
        } else {
            queryWrapper.eq(StringUtils.isNotBlank(userRole), "role", userRole);
        }
        queryWrapper.like(StringUtils.isNotBlank(userName), "username", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public void judgeDoctor(DoctorApplyDTO doctorApplyDTO) {
        //todo
    }

    @Override
    public LoginVO getLoginVO() {
        User user = lambdaQuery().eq(User::getId, UserContext.getUserId()).one();

        if (user == null) {
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户不存在");
        }
        LoginVO loginVO = BeanUtil.copyProperties(user, LoginVO.class);
        return loginVO;
    }

    @Override
    public void updateUserRole(UserUpdateRoleDTO userUpdateRoleDTO) {
        User user = new User();
        user.setId(userUpdateRoleDTO.getUserId());
        user.setRole(userUpdateRoleDTO.getRole());
        boolean b = updateById(user);
        if (!b) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "用户角色更新失败");
        }
    }

    @Override
    public List<UserVO> getUserList(UserQueryDTO userQueryDTO) {
        List<User> list = lambdaQuery().like(User::getUsername, userQueryDTO.getUsername()).list();
        List<UserVO> userVOS = BeanUtil.copyToList(list, UserVO.class);
        return userVOS;
    }

    @Override
    public UserVO getUser() {
        User user = lambdaQuery().eq(User::getId, UserContext.getUserId()).one();
        if (user == null) {
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户不存在");
        }
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    public UserVO getUserById(GetUserVODTO getUserVODTO) {
        User user = lambdaQuery().eq(User::getId, getUserVODTO.getUserId()).one();
        if (user == null) {
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户不存在");
        }
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    public void findPassword(UserFindPasswordDTO userFindPasswordDTO) {
        String email = userFindPasswordDTO.getEmail();
        if (!ReUtil.isMatch(RegexConstant.EMAIL, email)) {
            throw new ParamException(SysErrorEnum.PARAM_ERROR, "邮箱格式不符合要求");
        }
        User user = lambdaQuery().eq(User::getEmail, email).one();
        if (user == null) {
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "用户不存在");
        }
        String encryptPassword = DigestUtil.md5Hex(user.getUserPassword());
        try {
            EmailUtils.sendMail(email, EmailConstant.SUBJECT, EmailConstant.FIND_PASSWORD_TEXT + encryptPassword);
        } catch (MessagingException e) {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "邮件发送失败");
        }
    }
}
