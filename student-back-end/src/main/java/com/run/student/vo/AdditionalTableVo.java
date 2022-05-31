package com.run.student.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalTableVo {
    private Integer aid;
    private String tableName;
    private Integer counsellorId;
    private String counsellorName;
    private String assistantName;
}
