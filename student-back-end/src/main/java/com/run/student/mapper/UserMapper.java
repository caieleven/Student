package com.run.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.run.student.pojo.User;
import org.springframework.stereotype.Repository;

@Repository //代表持久层
public interface UserMapper extends BaseMapper<User> {
}
