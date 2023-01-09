package com.example.operation_system.constant;

import java.util.*;

/**
 * @program: operation_system
 * @description: 各种常量
 * @author: Xuan
 * @create: 2022-11-02 16:33
 **/
public class Constant {

    public static final String END_SIGN = "#";

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

    public static final Map<String, Map<String, String>> PREDICTIVE_ANALYSIS_TABLE;

    public static final Set<String> TERMINATOR;

    public static final Set<String> NON_TERMINATOR;

    static {
        PRIORITY = new HashMap<>();
        PRIORITY.put("(", 0);
        PRIORITY.put(SELECT, 1);
        PRIORITY.put(PROJECT, 1);
        PRIORITY.put(JOIN, 2);
        PRIORITY.put(DIFF, 3);
        PRIORITY.put(PROD, 4);
        PRIORITY.put(DIV, 4);
        PRIORITY.put(AND, 5);
        PRIORITY.put(OR, 6);

        UNARY_OPERATOR = new HashSet<>();
        UNARY_OPERATOR.add(PROJECT);
        UNARY_OPERATOR.add(SELECT);

        PREDICTIVE_ANALYSIS_TABLE = new HashMap<>();

        TERMINATOR = new HashSet<>();
        String[] temp = {
                "∩","∪","−","×","⋈","÷","π","σ","∧","∨","(",")","[","]","≤","≥","≠",">","<","=",",","id","num"
        };
        TERMINATOR.addAll(Arrays.asList(temp));

        NON_TERMINATOR = new HashSet<>();

        Map<String, String> cur;
        // todo @manqi

        cur = new HashMap<>();
        // 将第一行数据记录，左边是最上面一行（列名），右边是值
        cur.put("id", "CB");
        cur.put("num", "ERROR");
        cur.put("∪", "ERROR");
        cur.put("∩", "ERROR");
        // ....
        // 完成后，按如下格式添加到“A”中（A是第一行的的行名），下次添加就是添加到“B”中，以此类推
        PREDICTIVE_ANALYSIS_TABLE.put("A", cur);

        // 开始录第二行，每次按如下格式初始化一下cur
        cur = new HashMap<>();
        cur.put("id", "ERROR");
        cur.put("num", "ERROR");
//        ....
        PREDICTIVE_ANALYSIS_TABLE.put("B", cur);

//        ...
    }

}
