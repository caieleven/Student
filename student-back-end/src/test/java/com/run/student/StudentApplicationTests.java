package com.run.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.run.student.entity.Student;
import com.run.student.entity.User;
import com.run.student.mapper.StudentMapper;
import com.run.student.mapper.StudentVoMapper;
import com.run.student.mapper.UserMapper;
import com.run.student.service.StudentService;
import com.run.student.service.UserService;
import com.run.student.vo.StudentVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.summarizingDouble;
import static java.util.stream.Collectors.toSet;

@SpringBootTest
class StudentApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentVoMapper studentVoMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testMapper(){
        StudentVo studentVo = studentVoMapper.getStudentById("19122206");
        System.out.print(studentVo.getName());
        System.out.print("success");
    }

    @Test
    void testGetAll(){
        studentVoMapper.getAllStudents();
    }

    @Test
    void testWrapper(){

        QueryWrapper<StudentVo> wrapper = new QueryWrapper<>();
        wrapper.eq("class_name", "19级分流计科2班");
        PageHelper.startPage(1, 4);
//        final List<StudentVo> list = studentMapper.list(wrapper);
//        PageInfo<StudentVo> studentVoPageInfo = new PageInfo<>(list);
//        final long total1 = studentVoPageInfo.getTotal();
//        for(StudentVo s : list){
//            System.out.println(s.getName());
//        }
//        System.out.print(total1 + "_____");

//        PageHelper.startPage(pageNum, pageSize);
//        List<StudentVo> data = studentService.getSpecialStudent(sid, studentName, cid, status);
//        PageInfo<StudentVo> studentVoPageInfo = new PageInfo<>(data);
//        Integer total = (int) studentVoPageInfo.getTotal();
//        Result<Object> result = new Result<>();
//        result.setData(data);
//        result.setCount(total);
    }

    @Test
    void testListWrapper(){
       List<String> columns = new ArrayList<>();
       columns.add("sid");
       columns.add("name");
       List<Long> sids = new ArrayList<>();
       sids.add((long) 19122206);
       sids.add((long) 19122207);
//       List<Map<String, Object>> maps = studentService.queryStudent(columns, sids);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("sid", sids);
        queryWrapper.select("sid", "name");
        final List<Student> students = studentMapper.selectList(queryWrapper);
        students.forEach(System.out::println);

    }

    @Test
    void testGetManagedUsers(){
        String str = "";
        if(str.isEmpty())
            System.out.println("hello");
    }

}
