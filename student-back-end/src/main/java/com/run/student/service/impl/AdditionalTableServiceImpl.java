package com.run.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.run.student.entity.AdditionalTable;
import com.run.student.mapper.AdditionalTableMapper;
import com.run.student.service.AdditionalTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.run.student.vo.AdditionalTableVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<AdditionalTableVo> list(AdditionalTable additionalTable) {
        QueryWrapper<AdditionalTable> wrapper = new QueryWrapper<>();
        final Integer assistantId = additionalTable.getAssistantId();
        final Integer counsellorId = additionalTable.getCounsellorId();
        if(!ObjectUtils.isEmpty(assistantId)){
            wrapper.eq("assistant_id", assistantId);
        }
        if(!ObjectUtils.isEmpty(counsellorId)){
            wrapper.eq("counsellor_id", counsellorId);
        }
        final List<AdditionalTableVo> list = baseMapper.list(wrapper);
        return list;
    }

    @Override
    public boolean addAsistantToTable(Map<String, Object> map) {
        //基本表中增添记录
        List<Integer> assistantsId = (List<Integer>) map.get("assistantId");
        String tableName = (String) map.get("tableName");
        Integer counsellorId = (Integer) map.get("counsellorId");
        // 排查已经拥有改表格权限的助手
        QueryWrapper<AdditionalTable> wrapper = new QueryWrapper<>();
        wrapper.eq("table_name", tableName);
        wrapper.eq("counsellor_id", counsellorId);
        wrapper.in("assistant_id", assistantsId);
        final List<AdditionalTable> additionalTables = baseMapper.selectList(wrapper);
        List<Integer> alreadyExist = additionalTables.stream().map(AdditionalTable::getAssistantId).collect(Collectors.toList());
        // 去重
        assistantsId.removeAll(alreadyExist);
        //插入
        AdditionalTable table = new AdditionalTable();
        table.setCounsellorId(counsellorId);
        table.setTableName(tableName);
        for(Integer id : assistantsId){
            table.setAssistantId(id);
            baseMapper.insert(table);
        }
        return true;
    }

    @Override
    public boolean save(AdditionalTable entity) {
        QueryWrapper<AdditionalTable> wrapper = new QueryWrapper<>();
        wrapper.eq("colusellor_id", entity.getAssistantId());
        wrapper.eq("counsello_id", entity.getTableName());
        //命名重复
        if(count(wrapper) > 1){
            return false;
        }
        //基本表中插入关系
        baseMapper.insert(entity);
        //附加表中插入集合
        //考虑到不同用户创建的表名可能相同，所以，在mongo中附加表（集合）名为表名+counsellor_id
        mongoTemplate.createCollection(entity.getTableName()+entity.getCounsellorId());
        return true;
    }
}
