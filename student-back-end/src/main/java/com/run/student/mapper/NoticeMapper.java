package com.run.student.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.run.student.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.run.student.vo.StudentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告 Mapper 接口
 * </p>
 *
 * @author baorun chen
 * @since 2022-06-10
 */
public interface NoticeMapper extends BaseMapper<Notice> {
    List<Map<String, Object>> queryNotice(@Param(Constants.WRAPPER) Wrapper<Notice> queryWrapper);
}
