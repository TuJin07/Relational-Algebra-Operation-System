package com.example.operation_system.service.impl;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.exception.ComputingException;
import com.example.operation_system.exception.ParamLenException;
import com.example.operation_system.service.ComputingService;
import com.example.operation_system.service.RelationService;
import com.example.operation_system.vo.RelationVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ComputingServiceImplTest {

    @Resource
    private static ComputingService computingService;

    @Resource
    private static RelationService relationService;

    private static RelationBo bo1;

    private static RelationBo bo2;

    // bo1 初始化
    private static int bo1RowLen = 2;

    private static int bo1ColLen = 2;

    private static String[] bo1ColName = {"A", "B"};

    private static String[][] bo1Content = {
            {"a1", "b1"}, {"a2", "b2"}
    };

    // bo2 初始化
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
        List<RelationVo> list = new ArrayList<>();
        list.add(RelationBo.toRelationVo(bo1, "A"));
        list.add(RelationBo.toRelationVo(bo2, "B"));
        RelationVo[] vos = new RelationVo[list.size()];
        list.toArray(vos);
        try {
            relationService.insertRelation(RelationBo.toRelationVo(bo1, "A"));
        } catch (ParamLenException e) {
            log.error("参数长度错误", e);
        }

    }

    private static final String expression = "A #and B";

    @Test
    void compute() {
        RelationVo res = new RelationVo();
        try {
            res = computingService.compute(expression);
        } catch (ComputingException e) {
            log.error("计算错误", e);
        }
        System.out.println(res);
    }
}
