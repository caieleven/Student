package com.run.student.service.impl;

import com.run.student.entity.User;
import com.run.student.mapper.UserMapper;
import com.run.student.mapper.UserVoMapper;
import com.run.student.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.run.student.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserVoMapper userVoMapper;

    @Override
    public List<UserVo> getAllUsers() {
        return userVoMapper.getAllUser();
    }

    @Override
    public UserVo getUserById(Integer uid) {
        return userVoMapper.getUserById(uid);
    }

    @Override
    public UserVo checkLogin(Integer uid, String password) {
        return userVoMapper.checkLogin(uid, password);
    }
}
