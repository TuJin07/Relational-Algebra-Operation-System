package com.example.operation_system.service.impl;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.constant.Constant;
import com.example.operation_system.exception.ComputingException;
import com.example.operation_system.service.ComputingService;
import com.example.operation_system.service.RelationService;
import com.example.operation_system.util.ComputingUtil;
import com.example.operation_system.vo.RelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @program: operation_system
 * @description: 计算表达式Service，借助ComputingUtil完成结果的计算
 * @author: Xuan
 * @create: 2022-10-17 15:15
 **/

@Slf4j
@Service
public class ComputingServiceImpl implements ComputingService {

    @Resource
    private RelationService relationService;

    @Override
    public RelationVo compute(String expression) throws ComputingException {
        RelationBo result = calculate(expression);
        if (result == null) {
            log.error("计算结果为空");
            return null;
        }
        return RelationBo.toRelationVo(result, "result");
    }

    /**
     * 计算后缀表达式
     * @param expression
     * @return
     */
    private RelationBo calculate(String expression) throws ComputingException {
        // todo 剩余四种运算完善
        List<String> post = parse(expression);
        Deque<RelationBo> stack = new ArrayDeque<>();
        for (String elem : post) {
           if (relationService.contains(elem)) {
               stack.push(relationService.get(elem));
               continue;
           }
           String operation = elem;
           RelationBo bo1 = stack.poll(), bo2 = stack.poll();
           RelationBo res = null;
           switch (operation) {
               case Constant.AND:
                   res = ComputingUtil.and(bo1, bo2);
                   break;
               case Constant.OR:
                   res = ComputingUtil.or(bo1, bo2);
                   break;
               case Constant.PROD:
                   res = ComputingUtil.prod(bo1, bo2);
                   break;
               case Constant.DIFF:
                   res = ComputingUtil.diff(bo1, bo2);
                   break;
               default:
                   throw new ComputingException();
           }
           stack.push(res);
        }
        return null;
    }

    /**
     * 中缀转后缀
     * @param expression
     * @return
     */
    private List<String> parse(String expression) {
        String[] elems = expression.split(" ");
        List<String> res = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        for (String elem : elems) {
            if (relationService.contains(elem)) {
                res.add(elem);
                continue;
            }
            if (elem.equals("(") || (elem.charAt(0) == '#') && stack.isEmpty()) {
                stack.push(elem);
                continue;
            }
            if (elem.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    res.add(stack.poll());
                }
                stack.poll();
                continue;
            }
            while (!stack.isEmpty()
                    && !stack.peek().equals("(")
                    && Constant.PRIORITY.get(elem) >= Constant.PRIORITY.get(stack.peek())) {
                res.add(stack.poll());
            }
            stack.push(elem);
        }
        while (!stack.isEmpty()) {
            res.add(stack.poll());
        }
        return res;
    }
}
