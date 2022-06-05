package com.run.student.service;

import com.run.student.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
public interface ClassService extends IService<Class> {
    public Integer selectCidByCname(String className);
}
