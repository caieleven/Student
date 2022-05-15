package com.run.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
public class UserGroup{

    /**
     * 组ID
     */
      @TableId(value = "gid", type = IdType.AUTO)
    private Integer gid;

    /**
     * 用户组名
     */
    private String groupName;


}
