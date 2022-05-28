package com.run.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.run.student.entity.AdditionalTable;
import com.run.student.mapper.AdditionalTableMapper;
import com.run.student.service.AdditionalTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.run.student.vo.AdditionalTableVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-26
 */
@Service
public class AdditionalTableServiceImpl extends ServiceImpl<AdditionalTableMapper, AdditionalTable> implements AdditionalTableService {

    @Override
    public List<AdditionalTableVo> list(AdditionalTable additionalTable) {
        QueryWrapper<AdditionalTableVo> wrapper = new QueryWrapper<>();
        List<AdditionalTableVo> list = new ArrayList<>();
        return list;
    }
}
