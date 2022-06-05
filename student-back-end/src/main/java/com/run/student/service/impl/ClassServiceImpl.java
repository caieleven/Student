package com.run.student.service.impl;

import com.run.student.entity.Class;
import com.run.student.mapper.ClassMapper;
import com.run.student.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Autowired
    @Resource
    ClassMapper classMapper;

    @Override
    public Integer selectCidByCname(String className) {
        return classMapper.selectCidByCname(className);
    }

}
