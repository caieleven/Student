package com.run.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
public class Department {

    /**
     * 学院id
     */
      @TableId(value = "did", type = IdType.AUTO)
    private Integer did;

    /**
     * 学院名字
     */
    private String name;


}
