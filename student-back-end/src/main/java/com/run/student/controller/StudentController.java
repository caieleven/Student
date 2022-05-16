package com.run.student.controller;


import com.run.student.entity.Student;
import com.run.student.service.StudentService;
import com.run.student.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;


    @GetMapping("/all")
    public List<StudentVo> getAllStudent(){
        return studentService.list();
    }


    /**
     * 分页查询
     * @param pageNum   第几页
     * @param pageSize  页的大小
     * @return  List数据和总数
     */
    @GetMapping("page")
    public Map<String, Object> getPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") String userName){
        pageNum = (pageNum - 1)* pageSize;
        List<StudentVo> data = studentService.selectPage(pageNum, pageSize);
        Integer total = studentService.getTotalNum();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }

}

