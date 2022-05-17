package com.run.student.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.run.student.entity.Class;
import com.run.student.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    ClassService classService;

    @GetMapping("/allCidAndName")
    public List<Class> getClassMap(){
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("cid", "name");
        List<Class> list = classService.list(queryWrapper);
        return list;
//        Map<String, Object> classMap = new HashMap<>();
//        classService.
    }




}

