package com.run.student.controller;


import com.run.student.entity.Notice;
import com.run.student.service.NoticeService;
import com.run.student.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告 前端控制器
 * </p>
 *
 * @author baorun chen
 * @since 2022-06-10
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    /**
     * 发布新公告
     * @param notice
     * @return
     */
    @PostMapping("/addNew")
    public Result<Object> postNewNotice(@RequestBody Notice notice){
        LocalDate time = LocalDate.now();
        notice.setTime(time);
        noticeService.save(notice);
        return Result.success();
    }

    /**
     * 返回当前用户的公告，后期可以添加requestBody来进行查询
     * @param uid
     * @return map格式返回
     */
    @PostMapping("/getNotice/{uid}")
    public Result<Object> getNotice(@PathVariable Integer uid,
                                    @RequestBody Map<String, Object> queryMap){
        final List<Map<String, Object>> maps = noticeService.queryNotice(uid, queryMap);
        System.out.println(maps.toString());
        Result<Object> success = Result.success();
        success.setData(maps);
        System.out.println(success.toString());
        return success;
    }

    @DeleteMapping("/delete/{nid}")
    public Result<Object> deleteNotice(@PathVariable Integer nid){
        noticeService.removeById(nid);
        return Result.success();
    }

}

