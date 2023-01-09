package com.example.operation_system.service;

import java.util.List;

/**
 * @program: operation_system
 * @description:
 * @author: Xuan
 * @create: 2023-01-09 14:52
 **/
public interface LexicalAnalysisService {
    boolean analysis(String expression);

    List<String> getResult();
}
