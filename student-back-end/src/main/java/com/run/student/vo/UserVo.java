package com.run.student.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL) //只有在非空时才发送
public class UserVo {
    private Integer uid;
    private String username;
    private String groupName;
    private Integer permission; //权限码
    private String fName;   //管理者
}
