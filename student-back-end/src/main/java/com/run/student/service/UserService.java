package com.run.student.service;

import com.run.student.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.run.student.vo.UserVo;

import java.util.List;
import java.util.Map;


public interface UserService extends IService<User> {
    List<UserVo> getCounsellors();
    List<Map<String, Object>> getCounsellors(Integer uid);
    List<Map<String, Object>> getAssistant(Integer uid);
    UserVo getUserById(Integer uid);
    UserVo checkLogin(Integer uid, String password);
}
