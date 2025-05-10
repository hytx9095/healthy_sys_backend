package com.naiyin.healthy.model.dto.user;

import com.naiyin.healthy.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryDTO extends PageRequest implements Serializable {

    /**
	 * 用户昵称
	 */
    private String username;

    /**
     * 用户角色：user/admin/ban
     */
    private String role;
    private static final long serialVersionUID = 1L;
}
