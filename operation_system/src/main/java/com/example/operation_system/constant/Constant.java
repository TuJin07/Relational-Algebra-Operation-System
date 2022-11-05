package com.example.operation_system.constant;

import java.util.HashMap;
import java.util.Map;

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

    public static final Map<String, Integer> PRIORITY;

    static {
        PRIORITY = new HashMap<>();
        PRIORITY.put("(", 0);
        PRIORITY.put(SELECT, 1);
        PRIORITY.put(PROJECT, 1);
        PRIORITY.put(JOIN, 1);
        PRIORITY.put(PROD, 2);
        PRIORITY.put(DIV, 2);
        PRIORITY.put(AND, 3);
        PRIORITY.put(OR, 4);
    }




}
