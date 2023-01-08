package com.example.operation_system.util;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.exception.ParamLenException;
import com.example.operation_system.exception.WrongColumnNameException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ComputingUtilTest {

    private static RelationBo bo1;

    private static RelationBo bo2;

    private static RelationBo bo3;

    private static RelationBo bo4;

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

    //bo3
    private static int bo3RowLen = 1;

    private static int bo3ColLen = 1;

    private static String[] bo3ColName = {"B"};

    private static String[][] bo3Content = {
            {"3"}
    };

    //bo4
    private static int bo4RowLen = 1;

    private static int bo4ColLen = 2;

    private static String[] bo4ColName = {"A","B"};

    private static String[][] bo4Content = {
            {"1","2"}
    };

    static {
        try {
            bo1 = new RelationBo(bo1RowLen, bo1ColLen, bo1ColName, bo1Content);
            bo2 = new RelationBo(bo2RowLen, bo2ColLen, bo2ColName, bo2Content);
            bo3 = new RelationBo(bo3RowLen, bo3ColLen, bo3ColName, bo3Content);
            bo4 = new RelationBo(bo4RowLen, bo4ColLen, bo4ColName, bo4Content);
        } catch (ParamLenException e) {
            log.error("参数长度错误", e);
        }
    }


    @Test
    void and() {
        RelationBo res = ComputingUtil.and(bo3, bo4);
        System.out.println(res);
    }

    @Test
    void or() {
        RelationBo res = ComputingUtil.or(bo3, bo4);
        System.out.println(res);
    }

    @Test
    void diff() {
        RelationBo res = ComputingUtil.diff(bo3, bo4);
        System.out.println(res);
    }

    @Test
    void div() {
        RelationBo res = ComputingUtil.div(bo4, bo3);
        System.out.println(res);
    }

    @Test
    void prod() {
        RelationBo res = ComputingUtil.prod(bo3, bo4,0);
        System.out.println(res);
    }

    @Test
    void select() {
        RelationBo res = ComputingUtil.select(bo3,"A=a");
        System.out.println(res);
    }

    @Test
    void project() throws WrongColumnNameException {
        int[] cols = new int[]{0,1};
        RelationBo res = ComputingUtil.project(bo4,cols);
        System.out.println(res);
    }

    @Test
    void join() {
        RelationBo res = ComputingUtil.join(ComputingUtil.prod(bo4,bo3,0),bo3);
        System.out.println(res);
    }
}
