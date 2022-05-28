package com.run.student.controller;


import com.run.student.entity.AdditionalTable;
import com.run.student.service.AdditionalTableService;
import com.run.student.utils.AdditionalTableVo;
import com.run.student.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-26
 */
@Controller
@RequestMapping("/additionalTable")
public class AdditionalTableController {
    @Autowired
    AdditionalTableService additionalTableService;

    @RequestMapping("/addNewTable")
    public Result<Object> addNewTable(@RequestBody AdditionalTable table){
//     添加新表，表中存储的为表和辅导员/助手的关系，添加新表也是增添关系
        boolean save = additionalTableService.save(table);
        if(save){
            return Result.success();
        }
        return Result.fail("添加失败");
    }

    /**
     * @param table 其中，需包括aid，counsellorId，assistantId
     * @return
     */
    @RequestMapping("/addToTable")
    public Result<Object> addAssistantToTable(@RequestBody AdditionalTable table){
        boolean update = additionalTableService.saveOrUpdate(table);
        if(update){
            return Result.success();
        }
        return Result.fail("添加失败");

    }

    @GetMapping("/getTable")
    public Result<Object> query(@RequestParam Integer uid,
                                           @RequestParam String tableName){
        AdditionalTable table = new AdditionalTable();
        table.setCounsellorId(uid);
        table.setTableName(tableName);
        Result<Object> result = Result.success();
        result.setData(additionalTableService.list(table));
        return result;
    }


}

