package com.run.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.run.student.entity.Notice;
import com.run.student.entity.User;
import com.run.student.mapper.NoticeMapper;
import com.run.student.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.run.student.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告 服务实现类
 * </p>
 *
 * @author baorun chen
 * @since 2022-06-10
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    UserService userService;

    @Override
    public List<Map<String, Object>> queryNotice(Integer uid, Map<String, Object> queryMap) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        // 查找自己发布的和fid发布的
        final User user = userService.getById(uid);
        if(!user.getGroupId().equals(1)){
            List<Integer> uidList = new ArrayList<>();
            uidList.add(uid);
            uidList.add(user.getFid());
            wrapper.in("user_id", uidList);
        }
        if(ObjectUtils.isEmpty(queryMap))
            return baseMapper.queryNotice(wrapper);

        //        在继续搜寻关键词
        String keyword = (String) queryMap.get("keyword");
        wrapper.like("title", keyword).or().like("content", keyword);
        return baseMapper.queryNotice(wrapper);
    }
}
