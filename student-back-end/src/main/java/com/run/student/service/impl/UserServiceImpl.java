package com.run.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.run.student.entity.User;
import com.run.student.mapper.UserMapper;
import com.run.student.mapper.UserVoMapper;
import com.run.student.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.run.student.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> getCounsellors(Integer uid) {
        User currentUser = baseMapper.selectById(uid);
        //判断身份是否为admin，此处逻辑较为矛盾，和权限认证杂糅
        if(currentUser.getGroupId() != 1){
            return null;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", 2);
        List<UserVo> list = baseMapper.list(queryWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        //为了前端方便管理，此处不直接返回UserVo
        for(UserVo user : list){
            Map<String, Object> map = new HashMap<>();
            map.put("uid", user.getUid());
            map.put("username", user.getUsername());
            map.put("groupName", user.getGroupName());
            map.put("permission", user.getPermission());
            map.put("fName", user.getFName());
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getAssistant(Integer uid) {
        User currentUser = baseMapper.selectById(uid);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", 3);
        // 如果为辅导员，只能查看自己管理的助手，若为admin，无限制
        if(currentUser.getGroupId() == 2){
            queryWrapper.eq("fid", uid);
        }
        //若为助手，不应返回数据
        if(currentUser.getGroupId() == 3){
            return null;
        }
        List<UserVo> list = baseMapper.list(queryWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        //为了前端方便管理，此处不直接返回UserVo
        for(UserVo user : list){
            Map<String, Object> map = new HashMap<>();
            map.put("uid", user.getUid());
            map.put("username", user.getUsername());
            map.put("groupName", user.getGroupName());
            map.put("permission", user.getPermission());
            map.put("fName", user.getFName());
            result.add(map);
        }
        return result;
    }

    @Override
    public UserVo getUserById(Integer uid) {
        return userVoMapper.getUserById(uid);
    }

    @Override
    public UserVo checkLogin(Integer uid, String password) {
        return userVoMapper.checkLogin(uid, password);
    }

    @Override
    public void getUserTotalByGroup(Map<String, Integer> map) {
        userVoMapper.getUserTotalByGroup(map);
    }
}
