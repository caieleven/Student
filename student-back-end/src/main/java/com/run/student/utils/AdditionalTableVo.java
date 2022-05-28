package com.run.student.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalTableVo {
    private Integer aid;
    private String tableName;
    private String counsellorName;
    private String assistantName;

}
