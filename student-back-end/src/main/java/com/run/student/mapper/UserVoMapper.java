package com.run.student.mapper;

import com.run.student.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserVoMapper {
    public List<UserVo> getAllUser();
    public UserVo getUserById(Integer uid);
    public UserVo checkLogin(Integer uid, String password);
    public void getUserTotalByGroup(Map<String, Integer> map);
}
