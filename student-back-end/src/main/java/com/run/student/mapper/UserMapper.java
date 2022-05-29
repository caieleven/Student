package com.run.student.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.run.student.entity.User;
import com.run.student.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
public interface UserMapper extends BaseMapper<User> {
    List<UserVo> list(@Param("ew") Wrapper<User> queryWrapper);
}
