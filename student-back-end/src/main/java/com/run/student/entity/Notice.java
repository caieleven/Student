package com.run.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公告
 * </p>
 *
 * @author baorun chen
 * @since 2022-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公告主键id
     */
      @TableId(value = "nid", type = IdType.AUTO)
    private Integer nid;

    /**
     * 标题
     */
    private String title;

    /**
     * 发布人
     */
    private Integer userId;

    /**
     * 发布时间
     */
    private LocalDate time;

    /**
     * 内容
     */
    private String content;


}
