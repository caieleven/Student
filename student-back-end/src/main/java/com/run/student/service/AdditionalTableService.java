package com.run.student.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.run.student.entity.AdditionalTable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.run.student.vo.AdditionalTableVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-26
 */
public interface AdditionalTableService extends IService<AdditionalTable> {
    //查询表
    List<AdditionalTableVo> list(AdditionalTable additionalTable);
    //添加助手
    boolean addAsistantToTable(Map<String, Object> map);
    boolean save(Map<String, Object> map);

    List<String> getTableNameByUid(Integer uid, String groupName);
}
