package com.example.operation_system.service;

import java.util.List;

/**
 * @program: operation_system
 * @description:
 * @author: Xuan
 * @create: 2023-01-09 14:52
 **/
public interface ParsingService {

    boolean parsing(List<String> expression, String startSign);
}
