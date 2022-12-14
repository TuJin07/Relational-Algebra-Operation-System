package com.example.operation_system.service.impl;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.constant.Constant;
import com.example.operation_system.exception.*;
import com.example.operation_system.service.ComputingService;
import com.example.operation_system.service.RelationService;
import com.example.operation_system.util.ComputingUtil;
import com.example.operation_system.vo.RelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    private int relationCount = 0;

    @Override
    public RelationVo compute(String expression) throws ComputingException, WrongColumnNameException, IllegalOperationException, RelationNotExistsException {
        relationCount = 0;
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
    private RelationBo calculate(String expression) throws ComputingException, WrongColumnNameException, IllegalOperationException, RelationNotExistsException {
        List<String> post = null;
        try {
            post = parse(expression);
        } catch (ParamLenException e) {
            log.error("临时关系生成错误", e);
            throw new ComputingException();
        }
        Deque<RelationBo> stack = new ArrayDeque<>();
        for (String elem : post) {
           if (relationService.contains(elem)) {
               stack.push(relationService.get(elem));
               continue;
           }
           if (elem.charAt(0) != '#') {
               throw new RelationNotExistsException();
           }
           RelationBo bo1 = stack.poll(), bo2 = stack.poll();
           if (bo1 == null || bo2 == null) {
               throw new ComputingException();
           }
           RelationBo res = null;
           switch (elem) {
               case Constant.JOIN:
                   res = ComputingUtil.join(bo1, bo2);
                   break;
               case Constant.DIV:
                   res = ComputingUtil.div(bo2, bo1);
                   break;
               case Constant.AND:
                   res = ComputingUtil.and(bo1, bo2);
                   break;
               case Constant.OR:
                   res = ComputingUtil.or(bo1, bo2);
                   break;
               case Constant.PROD:
                   res = ComputingUtil.prod(bo2, bo1, relationCount);
                   relationCount += 2;
                   break;
               case Constant.DIFF:
                   res = ComputingUtil.diff(bo2, bo1);
                   break;
               default:
                   throw new ComputingException();
           }
           if (res == null) {
               res = RelationBo.EMPTY_RELATION;
           }
           stack.push(res);
        }
        return stack.peek();
    }

    /**
     * 中缀转后缀，并处理单目运算符
     * @param expression
     * @return
     */
    private List<String> parse(String expression) throws ComputingException, ParamLenException, WrongColumnNameException, IllegalOperationException, RelationNotExistsException {
//        String[] elems = expression.split(" ");
        String[] elems = splitExpression(expression);
        List<String> res = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        // 临时表计数
        int tempCount = 0;
        for (String elem : elems) {
            // elem为已定义的关系
            if (relationService.contains(elem)) {
                res.add(elem);
                continue;
            }
            // 处理单目运算符
            if (elem.charAt(0) == '#' && elem.length() > 6) {
                tempCount = preprocessingUnaryOperator(res, tempCount, elem);
                continue;
            }
            // elem为括号或是栈空且elem为多目运算符时，直接入栈
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

    /**
     * 获取单目运算符的参数
     * @param elem
     * @return
     */
    private String[] getParam(String elem) {
        int startIndex = 0, endIndex = 0;
        for (int i = 0; i < elem.length(); i++) {
            if (elem.charAt(i) == '[') {
                startIndex = i + 1;
                break;
            }
        }
        String[] temp = elem.substring(startIndex, elem.length() - 1).split(",");
        int paramLen = Integer.parseInt(temp[temp.length - 1]);
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int j = 0, k = 0;
        for (j = 0; j < temp.length - 1 - paramLen; j++) {
            sb.append(temp[j]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        result.add(sb.toString());
        for (k = j; k < temp.length - 1; k++) {
            result.add(temp[k]);
        }
        String[] res = new String[result.size()];
        result.toArray(res);
        return res;
    }

    private int preprocessingUnaryOperator(List<String> res, int tempCount, String elem) throws ComputingException, ParamLenException, WrongColumnNameException, IllegalOperationException, RelationNotExistsException {

        // 第0个参数为关系（或是嵌套表达式），其余参数为运算的参数
        String[] params = getParam(elem);
        if (params.length <= 1) {
            log.error("getParam出错");
            throw new ComputingException();
        }
        RelationBo tmp = null;
        // params[0]本身已经是已定义的关系则直接取用，否则递归计算表达式
        if (relationService.contains(params[0])) {
            tmp = relationService.get(params[0]);
        } else {
            tmp = calculate(params[0]);
        }
        String tempRelationName = Constant.TEMP_RELATION_PREFIX + tempCount;
        if (Constant.SELECT.equals(elem.substring(0,7))) {     // 选择运算处理
            RelationBo selectResult = ComputingUtil.select(tmp, params[1]);
            relationService.insertRelation(selectResult, tempRelationName);
            res.add(tempRelationName);
            tempCount++;
        }
        if (Constant.PROJECT.equals(elem.substring(0,8))) {    // 投影运算处理
            String[] projectColName = new String[params.length - 1];
            System.arraycopy(params, 1, projectColName, 0, params.length - 1);
            int[] projectColNo = new int[projectColName.length];
            for (int i = 0; i < projectColName.length; i++) {
                projectColNo[i] = tmp.getColIndexByName(projectColName[i]);
            }
            RelationBo projectResult = ComputingUtil.project(tmp, projectColNo);
            relationService.insertRelation(projectResult, tempRelationName);
            res.add(tempRelationName);
            tempCount++;
        }
        return tempCount;
    }

    private String[] splitExpression(String expression) {
        List<String> res = new ArrayList<>();
        int i = 0, j = 0, cnt = 0;
        while (j < expression.length()) {
            if (expression.charAt(j) == '[') {
                cnt++;
            }
            if (expression.charAt(j) == ']') {
                cnt--;
            }
            if (expression.charAt(j) == ' ' && cnt == 0) {
                res.add(expression.substring(i, j));
                i = j + 1;
            }
            j++;
        }
        res.add(expression.substring(i, j));
        return res.toArray(new String[0]);
    }

    public static void main(String[] args) {
        ComputingServiceImpl csi = new ComputingServiceImpl();
//        String str = "#project[#select[Student #join Course #join SC,Grade<70,1],Sname,1]";
//        String str = "#project[A #and B #join C,Sname,Sno,2]";
        String str = "#select[Student #join Course #join SC,Grade<70,1]";
        csi.getParam(str);
    }
}
