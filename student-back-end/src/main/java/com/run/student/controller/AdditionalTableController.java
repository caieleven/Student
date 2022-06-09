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
     * @param map 其中必需包含tableName:'',counsellorId, baseColumns:[], additionalColumns:[];
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


    @PostMapping("/updateTable/{uid}/{tableName}")
    public Result<Object> updateTable(@PathVariable Integer uid,
                                      @PathVariable String tableName,
                                      @RequestBody Map<String, Object> infoMap){
        additionalTableService.updateOne(uid, tableName, infoMap);
        return Result.success();
    }

    /**
     * 根据uid和tablename查询所管理的表，返回map型
     * @param uid  发出请求的用户id
     * @param tableName 不为必须，表名关键词，用以模糊查询
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
//            table.setAssistantId(null);
        }
        //模糊查询
        if(!tableName.isEmpty())
            table.setTableName(tableName);
        Result<Object> result = Result.success();
        List<AdditionalTableVo> list =  additionalTableService.list(table);
        Map<String, List<AdditionalTableVo>> collect = list.stream().collect(Collectors.groupingBy(AdditionalTableVo::getTableName));
        result.setData(collect);
        return result;
    }

    /**
     * 为表添加助手，即给助手分配权限
     * @param map 其中需包含tableName，counsellorId，以及assistantId数组
     * @return
     */
    @PostMapping("/addAssistantToTable")
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

    /**
     * 将某些学生添加到附加表中,其中需包括 sids:[]，tableName，uid（管理表的辅导员）
     * 实际上是将sid添加到mongo中
     * @param map
     * @return
     */
    @PostMapping("/addStudentsToTable")
    public Result<Object> addStudentToTable(@RequestBody Map<String, Object> map){
        if(!map.containsKey("sids")){
            return Result.fail("没有待添加的学生");
        }
        if(!map.containsKey("tableName")){
            return Result.fail("没有包含表名");
        }
        if(!map.containsKey("uid")){
            return Result.fail("没有包含表的管理者");
        }
        additionalTableService.addStudentToTable(map);
        return Result.success();
    }


    /**
     * 从基本表和附加表中取数据，暂时不支持分页查询
     * 其中没有判断是否存在该用户，存在该表，前端需返回正确
     * 如果当前用户为admin，注意uid需填写为表的管理者，即辅导员的id
     * @param uid 请求用户的id
     * @param tableName 附加表表名
     * @param queryMap 查询条件
     * @return
     */
    @PostMapping("/getStudentsFromTable/{uid}/{tableName}")
    public Result<Object> getStudentsFromTable(@PathVariable Integer uid,
                                               @PathVariable String tableName,
                                               @RequestBody Map<String, Object> queryMap){
        final Map<String, Object> maps = additionalTableService.queryStudents(uid, tableName, queryMap);
        Result<Object> result = Result.success();
        result.setData(maps);
        return result;
    }

    /**
     * 更改附加表中的内容
     * @param uid 用户id
     * @param tableName 表名
     * @param additionalInfo 其中，必须包含sid
     * @return
     */
    @PostMapping("/edit/{uid}/{tableName}")
    public Result<Object> editTable(@PathVariable Integer uid,
                                    @PathVariable String tableName,
                                    @RequestBody Map<String, Object> additionalInfo){
        additionalTableService.updateOne(uid, tableName,additionalInfo);
        return Result.success();
    }

    @DeleteMapping("/deleteStudentFromTable/{uid}/{tableName}/{sid}")
    public Result<Object> deleteStudentFromTable(@PathVariable Integer uid,
                                                 @PathVariable String tableName,
                                                 @PathVariable Long sid){
        final boolean b = additionalTableService.deleteOne(uid, tableName, sid);
        if(b)
            return Result.success();
        return Result.fail("删除失败");
    }

}

