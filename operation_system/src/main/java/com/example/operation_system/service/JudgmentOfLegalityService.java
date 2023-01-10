package com.example.operation_system.service;

import com.example.operation_system.exception.LexicalAnalysisException;
import com.example.operation_system.exception.ParsingException;

/**
 * @program: operation_system
 * @description: 表达式合法性判断
 * @author: Xuan
 * @create: 2023-01-09 14:05
 **/
public interface JudgmentOfLegalityService {
    /**
     * @param expression 待计算的表达式
     */
    void judgeLegality(String expression) throws LexicalAnalysisException, ParsingException;
}
