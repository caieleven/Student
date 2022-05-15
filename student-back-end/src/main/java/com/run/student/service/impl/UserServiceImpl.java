package com.run.student.service.impl;

import com.run.student.entity.User;
import com.run.student.mapper.UserMapper;
import com.run.student.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
