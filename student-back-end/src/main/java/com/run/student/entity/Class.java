package com.run.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //只有在非空时才发送
public class Class {
    /**
     * 班级ID
     */
      @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 专业ID
     */
    private Integer majorId;

    /**
     * 辅导员ID
     */
    private Integer counsellorId;


}
