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

    public static RelationVo toRelationVo(RelationBo bo, String name) {
        RelationVo res = new RelationVo();
        res.setName(name);
        res.setColLen(bo.getColLen());
        res.setRowLen(bo.getRowLen());
        String content = null;
        for (int i = 0; i < bo.getRowLen(); i++) {
            for (int j = 0; j < bo.getColLen(); j++) {
                // todo
            }
        }
        String colName = null;
        for (int i = 0; i < bo.getColName().length; i++) {
            // todo
        }
        res.setContent(content);
        res.setColName(colName);
        return res;
    }
}
