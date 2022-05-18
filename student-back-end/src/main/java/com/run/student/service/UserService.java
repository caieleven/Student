package com.run.student.service;

import com.run.student.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.run.student.vo.UserVo;

import java.util.List;


public interface UserService extends IService<User> {
    public List<UserVo> getAllUsers();
    public UserVo getUserById(Integer uid);
    public UserVo checkLogin(Integer uid, String password);
}
