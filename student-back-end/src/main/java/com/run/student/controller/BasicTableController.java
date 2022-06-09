package com.run.student.controller;


import com.run.student.entity.Department;
import com.run.student.service.ClassService;
import com.run.student.service.DepartmentService;
import com.run.student.service.MajorService;
import com.run.student.utils.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/*
    基本表管理
 */
@RestController
@RequestMapping("/basicTable")
public class BasicTableController {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    ClassService classService;

    @Autowired
    MajorService majorService;

    /**
     * 增加新的院系，需提供院系名，院系号自动生成
     * @param departmentName 院系名
     * @return
     */
    @PostMapping("/insertNewDepartment")
    public Result<Object> insertNewDepartment(@RequestParam String departmentName){
        if(ObjectUtils.isEmpty(departmentName))
            return Result.fail("院系名为空");

        Department department = new Department();
        department.setName(departmentName);
        final boolean save = departmentService.save(department);
        if(save)
            return Result.success();
        return Result.fail("新增失败");
    }


//    @GetMapping("list")



}
