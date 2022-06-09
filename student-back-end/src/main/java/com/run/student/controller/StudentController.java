package com.run.student.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.run.student.entity.Student;
import com.run.student.service.ClassService;
import com.run.student.service.StudentService;
import com.run.student.utils.Result;
import com.run.student.utils.StudentQuery;
import com.run.student.vo.StudentVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    ClassService classService;


    @GetMapping("/all")
    public Result getAllStudent() {
        Result<Object> result = Result.success();
        result.setData(studentService.list());
        return result;
    }


    /**
     * 分页查询
     *
     * @param pageNum  第几页
     * @param pageSize 页的大小
     * @return List数据和总数
     */
//    @GetMapping("page")
//    public Result<Object> getPage(@RequestParam Integer pageNum,
//                                  @RequestParam Integer pageSize,
//                                  @RequestParam(defaultValue = "") String userName) {
//        pageNum = (pageNum - 1) * pageSize;
//        List<StudentVo> data = studentService.selectPage(pageNum, pageSize);
//        Integer total = studentService.getTotalNum();
//        Result<Object> result = new Result<>();
//        result.setData(data);
//        result.setCount(total);
//        return result;
//    }

    // 根据姓名和学号模糊查询
    @GetMapping("specialStudent")
    public Result<Object> getSpecialStudent(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @RequestParam(defaultValue = "") String studentName,
                                            @RequestParam(defaultValue = "") String sid,
                                            @RequestParam(defaultValue = "") String cid,
                                            @RequestParam(defaultValue = "") String status) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentVo> data = studentService.getSpecialStudent(sid, studentName, cid, status);
        PageInfo<StudentVo> studentVoPageInfo = new PageInfo<>(data);
        Long total = studentVoPageInfo.getTotal();
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

    // 批量删除
    @PostMapping("/deleteBatch")
    public int deleteBatch(@RequestBody List<Integer> sids) {
        return studentService.deleteBatch(sids);
    }

    // 检查当前学号是否存在
    @PostMapping("/exist/{id}")
    public Student exist(@PathVariable int id) {
        return studentService.isExist(id);
    }

    @PostMapping("/queryPage/{pageNum}/{pageSize}")
    public Result<Object> pageQuery(@PathVariable("pageNum") Integer pageNum,
                                    @PathVariable("pageSize") Integer pageSize,
                                    @RequestBody StudentQuery studentQuery) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentVo> data = studentService.queryPage(studentQuery);
        PageInfo<StudentVo> pageInfo = new PageInfo<>(data);
        Result<Object> result = new Result<>();
        result.setData(data);
        result.setCount(pageInfo.getTotal());
        return result;
    }

    @GetMapping("/export")
        public void export(HttpServletResponse response) throws Exception {
        List<StudentVo> list= studentService.list();
        //用工具类创建writer，写出到浏览器
            ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("sid","学号");
        writer.addHeaderAlias("name","姓名");
        writer.addHeaderAlias("className","班级");
        writer.addHeaderAlias("major","专业");
        writer.addHeaderAlias("department","学院");
        writer.addHeaderAlias("counsellor","辅导员");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("tel","电话");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("origin","生源地");
        writer.addHeaderAlias("status","政治面貌");
        writer.addHeaderAlias("nation","民族");
        writer.addHeaderAlias("dormitory","宿舍楼号");
        writer.addHeaderAlias("bedroom","室号");
        writer.addHeaderAlias("bed","床号");
        writer.addHeaderAlias("admissionDate","入学日期");
        writer.addHeaderAlias("birth","出生日期");
        writer.addHeaderAlias("background","来源");
        writer.setOnlyAlias(true);
        //写出list内的对象到excel，强制输出标题
        writer.write(list,true);
        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("学号","sid");
        reader.addHeaderAlias("姓名","name");
        reader.addHeaderAlias("班级","className");
        reader.addHeaderAlias("专业","major");
        reader.addHeaderAlias("学院","department");
        reader.addHeaderAlias("辅导员","counsellor");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("电话","tel");
        reader.addHeaderAlias("邮箱","email");
        reader.addHeaderAlias("生源地","origin");
        reader.addHeaderAlias("政治面貌","status");
        reader.addHeaderAlias("民族","nation");
        reader.addHeaderAlias("宿舍楼号","dormitory");
        reader.addHeaderAlias("室号","bedroom");
        reader.addHeaderAlias("床号","bed");
        reader.addHeaderAlias("入学日期","admissionDate");
        reader.addHeaderAlias("出生日期","birth");
        reader.addHeaderAlias("来源","background");
        List<StudentVo> list = reader.readAll(StudentVo.class);
        for(int i=0; i<list.size(); i++) {
            Student student = new Student();
            student.setSid(list.get(i).getSid());
            student.setName(list.get(i).getName());
            student.setClassId(classService.selectCidByCname(list.get(i).getClassName()));
            student.setSex(list.get(i).getSex());
            student.setTel(list.get(i).getTel());
            student.setEmail(list.get(i).getEmail());
            student.setOrigin(list.get(i).getOrigin());
            student.setStatus(list.get(i).getStatus());
            student.setNation(list.get(i).getNation());
            student.setDormitory(list.get(i).getDormitory());
            student.setBedroom(list.get(i).getBedroom());
            student.setBed(list.get(i).getBed());
            student.setAdmissionDate(list.get(i).getAdmissionDate());
            student.setBirth(list.get(i).getBirth());
            student.setBackground(list.get(i).getBackground());
            studentService.insert(student);
        }
        return true;
    }

}

