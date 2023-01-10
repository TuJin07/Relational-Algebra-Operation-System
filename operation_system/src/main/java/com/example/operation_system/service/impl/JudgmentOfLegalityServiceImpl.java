package com.example.operation_system.service.impl;

import com.example.operation_system.exception.LexicalAnalysisException;
import com.example.operation_system.exception.ParsingException;
import com.example.operation_system.service.JudgmentOfLegalityService;
import com.example.operation_system.service.LexicalAnalysisService;
import com.example.operation_system.service.ParsingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public void judgeLegality(String expression) throws LexicalAnalysisException, ParsingException {
        boolean res = lexicalAnalysis(expression);
        if (!res) {
            throw new LexicalAnalysisException();
        }
        List<String> temp = lexicalAnalysisService.getResult();
        res = parsing(temp);
        if (!res) {
            throw new ParsingException();
        }
    }

    private boolean lexicalAnalysis(String expression) {
        return lexicalAnalysisService.analysis(expression);
    }

    private boolean parsing(List<String> expression) {
        return parsingService.parsing(expression, "A");
    }
}
