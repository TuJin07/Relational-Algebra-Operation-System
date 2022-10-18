package com.example.operation_system.service;

import com.example.operation_system.exception.ParamLenException;
import com.example.operation_system.vo.RelationVo;

/**
 * @program: operation_system
 * @description: 管理（新建/删除）Relation接口
 * @author: Xuan
 * @create: 2022-10-18 20:39
 **/
public interface RelationService {

    /**
     * @param relationVos 前端传来的Relations
     * @throws ParamLenException 参数不对时抛出此异常
     */
    void insertRelation(RelationVo[] relationVos) throws ParamLenException;

    /**
     * 删除所有已建立的关系
     */
    void deleteRelation();
}
