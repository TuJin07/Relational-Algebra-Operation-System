package com.example.operation_system.constant;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @program: operation_system
 * @description: 各种常量
 * @author: Xuan
 * @create: 2022-11-02 16:33
 **/
@Slf4j
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
        String[] temp1 = {
                "∩","∪","−","×","⋈","÷","π","σ","∧","∨","(",")","[","]","≤","≥","≠",">","<","=",",","id","num"
        };
        TERMINATOR.addAll(Arrays.asList(temp1));

        NON_TERMINATOR = new HashSet<>();
        String[] temp2 = {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"
        };
        NON_TERMINATOR.addAll(Arrays.asList(temp2));

        String csvFile = "../doc/预测分析表.csv";
        CSVReader reader = null;
        String[] column = {"id", "num", "∪", "∩", "−", "×", "⋈", "÷", "σ", "π", "(", ")", "[", "]", "=", ">", "≥", "<", "≤", "≠", "∧", "∨", ",", "#"};
        String[] row = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};
        int i = 0;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                Map<String, String> cur = new HashMap<>();
                for (int j = 0; j < line.length; j++) {
                    if (j == 0 && i == 0) {
                        line[j] = line[j].substring(1, line[j].length());
                    }
                    if (line[j].equals("'")) {
                        line[j] = "";
                    }
                    cur.put(column[j], line[j]);
                }
                PREDICTIVE_ANALYSIS_TABLE.put(row[i], cur);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(Constant.PREDICTIVE_ANALYSIS_TABLE);
    }

}
