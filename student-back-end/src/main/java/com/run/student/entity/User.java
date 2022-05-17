package com.run.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //只有在非空时才发送
public class User{

    /**
     * 用户ID
     */
      @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户组ID
     */
    private Integer groupId;

    /**
     * 权限码
     */
    private Integer permission;


}
