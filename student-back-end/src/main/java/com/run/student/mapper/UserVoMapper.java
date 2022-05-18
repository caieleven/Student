package com.run.student.mapper;

import com.run.student.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserVoMapper {
    public List<UserVo> getAllUser();
    public UserVo getUserById(Integer uid);
    public UserVo checkLogin(Integer uid, String password);

}
