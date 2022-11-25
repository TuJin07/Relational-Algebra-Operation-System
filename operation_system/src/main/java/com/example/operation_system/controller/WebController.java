package com.example.operation_system.controller;

import com.example.operation_system.exception.ComputingException;
import com.example.operation_system.exception.ParamLenException;
import com.example.operation_system.response.Result;
import com.example.operation_system.service.ComputingService;
import com.example.operation_system.service.RelationService;
import com.example.operation_system.vo.RelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: operation_system
 * @description: 与前端的http接口
 * @author: Xuan
 * @create: 2022-10-17 15:16
 **/

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
public class WebController {

    @Resource
    private ComputingService computingService;

    @Resource
    private RelationService relationService;

    /**
     * 计算表达式结果并返回
     * @return
     */
    @RequestMapping(value = "/api/compute/", method = RequestMethod.POST)
    public Result getCalculationResult(@RequestBody Map<String, String> input) {
        RelationVo result = null;
        try {
            result = computingService.compute(input.get("expression"));
        } catch (ComputingException e) {
            log.error("[计算]计算异常", e);
            return Result.fail("计算异常");
        }
        return Result.success(result);
    }


    /**
     * 新建关系
     * @return
     */
    @RequestMapping(value = "/api/insert/", method = RequestMethod.POST)
    public Result insertRelation(@RequestBody RelationVo vo) {
        if (vo.getName() == null) {
            return Result.fail("关系名为空");
        }
        if (vo.getContent() == null) {
            return Result.fail("关系内容为空");
        }
        if (vo.getColLen() == 0 || vo.getRowLen() == 0) {
            return Result.fail("列长或行长为空");
        }
        if (vo.getColName() == null || vo.getColName().split(",").length != vo.getColLen()) {
            return Result.fail("列名有误");
        }
        try {
            relationService.insertRelation(vo);
        } catch (ParamLenException e) {
            log.error("[新增关系]参数异常", e);
            return Result.fail("参数异常");
        }
        return Result.success();
    }

    /**
     * 删除关系
     * @return
     */
    @RequestMapping(value = "/api/delete/", method = RequestMethod.POST)
    public Result deleteRelation(@RequestBody String name) {
        relationService.deleteRelation(name);
        return Result.success();
    }

    /**
     * 删除所有关系
     * @return
     */
    @RequestMapping(value = "/api/delete_all/", method = RequestMethod.GET)
    public Result deleteAll() {
        relationService.deleteAll();
        return Result.success();
    }

    @RequestMapping(value = "/api/is_alive/", method = RequestMethod.GET)
    public Result isAlive() {
        return Result.success();
    }

    @RequestMapping(value = "/api/test/", method = RequestMethod.POST)
    public Result test() {
        RelationVo vo = new RelationVo();
        vo.setColLen(2);
        vo.setRowLen(2);
        vo.setColName("A, B");
        vo.setName("result");
        vo.setContent("a,b,c,d");
        return Result.success(vo);
    }

}
