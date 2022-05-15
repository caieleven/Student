package com.run.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
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
