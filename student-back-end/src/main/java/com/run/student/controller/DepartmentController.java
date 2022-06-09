package com.run.student.controller;


import com.run.student.service.DepartmentService;
import com.run.student.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


//    @PostMapping("/addNewDepartment")
//    public Result<Object> addNewDepartment(@RequestParam Integer did){
//
//    }

}

