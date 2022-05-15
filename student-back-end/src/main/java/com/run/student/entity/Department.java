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
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学院id
     */
      @TableId(value = "did", type = IdType.AUTO)
    private Integer did;

    /**
     * 学院名字
     */
    private String name;


}
