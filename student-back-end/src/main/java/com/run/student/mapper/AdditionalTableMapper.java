package com.run.student.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.run.student.entity.AdditionalTable;
import com.run.student.vo.AdditionalTableVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-26
 */
public interface AdditionalTableMapper extends BaseMapper<AdditionalTable> {
    List<AdditionalTableVo> list(@Param("ew") Wrapper<AdditionalTable> queryWrapper);
}
