package com.example.operation_system.service;

import com.example.operation_system.exception.ComputingException;
import com.example.operation_system.vo.RelationVo;
import org.springframework.stereotype.Service;


/**
 * @program: operation_system
 * @description: 计算表达式接口
 * @author: Xuan
 * @create: 2022-10-17 15:12
 **/
public interface ComputingService {

    /**
     * @param expression 关系代数表达式
     * @return 计算后的结果
     */
    RelationVo compute(String expression) throws ComputingException;

}
