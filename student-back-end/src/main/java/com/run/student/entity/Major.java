package com.run.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
public class Major {

    /**
     * 专业id
     */
      @TableId(value = "mid", type = IdType.AUTO)
    private Integer mid;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 学院ID
     */
    private Integer departmentId;


}
