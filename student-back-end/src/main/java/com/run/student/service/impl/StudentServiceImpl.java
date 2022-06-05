package com.run.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.student.entity.Student;
import com.run.student.mapper.ClassMapper;
import com.run.student.mapper.StudentMapper;
import com.run.student.mapper.StudentVoMapper;
import com.run.student.service.StudentService;
import com.run.student.utils.StudentQuery;
import com.run.student.vo.StudentVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentVoMapper studentVoMapper;

    @Autowired
    @Resource
    StudentMapper studentMapper;

    @Override
    public List<StudentVo> list() {
        return studentVoMapper.getAllStudents();
    }

    @Override
    public List<StudentVo> selectPage(Integer pageNum, Integer pageSize) {
        return studentVoMapper.selectPage(pageNum, pageSize);
    }

    @Override
    public Page<Student> searchPage(Page<Student> page, QueryWrapper<Student> queryWrapper) {
        return studentMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int selectCount(QueryWrapper<Student> queryWrapper) {
        return studentMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer getTotalNum() {
        return studentMapper.selectCount(null);
    }

    @Override
    public int insert(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    public int updateById(Student student) {
        return studentMapper.updateById(student);
    }

    @Override
    public int deleteById(int id) {
        return studentMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Integer> sids) {
        return studentMapper.deleteBatchIds(sids);
    }

    @Override
    public List<StudentVo> queryPage(StudentQuery studentQuery) {
        QueryWrapper<StudentVo> wrapper = new QueryWrapper<>();
        if(studentQuery == null){
            return studentMapper.list(wrapper);
        }
        String name = studentQuery.getName();
        List<Long> sid = studentQuery.getSid();
        List<String> sex = studentQuery.getSex();
        List<Integer> cid = studentQuery.getCid(); //多选->班级id
        List<String> status = studentQuery.getStatus(); // 多选->政治面貌
        String dormitory = studentQuery.getDormitory(); // 宿舍楼关键词 如；新世纪、南区
        String background = studentQuery.getBackground(); // 来源关键词 如：退伍、分流
        String origin = studentQuery.getOrigin(); //   生源地关键词
        if(!ObjectUtils.isEmpty(name))
            wrapper.like("name", name);
        if(!ObjectUtils.isEmpty(sex))
            wrapper.in("sex", sex);
        if(!ObjectUtils.isEmpty(cid))
            wrapper.in("cid", cid);
        if(!ObjectUtils.isEmpty(status))
            wrapper.in("status", status);
        if(!ObjectUtils.isEmpty(dormitory))
            wrapper.like("dormitory", dormitory);
        if(!ObjectUtils.isEmpty(background))
            wrapper.like("background", background);
        if(!ObjectUtils.isEmpty(origin))
            wrapper.like("origin", origin);
        if(!ObjectUtils.isEmpty(sid))
            wrapper.in("sid", sid);
        return studentMapper.list(wrapper);
    }

    //无法生效
    @Override
    public List<Map<String, Object>> queryStudent(List<String> columns, List<Long> sids) {
        QueryWrapper<StudentVo> wrapper = new QueryWrapper<>();
        for(String column : columns){
            wrapper.select(column);
        }
        if(ObjectUtils.isEmpty(sids)){
            return null;
        }
        wrapper.in("sid", sids);
        return studentMapper.queryStudent(wrapper);
    }

//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Override
//    public List<StudentVo> testMongo(StudentQuery studentQuery) {
//        QueryWrapper<StudentVo> wrapper = new QueryWrapper<>();
//        if(studentQuery == null){
//            return studentMapper.list(wrapper);
//        }
//        String name = studentQuery.getName();
//        List<Long> sid = studentQuery.getSid();
//        List<String> sex = studentQuery.getSex();
//        List<Integer> cid = studentQuery.getCid(); //多选->班级id
//        List<String> status = studentQuery.getStatus(); // 多选->政治面貌
//        String dormitory = studentQuery.getDormitory(); // 宿舍楼关键词 如；新世纪、南区
//        String background = studentQuery.getBackground(); // 来源关键词 如：退伍、分流
//        String origin = studentQuery.getOrigin(); //   生源地关键词
//        if(!ObjectUtils.isEmpty(name))
//            wrapper.like("name", name);
//        if(!ObjectUtils.isEmpty(sex))
//            wrapper.in("sex", sex);
//        if(!ObjectUtils.isEmpty(cid))
//            wrapper.in("cid", cid);
//        if(!ObjectUtils.isEmpty(status))
//            wrapper.in("status", status);
//        if(!ObjectUtils.isEmpty(dormitory))
//            wrapper.like("dormitory", dormitory);
//        if(!ObjectUtils.isEmpty(background))
//            wrapper.like("background", background);
//        if(!ObjectUtils.isEmpty(origin))
//            wrapper.like("origin", origin);
//        if(!ObjectUtils.isEmpty(sid))
//            wrapper.in("sid", sid);
//        List<StudentVo> list = studentMapper.list(wrapper);
//
//        return list;
//    }


    @Override
    public Student isExist(int id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<StudentVo> getSpecialStudent(String sid, String studentName, String cid, String status) {
        return studentVoMapper.getSpecialStudent(sid, studentName, cid, status);
    }

}
