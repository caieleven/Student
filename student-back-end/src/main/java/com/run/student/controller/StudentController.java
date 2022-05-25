package com.run.student.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.run.student.entity.Student;
import com.run.student.service.StudentService;
import com.run.student.utils.Result;
import com.run.student.vo.StudentVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    @GetMapping("/all")
    public Result getAllStudent(){
        Result<Object> result = Result.success();
        result.setData(studentService.list());
        return result;
    }


    /**
     * 分页查询
     * @param pageNum   第几页
     * @param pageSize  页的大小
     * @return  List数据和总数
     */
    @GetMapping("page")
    public Result<Object> getPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String userName){
        pageNum = (pageNum - 1)* pageSize;
        List<StudentVo> data = studentService.selectPage(pageNum, pageSize);
        Integer total = studentService.getTotalNum();
        Result<Object> result = new Result<>();
        result.setData(data);
        result.setCount(total);
        return result;
    }

    // 根据姓名和学号模糊查询
    @GetMapping("specialStudent")
    public Result<Object> getSpecialStudent(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @RequestParam(defaultValue = "") String studentName,
                                            @RequestParam(defaultValue = "") String sid) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentVo> data = studentService.getSpecialStudent(sid, studentName);
        PageInfo<StudentVo> studentVoPageInfo = new PageInfo<>(data);
        Integer total = (int) studentVoPageInfo.getTotal();
        Result<Object> result = new Result<>();
        result.setData(data);
        result.setCount(total);
        return result;
    }

    // 根据班级查询
    @GetMapping("selectByClass")
    public Result<Object> getStudentByClass(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @RequestParam(defaultValue = "") Integer cid) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentVo> data = studentService.getStudentByClass(cid, pageNum, pageSize);
        PageInfo<StudentVo> studentVoPageInfo = new PageInfo<>(data);
        Integer total = (int) studentVoPageInfo.getTotal();
        Result<Object> result = new Result<>();
        result.setData(data);
        result.setCount(total);
        return result;
    }

    //  新增
    @PostMapping
    public int insert(@RequestBody Student student) {
        return studentService.insert(student);
    }

    //  编辑
    @PutMapping
    public int update(@RequestBody Student student) {
        return studentService.updateById(student);
    }

    //删除
    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        return studentService.deleteById(id);
    }

    // 检查当前学号是否存在
    @PostMapping("/exist/{id}")
    public Student exist(@PathVariable int id) { return  studentService.isExist(id); }

}

