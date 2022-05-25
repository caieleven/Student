package com.run.student.mapper;

import com.run.student.entity.Major;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
@Mapper
@Repository
public interface MajorMapper extends BaseMapper<Major> {

}
