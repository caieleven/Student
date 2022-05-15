package com.run.student.vo;


import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentVo {
    private String sid;
    private String name;
    private String sex;
    private String tel; //手机号码
    private String email;
    private String className;
    private String major;
    private String department;  //学院
    private String counsellor;  // 辅导员
    private String origin;  //生源地
    private String status;  //政治面貌
    private String nation; //民族
    private String dormitory;   //宿舍楼号
    private Integer bedroom;    //寝号
    private Integer bed;    //床号
    private LocalDate admissionDate;    //入学日期
    private LocalDate birth;    //出生日期
    private String from;    //来源
}
