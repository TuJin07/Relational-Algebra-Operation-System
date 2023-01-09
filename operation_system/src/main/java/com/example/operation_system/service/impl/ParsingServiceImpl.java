package com.example.operation_system.service.impl;

import com.example.operation_system.constant.Constant;
import com.example.operation_system.service.ParsingService;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @program: operation_system
 * @description:
 * @author: Xuan
 * @create: 2023-01-09 20:15
 **/
public class ParsingServiceImpl implements ParsingService {

    @Override
    public boolean parsing(List<String> expression, String startSign) {
        Deque<String> stack = new ArrayDeque<>();
        stack.push(Constant.END_SIGN);
        stack.push(startSign);
        boolean flag = true;
        int i = 0;
        while (flag) {
            String top = stack.poll();
            String cur = expression.get(i);
            if (Constant.TERMINATOR.contains(top)) {
                if (cur.equals(top)) {
                    i++;
                } else {
                    return false;
                }
            } else if ("#".equals(top)) {
                if (top.equals(cur)) {
                    flag = false;
                } else {
                    return false;
                }
            } else if (Constant.NON_TERMINATOR.contains(top)) {
                boolean swapRes = swap(top, cur, stack);
                if (!swapRes) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean swap(String top, String cur, Deque<String> stack) {
        String derivative = Constant.PREDICTIVE_ANALYSIS_TABLE.get(top).get(cur);
        if (derivative.equals("")) {
            return true;
        }
        if (derivative.equals("ERROR")) {
            return false;
        }
        char[] charArray = derivative.toCharArray();
        for (int i = charArray.length - 1; i >= 0; i--) {
            stack.push(String.valueOf(charArray[i]));
        }
        return true;
    }
}
