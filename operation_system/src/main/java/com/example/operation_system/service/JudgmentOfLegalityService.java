package com.example.operation_system.service;

/**
 * @program: operation_system
 * @description: 表达式合法性判断
 * @author: Xuan
 * @create: 2023-01-09 14:05
 **/
public interface JudgmentOfLegalityService {
    /**
     * @param expression 待计算的表达式
     * @return 表达式是否合法
     */
    boolean judgeLegality(String expression);
}
