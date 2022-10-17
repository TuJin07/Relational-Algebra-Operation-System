package com.example.operation_system.controller;

import com.example.operation_system.service.ComputingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: operation_system
 * @description: 与前端的http接口
 * @author: Xuan
 * @create: 2022-10-17 15:16
 **/

@RestController
public class WebController {

    @Resource
    private ComputingService computingService;

    @RequestMapping(value = "/api/compute/", method = RequestMethod.POST)
    public String getCalculationResult(
            @RequestParam("row_length") int rowLen,
            @RequestParam("col_length") int colLen,
            @RequestParam("expression") String expression) {


        return null;
    }

}
