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
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AdditionalTable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID
     */
      @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    /**
     * 附加表表名
     */
    private String tableName;

    /**
     * 辅导员ID
     */
    private Integer counsellorId;

    /**
     * 助手ID
     */
    private Integer assistantId;


}
