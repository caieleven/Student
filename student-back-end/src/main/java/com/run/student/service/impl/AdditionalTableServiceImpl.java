package com.run.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.run.student.entity.AdditionalTable;
import com.run.student.entity.User;
import com.run.student.mapper.AdditionalTableMapper;
import com.run.student.service.AdditionalTableService;
import com.run.student.service.MongoService;
import com.run.student.service.StudentService;
import com.run.student.service.UserService;
import com.run.student.utils.StudentQuery;
import com.run.student.vo.AdditionalTableVo;
import com.run.student.vo.StudentVo;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-26
 */
@Service
public class AdditionalTableServiceImpl extends ServiceImpl<AdditionalTableMapper, AdditionalTable> implements AdditionalTableService {


    @Autowired
    MongoService mongoService;

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;


    /**
     * 根据条件，查找表
     * @param additionalTable
     * @return
     */
    @Override
    public List<AdditionalTableVo> list(AdditionalTable additionalTable) {
        QueryWrapper<AdditionalTable> wrapper = new QueryWrapper<>();
        final Integer assistantId = additionalTable.getAssistantId();
        final Integer counsellorId = additionalTable.getCounsellorId();
        final String tableName = additionalTable.getTableName();
        if (!ObjectUtils.isEmpty(assistantId)) {
            wrapper.eq("assistant_id", assistantId);
        }
        if (!ObjectUtils.isEmpty(counsellorId)) {
            wrapper.eq("counsellor_id", counsellorId);
        }
        if (!ObjectUtils.isEmpty(tableName)){
            wrapper.like("table_name", tableName);
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
        for (Integer id : assistantsId) {
            table.setAssistantId(id);
            baseMapper.insert(table);
        }
        return true;
    }


    // 添加新表，map中包含的内容参考Controller层
    @Override
    public boolean save(Map<String, Object> map) {
        String tableName = (String)map.get("tableName");
        Integer counsellorId = (Integer) map.get("counsellorId");
        map.remove("counsellorId");
        AdditionalTable entity = new AdditionalTable();
        entity.setTableName(tableName);
        entity.setCounsellorId(counsellorId);
        QueryWrapper<AdditionalTable> wrapper = new QueryWrapper<>();
        wrapper.eq("counsellor_id", entity.getAssistantId());
        wrapper.eq("table_name", entity.getTableName());
        //命名重复
        if (count(wrapper) > 0) {
            return false;
        }
        //基本表中插入关系
        baseMapper.insert(entity);
        //附加表中插入集合
        //考虑到不同用户创建的表名可能相同，所以，在mongo中附加表（集合）名为表名+counsellor_id
        mongoService.createCollection(tableName+counsellorId);
        //在map中也需要更改
        map.replace("tableName", tableName+counsellorId);
        //此时map中剩下tableName, baseColumns, additionalColumns;
        //添加sids字段
        map.put("sids", new ArrayList<Long>());
        mongoService.insertOne("TableInfo", map);
        return true;
    }

    @Override
    public List<String> getTableNameByUid(Integer uid, String groupName) {
        QueryWrapper<AdditionalTable> queryWrapper = new QueryWrapper<>();
        if(groupName.equals("counsellor")){
            queryWrapper.eq("counsellor_id", uid);
        }
        else if(groupName.equals("assistant")){
            queryWrapper.eq("assistant_id", uid);
        }
        return baseMapper.getTableName(queryWrapper);
    }


    /**
     * 添加学生到附加表中
     * @param map 需保证包含sids:[]，uid，tableName
     * @return
     */
    @Override
    public boolean addStudentToTable(Map<String, Object> map) {
        //先判断当前用户是否拥有此表
        Integer uid = (Integer) map.get("uid");
        String tableName = (String) map.get("tableName");
        List<Long> sids = (List<Long>) map.get("sids");
        QueryWrapper<AdditionalTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("counsellor_id", uid);
        queryWrapper.eq("table_name", tableName);
        // 没有该表
        if(count(queryWrapper) == 0){
            return false;
        }
        return mongoService.updateSids(tableName+uid, sids);
    }

    /**
     * 从基本表和附加表中取数据，暂时不支持分页查询
     * @param uid 请求用户的id
     * @param tableName 附加表表名
     * @param queryMap 查询条件
     * @return
     */
    @Override
    public List<Map<String, Object>> queryStudents(Integer uid, String tableName, Map<String, Object> queryMap) {
        //获取用户身份，根据身份得到mongo中集合名
        User user = userService.getById(uid);
        Integer counsellor_id = user.getGroupId().equals(2) ? uid : user.getFid();
        //从mongo中获取表的信息，包括baseColumns, additionalColumns, sids
        final Map<String, Object> tableInfo = mongoService.getAllInfoInTableInfo(tableName + counsellor_id);
        List<String> baseColumns = (List<String>) tableInfo.get("baseColumns");
        List<String> additionalColumns = (List<String>) tableInfo.get("additionalColumns");
        List<Long> sids = (List<Long>) tableInfo.get("sids");

        List<Map<String, Object>> result = new ArrayList<>();

        //查询基本表中的内容
        final List<Map<String, Object>> maps = studentService.queryStudent(baseColumns, sids);
        for(Map<String, Object> map : maps){
            Map<String, Object> student = new HashMap<>();
            for(String column : baseColumns){
                student.put(column, map.get(column));
            }
            //查询附加表中的内容
            Map<String, Object> addInfo = mongoService.queryBySid(tableName + counsellor_id, (Long) map.get("sid"));
            student.put("additionalInfo", addInfo);
            //添加到结果列表中
            result.add(student);
        }
        return result;
    }

    @Override
    public boolean updateOne(Integer uid, String tableName, Map<String, Object> infoMap) {
        //获取用户身份，根据身份得到mongo中集合名
        User user = userService.getById(uid);
        Integer counsellor_id = user.getGroupId().equals(2) ? uid : user.getFid();
        mongoService.updateOne(tableName+counsellor_id, infoMap);
        return false;
    }


}
