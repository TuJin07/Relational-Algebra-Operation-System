package com.example.operation_system.vo;

import com.example.operation_system.bo.RelationBo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @program: operation_system
 * @description: 包装返回给前端的relation
 * @author: Xuan
 * @create: 2022-10-18 20:44
 **/
@Data
@ToString
public class RelationVo {

    @JsonProperty("relation_name")
    private String name;

    @JsonProperty("row_len")
    private int rowLen;

    @JsonProperty("col_len")
    private int colLen;

    @JsonProperty("col_name")
    private String colName;

    @JsonProperty("content")
    private String content;
}
