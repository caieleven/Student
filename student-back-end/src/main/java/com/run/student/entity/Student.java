package com.run.student.entity;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Student{
    /**
     * 学号
     */
    @TableId(value = "sid")
      private Long sid;

    /**
     * 姓名
     */
    private String name;

    private Integer classId;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号码
     */
    private String tel;

    private String email;

    /**
     * 生源地
     */
    private String origin;

    /**
     * 政治面貌
     */
    private String status;

    /**
     * 民族
     */
    private String nation;

    /**
     * 宿舍楼号
     */
    private String dormitory;

    /**
     * 室号
     */
    private Integer bedroom;

    /**
     * 床号
     */
    private Integer bed;

    /**
     * 入学日期
     */
    private LocalDate admissionDate;

    /**
     * 出生日期
     */
    private LocalDate birth;

    /**
     * 来源
     */
    private String background;


}
