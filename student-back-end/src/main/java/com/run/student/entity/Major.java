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
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 专业id
     */
      @TableId(value = "mid", type = IdType.AUTO)
    private Integer mid;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 学院ID
     */
    private Integer departmentId;


}
