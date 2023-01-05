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

    // bo1 抄的Course
    private static int bo1RowLen = 3;

    private static int bo1ColLen = 3;

    private static String[] bo1ColName = {"Cno","Cname","Ccredit"};

    private static String[][] bo1Content = {
            {"1", "shujuku","4"}, {"2", "caozuoxitong","3"},{"3", "shujujiegou","4"}
    };

    // bo2 抄的SC
    private static int bo2RowLen = 4;

    private static int bo2ColLen = 3;

    private static String[] bo2ColName = {"Sno","Cno","Grade"};

    private static String[][] bo2Content = {
            {"95001", "1","92"}, {"95001", "2","65"},{"95002","2","90"},{"95002", "3","73"}
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
        RelationBo res = ComputingUtil.and(bo1, bo2);
        System.out.println(res);
    }

    @Test
    void or() {
        RelationBo res = ComputingUtil.or(bo1, bo2);
        System.out.println(res);
    }

    @Test
    void diff() {
        RelationBo res = ComputingUtil.diff(bo1, bo2);
        System.out.println(res);
    }

    @Test
    void div() {
        RelationBo res = ComputingUtil.div(bo1, bo2);
        System.out.println(res);
    }

    @Test
    void prod() {
        RelationBo res = ComputingUtil.prod(bo1, bo2);
        System.out.println(res);
    }

    @Test
    void select() {
        RelationBo res = ComputingUtil.select(bo1,"A>0");
        System.out.println(res);
    }

    @Test
    void project() {
        int[] cols = new int[2];
        cols[0] = 1;
        cols[1] = 2;
        RelationBo res = ComputingUtil.project(bo1,cols);
        System.out.println(res);
    }

    @Test
    void join() {
        RelationBo res = ComputingUtil.join(bo1,bo2);
        System.out.println(res);
    }
}
