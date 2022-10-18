package com.example.operation_system.controller;

import com.example.operation_system.response.Result;
import com.example.operation_system.service.ComputingService;
import com.example.operation_system.service.RelationService;
import com.example.operation_system.vo.RelationVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: operation_system
 * @description: 与前端的http接口
 * @author: Xuan
 * @create: 2022-10-17 15:16
 **/

@SuppressWarnings("rawtypes")
@RestController
public class WebController {

    @Resource
    private ComputingService computingService;

    @Resource
    private RelationService relationService;

    /**
     * 计算表达式结果并返回
     * @return
     */
    @RequestMapping(value = "/api/compute/", method = RequestMethod.GET)
    public Result<RelationVo> getCalculationResult() {
        // todo
        return null;
    }


    /**
     * 新建关系
     * @return
     */
    @RequestMapping(value = "/api/insert/", method = RequestMethod.POST)
    public Result insertRelation() {
        // todo
        return null;
    }

}
