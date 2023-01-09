package com.example.operation_system.service.impl;

import com.example.operation_system.exception.IllegalInputException;
import com.example.operation_system.service.JudgmentOfLegalityService;
import com.example.operation_system.service.LexicalAnalysisService;
import com.example.operation_system.service.ParsingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: operation_system
 * @description:
 * @author: Xuan
 * @create: 2023-01-09 14:08
 **/
@Service
public class JudgmentOfLegalityServiceImpl implements JudgmentOfLegalityService {

    @Resource
    LexicalAnalysisService lexicalAnalysisService;

    @Resource
    ParsingService parsingService;

    @Override
    public boolean judgeLegality(String expression) {
        boolean res = lexicalAnalysis(expression);
        if (!res) {
            return false;
        }
        return true;
    }

    private boolean lexicalAnalysis(String expression) {
        return lexicalAnalysisService.analysis(expression);
    }
}
