package com.run.student.service.impl;

import com.run.student.entity.Student;
import com.run.student.mapper.StudentMapper;
import com.run.student.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
