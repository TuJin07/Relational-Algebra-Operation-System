package com.example.operation_system.constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @program: operation_system
 * @description: 各种常量
 * @author: Xuan
 * @create: 2022-11-02 16:33
 **/
public class Constant {

    public static final String OR = "#or";

    public static final String AND = "#and";

    public static final String DIFF = "#diff";

    public static final String PROD = "#prod";

    public static final String DIV = "#div";

    public static final String SELECT = "#select";

    public static final String PROJECT = "#project";

    public static final String JOIN = "#join";

    public static final Set<String> UNARY_OPERATOR;

    public static final Map<String, Integer> PRIORITY;

    public static final String TEMP_RELATION_PREFIX = "TEMP";

    static {
        PRIORITY = new HashMap<>();
        PRIORITY.put("(", 0);
        PRIORITY.put(SELECT, 1);
        PRIORITY.put(PROJECT, 1);
        PRIORITY.put(JOIN, 2);
        PRIORITY.put(PROD, 3);
        PRIORITY.put(DIV, 3);
        PRIORITY.put(AND, 4);
        PRIORITY.put(OR, 5);

        UNARY_OPERATOR = new HashSet<>();
        UNARY_OPERATOR.add(PROJECT);
        UNARY_OPERATOR.add(SELECT);
    }




}
