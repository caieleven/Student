package com.run.student.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.student.entity.Student;
import com.run.student.vo.StudentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
public interface StudentMapper extends BaseMapper<Student> {
    List<StudentVo> list(@Param(Constants.WRAPPER) Wrapper<StudentVo> queryWrapper);
    /*
     * 返回的total为0，原因未知
     */
//    IPage<StudentVo> list(@Param("page") Page<StudentVo> page, @Param("ew") Wrapper<StudentVo> queryWrapper);

}

