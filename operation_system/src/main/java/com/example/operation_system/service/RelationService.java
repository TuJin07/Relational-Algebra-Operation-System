package com.example.operation_system.service;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.exception.ParamLenException;
import com.example.operation_system.vo.RelationVo;
import org.springframework.stereotype.Service;

/**
 * @program: operation_system
 * @description: 管理（新建/删除）Relation接口
 * @author: Xuan
 * @create: 2022-10-18 20:39
 **/
public interface RelationService {

    /**
     * @param relationVo 前端传来的Relations
     * @throws ParamLenException 参数不对时抛出此异常
     */
    void insertRelation(RelationVo relationVo) throws ParamLenException;

    void insertRelation(RelationBo relationBo, String name) throws ParamLenException;

    /**
     * 删除关系
     */
    void deleteRelation(String name);

    /**
     * 删除所有已建立关系
     */
    void deleteAll();

    /**
     * 是否存在名为key的关系
     * @param key
     * @return
     */
    boolean contains(String key);

    /**
     * 获取name对应的Bo
     * @param name
     * @return
     */
    RelationBo get(String name);
}
