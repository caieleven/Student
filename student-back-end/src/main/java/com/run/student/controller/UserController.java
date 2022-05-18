package com.run.student.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.run.student.entity.User;
import com.run.student.mapper.UserVoMapper;
import com.run.student.service.UserService;
import com.run.student.utils.Result;
import com.run.student.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

//    @PostMapping("/login")
//    public Result<Object> login(@RequestParam String uid,
//                                     @RequestParam String password){
////        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//////        queryWrapper.select("uid", "password").eq(uid, password);
////        queryWrapper.eq("password", password);
////        queryWrapper.eq("uid",uid);
////        queryWrapper.select("username", "group_id", "permission");
////        User user = userService.getOne(queryWrapper);
//
//        UserVo user = userService.checkLogin(Integer.valueOf(uid), password);
//        Result<Object> result = new Result<>();
////        Map<String, Object> map = new HashMap<>();
//        if(user != null){
////            map.put("isSuccess",true);
//            result.setCode(0);
//            result.setMessage("success");
//        }
//        else {
////            map.put("isSuccess", false);
//            result.setCode(-1);
//            result.setMessage("wrong uid or password");
//        }
//        result.setData(user);
//        return result;
////        map.put("userInfo", user);
////        return map;
////        return !(userService.list(queryWrapper).isEmpty());
//    }

    @PostMapping("/login")
    public Result<Object> login(@RequestBody User tempUser){
        UserVo user = userService.checkLogin(Integer.valueOf(tempUser.getUid()), tempUser.getPassword());
        Result<Object> result = new Result<>();
//        Map<String, Object> map = new HashMap<>();
        if(user != null){
//            map.put("isSuccess",true);
            result.setCode(0);
            result.setMessage("success");
        }
        else {
//            map.put("isSuccess", false);
            result.setCode(-1);
            result.setMessage("wrong uid or password");
        }
        result.setData(user);
        return result;

    }



    @GetMapping("/getAllUsers")
    public Result<Object> getAll(){
        Result<Object> result = Result.success();
        result.setData(userService.getAllUsers());
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


}

