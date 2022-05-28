package com.run.student.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQuery {
    private String name;    //关键词
    private List<Long> sid; //学号 用于附加表与基本表的拼接
    private List<String> sex;
    private List<Integer> cid; //多选->班级id
    private List<String> status; // 多选->政治面貌
    private String dormitory; // 宿舍楼关键词 如；新世纪、南区
    private String background; // 来源关键词 如：退伍、分流
    private String origin; //   生源地关键词
}
