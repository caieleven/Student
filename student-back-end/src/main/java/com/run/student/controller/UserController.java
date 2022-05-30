package com.run.student.controller;


import com.run.student.entity.User;
import com.run.student.service.AdditionalTableService;
import com.run.student.service.UserService;
import com.run.student.utils.Result;
import com.run.student.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if(user.getPermission() == 0){
            return Result.fail("该账户已失效");
        }
        Result<Object> result = new Result<>();
        if(user != null){
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

    @PostMapping("/getUser")
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


    //需增添修改密码的方法

}

