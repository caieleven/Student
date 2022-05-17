package com.run.student.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.run.student.entity.User;
import com.run.student.service.UserService;
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

    @GetMapping("/all")
    public List<User> getUser() {
        return userService.list();
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam Integer uid,
                         @RequestParam String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("uid", "password").eq(uid, password);
        queryWrapper.eq("password", password);
        queryWrapper.eq("uid",uid);
        queryWrapper.select("username", "group_id", "permission");
        User user = userService.getOne(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        if(user != null){
            map.put("isSuccess",true);
        }
        else {
            map.put("isSuccess", false);
        }
        map.put("userInfo", user);
        return map;
//        return !(userService.list(queryWrapper).isEmpty());
    }


}

