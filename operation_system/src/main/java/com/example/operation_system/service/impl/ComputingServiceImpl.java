package com.example.operation_system.service.impl;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.service.ComputingService;
import com.example.operation_system.service.RelationService;
import com.example.operation_system.vo.RelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: operation_system
 * @description: 计算表达式Service，借助ComputingUtil完成结果的计算
 * @author: Xuan
 * @create: 2022-10-17 15:15
 **/

@Slf4j
@Service
public class ComputingServiceImpl implements ComputingService {

    @Resource
    private RelationService relationService;

    @Override
    public RelationVo compute(String expression) {
        RelationBo result = calculate(expression);
        if (result == null) {
            log.error("计算结果为空");
            return null;
        }
        return RelationBo.toRelationVo(result, "result");
    }

    private RelationBo calculate(String expression) {
        // TODO: 2022/10/18 计算expression表达式，返回结果Relation
        return null;
    }
}
