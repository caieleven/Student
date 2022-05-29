package com.run.student.controller;


import com.run.student.entity.AdditionalTable;
import com.run.student.service.AdditionalTableService;
import com.run.student.service.UserService;
import com.run.student.utils.Result;
import com.run.student.vo.AdditionalTableVo;
import com.run.student.vo.UserVo;
import org.apache.commons.lang3.ObjectUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-26
 */
@RestController
@RequestMapping("/additionalTable")
public class AdditionalTableController {
    @Autowired
    AdditionalTableService additionalTableService;

    @Autowired
    UserService userService;


    /**
     * @param table 其中必需包含表名，counsellor_id
     * @return
     */
    @PostMapping("/addNewTable")
    public Result<Object> addNewTable(@RequestBody Map<String, Object> map){
        // 添加新表，表中存储的为表和辅导员/助手的关系，添加新表也是增添关系
        // 先进行简单判断
        String tableName = (String)map.get("tableName");
        Integer counsellorId = (Integer) map.get("counsellorId");
        if(ObjectUtils.isEmpty(tableName)){
            return Result.fail("表名为空，添加失败");
        }
        //此处判断可以删除
        if(ObjectUtils.isEmpty(counsellorId)){
            return Result.fail("没有添加用户ID， 添加失败");
        }
        boolean save = additionalTableService.save(map);
        if(save){
            return Result.success();
        }
        return Result.fail("表名重复");
    }

    /**
     * 更新表名,暂时弃用
     * @param table 其中，需包括aid，counsellorId，assistantId
     * @return
     */
    @PostMapping("/updateTable")
    public Result<Object> updateTable(@RequestBody AdditionalTable table){
        //注意此方法通过主键完成
        boolean update = additionalTableService.saveOrUpdate(table);
        if(update){
            return Result.success();
        }
        return Result.fail("更新失败");
    }

    /**
     * 根据uid和tablename查询所管理的表，返回map型
     * @param uid  用户id
     * @param tableName 不为必须，表名关键词
     * @return
     */
    @GetMapping("/getTable")
    public Result<Object> query(@RequestParam Integer uid,
                                @RequestParam(defaultValue = "") String tableName){
        UserVo user = userService.getUserById(uid);
        AdditionalTable table = new AdditionalTable();
        if(user.getGroupName().equals("counsellor")){
            table.setCounsellorId(uid);
        }
        else if(user.getGroupName().equals("assistant")){
            table.setAssistantId(uid);
        }
        // 若为admin，查询所有表格
        else {
            table.setAssistantId(null);
        }
        table.setTableName(tableName);
        Result<Object> result = Result.success();
        List<AdditionalTableVo> list =  additionalTableService.list(table);
        Map<String, List<AdditionalTableVo>> collect = list.stream().collect(Collectors.groupingBy(AdditionalTableVo::getTableName));
        result.setData(collect);
        return result;
    }

    /**
     * 未表添加助手，即给助手分配权限
     * @param map 其中需包含tableName，counsellorId，以及assistantId数组
     * @return
     */
    @PutMapping("/addAssistantToTable")
    public Result<Object> addAssistantToTable(@RequestBody Map<String, Object> map){
        if(!map.containsKey("counsellorId"))
            return Result.fail("没有当前用户id");
        if(!map.containsKey("tableName"))
            return Result.fail("没有表名");
        if(!map.containsKey("assistantId"))
            return Result.fail("没有待分配表权限的用户");
        additionalTableService.addAsistantToTable(map);
        return Result.success();
    }
}

