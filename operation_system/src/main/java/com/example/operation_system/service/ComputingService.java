package com.example.operation_system.service;

import com.example.operation_system.Bo.RelationBo;

/**
 * @program: operation_system
 * @description: 计算类接口
 * @author: Xuan
 * @create: 2022-10-17 15:12
 **/
public interface ComputingService {

    RelationBo compute(int rowLen, int colLen, String content);

}
