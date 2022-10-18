package com.example.operation_system.vo;

import lombok.Data;

/**
 * @program: operation_system
 * @description: 包装返回给前端的relation
 * @author: Xuan
 * @create: 2022-10-18 20:44
 **/
@Data
public class RelationVo {

    private String name;

    private int rowLen;

    private int colLen;

    private String colName;

    private String content;
}
