package com.run.student.controller;


import com.run.student.entity.User;
import com.run.student.service.AdditionalTableService;
import com.run.student.service.UserService;
import com.run.student.utils.Result;
import com.run.student.vo.UserVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AdditionalTableService additionalTableService;


    @PostMapping("/login")
    public Result<Object> login(@RequestBody User tempUser){
        UserVo user = userService.checkLogin(Integer.valueOf(tempUser.getUid()), tempUser.getPassword());
        Result<Object> result = new Result<>();
        if(user != null){
            if(user.getPermission() == 0){
                return Result.fail("该账户已失效");
            }
            result.setCode(0);
            result.setMessage("success");
        }
        else {
            result.setCode(-1);
            result.setMessage("wrong uid or password");
        }
        result.setData(user);
        return result;
    }


    /**
     * 根据用户身份，返回用户信息，其中包括用户所管理的表
     * @Param 请求用户的uid
     * @return map，包含uid, userName, groupName, permission, tableNames([])
     */
    @GetMapping("/getAssistants")
    public Result<Object> getAssistants(@RequestParam Integer uid){
        final List<Map<String, Object>> assistant = userService.getAssistant(uid);
        for (Map<String, Object> map : assistant){
            final List<String> tableNames = additionalTableService.getTableNameByUid((Integer) map.get("uid"), (String) map.get("groupName"));
            map.put("tableNames", tableNames);
        }
        Result<Object> result = Result.success();
        result.setData(assistant);
        return result;
    }

    @GetMapping("/getCounsellors")
    public Result<Object> getCounsellors(@RequestParam Integer uid){
        final List<Map<String, Object>> counsellors = userService.getCounsellors(uid);
        for (Map<String, Object> map : counsellors){
            List<String> tableNames = additionalTableService.getTableNameByUid((Integer) map.get("uid"), (String) map.get("groupName"));
            map.put("tableNames", tableNames);
        }
        Result<Object> result = Result.success();
        result.setData(counsellors);
        return result;
    }

    @GetMapping("/getUser")
    public Result getUserById(@RequestParam Integer uid){
        UserVo user = userService.getUserById(uid);
        if(user == null)
        {
            return Result.fail("user not exists");
        }
        else {
            Result<Object> result = Result.success();
            result.setData(user);
            return result;
        }
    }

    /**
     * 添加用户
     * @param newUser User类型，包括username，password，group_id与fid
     *                其中，fid为当前发出请求用户的uid，如果fid为2，group_id只能为3
     * @return
     */
    @PostMapping("/addUser")
    public Result<Object> addUser(@RequestBody User newUser){
        User currentUser = userService.getById(newUser.getFid());
        if(ObjectUtils.isEmpty(newUser.getGroupId()))
            return Result.fail("没有划定权限组");
        if(ObjectUtils.isEmpty(newUser.getFid()))
            return Result.fail("没有包含父级用户");
        if(currentUser.getGroupId() >= newUser.getGroupId())
            return Result.fail("当前用户没有创建此类用户的权限");
        userService.save(newUser);
        UserVo userById = userService.getUserById(newUser.getUid());
        Result<Object> result = Result.success();
        result.setData(userById);
        return result;
    }
    

    /**
     * 更新user，更改用户名、密码、permission，无法更新uid、fid，以及用户组
     * @param user
     * @return
     */
    @PutMapping("updateUser")
    public Result<Object> updateUser(@RequestBody User user){
        final Integer uid = user.getUid();
        final String username = user.getUsername();
        final String password = user.getPassword();
        final Integer permission = user.getPermission();
        if(ObjectUtils.isEmpty(uid)){
            return Result.fail("没有包含uid，无法更新");
        }
        User byId = userService.getById(uid);
        if(ObjectUtils.isNotEmpty(username))
            byId.setUsername(username);
        if(ObjectUtils.isNotEmpty(password))
            byId.setPassword(password);
        if(ObjectUtils.isNotEmpty(permission))
            byId.setPermission(permission);
        if(userService.updateById(byId))
            return Result.success();
        return Result.fail("修改失败");
    }


    /**
     * 删除用户，如果为教师，则判断名下是否有活动表，为助手，直接删除
     * @return
     */
    @DeleteMapping("deleteUser/{uid}")
    public Result<Object> deleteUser(@PathVariable Integer uid){
        final boolean b = userService.deleteOne(uid);
        if(b)
            return Result.success();
        return Result.fail("删除失败，检查是否还存在活动表");
    }

    @GetMapping("getUserTotalByGroup")
    public Result<Object> getUserTotalByGroup() {
        Map<String, Integer> map = new HashMap<>();
        map.put("adminNum", null);
        map.put("counsellorNum", null);
        map.put("assistantNum", null);
        userService.getUserTotalByGroup(map);
        Result<Object> result = Result.success();
        result.setData(map);
        return result;
    }

}

