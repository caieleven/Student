package com.run.student.service;

import com.run.student.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告 服务类
 * </p>
 *
 * @author baorun chen
 * @since 2022-06-10
 */
public interface NoticeService extends IService<Notice> {
    List<Map<String, Object>> queryNotice(Integer uid, Map<String, Object> queryMap);
}
