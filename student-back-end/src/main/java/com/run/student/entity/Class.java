package com.run.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author baorun chen
 * @since 2022-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
      @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 专业ID
     */
    private Integer majorId;

    /**
     * 辅导员ID
     */
    private Integer counsellorId;


}
