package com.example.operation_system.util;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.exception.ParamLenException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ComputingUtilTest {

    private static RelationBo bo1;

    private static RelationBo bo2;

    // bo1
    private static int bo1RowLen = 2;

    private static int bo1ColLen = 2;

    private static String[] bo1ColName = {"A", "B"};

    private static String[][] bo1Content = {
            {"a1", "b1"}, {"a2", "b2"}
    };

    // bo2
    private static int bo2RowLen = 2;

    private static int bo2ColLen = 3;

    private static String[] bo2ColName = {"C", "D", "E"};

    private static String[][] bo2Content = {
            {"c1", "d1", "e1"}, {"c2", "d2", "e2"}
    };

    static {
        try {
            bo1 = new RelationBo(bo1RowLen, bo1ColLen, bo1ColName, bo1Content);
            bo2 = new RelationBo(bo2RowLen, bo2ColLen, bo2ColName, bo2Content);
        } catch (ParamLenException e) {
            log.error("参数长度错误", e);
        }
    }

    @Test
    void and() {

    }

    @Test
    void or() {
    }

    @Test
    void diff() {
    }

    @Test
    void div() {
    }

    @Test
    void prod() {
    }

    @Test
    void select() {
    }

    @Test
    void project() {
    }

    @Test
    void join() {
    }
}
