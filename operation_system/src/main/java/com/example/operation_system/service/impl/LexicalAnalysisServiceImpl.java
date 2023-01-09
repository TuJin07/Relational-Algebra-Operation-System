package com.example.operation_system.service.impl;

import com.example.operation_system.service.LexicalAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @program: operation_system
 * @description:
 * @author: Xuan
 * @create: 2023-01-09 14:53
 **/
@Slf4j
@Service
public class LexicalAnalysisServiceImpl implements LexicalAnalysisService {

    private char[] code;

    private boolean isIllegal;

    private List<String> result;

    @Override
    public boolean analysis(String expression) {
        log.info("开始对表达式进行词法分析...");
        if (isEmpty(expression)) {
            log.info("表达式为空，结束词法分析");
            return false;
        }
        isIllegal = true;
        result = new ArrayList<>();
        code = (expression + "#").toCharArray();
        startState(0, 0);
        log.info("词法分析完成，结果为：" + isIllegal);
        return isIllegal;
    }

    private boolean isEmpty(String expression) {
        return expression == null || expression.equals("");
    }

    private void startState(int currentPointer, int basicPointer) {
        if (code[currentPointer] == '#') {
            return;
        } else if (code[currentPointer] == '∩') {
            andOperatorState(currentPointer);
        } else if (code[currentPointer] == '∪') {
            orOperatorState(currentPointer);
        } else if (code[currentPointer] == '−') {
            diffOperatorState(currentPointer);
        } else if (code[currentPointer] == '×') {
            prodOperatorState(currentPointer);
        } else if (code[currentPointer] == '⋈') {
            joinOperatorState(currentPointer);
        } else if (code[currentPointer] == '÷') {
            divOperatorState(currentPointer);
        } else if (code[currentPointer] == 'π') {
            projectOperatorState(currentPointer);
        } else if (code[currentPointer] == 'σ') {
            selectOperatorState(currentPointer);
        } else if (code[currentPointer] == '∧') {
            conditionalAndState(currentPointer);
        } else if (code[currentPointer] == '∨') {
            conditionalOrState(currentPointer);
        } else if (code[currentPointer] == '(') {
            lParentState(currentPointer);
        } else if (code[currentPointer] == ')') {
            rParentState(currentPointer);
        } else if (code[currentPointer] == '[') {
            lBracket(currentPointer);
        } else if (code[currentPointer] == ']') {
            rBracket(currentPointer);
        } else if (code[currentPointer] == '.') {
            pointState(basicPointer, currentPointer);
        } else if (code[currentPointer] == '≤') {
            conditionalLessOrEqualSign(currentPointer);
        } else if (code[currentPointer] == '≥') {
            conditionalGreaterOrEqualSign(currentPointer);
        } else if (code[currentPointer] == '≠') {
            conditionalInequalityState(currentPointer);
        } else if (code[currentPointer] == '>') {
            conditionalGreaterThanSign(currentPointer);
        } else if (code[currentPointer] == '<') {
            conditionalLessThanSign(currentPointer);
        } else if (code[currentPointer] == '=') {
            conditionalEqualState(currentPointer);
        } else if (code[currentPointer] == ',') {
            comma(currentPointer);
        } else if (isAlpha(currentPointer)) {
            identState(basicPointer, currentPointer + 1);
        } else if (isDigit(currentPointer)) {
            intState(basicPointer, currentPointer + 1);
        } else {
            isIllegal = false;
            String cur = new String(code, basicPointer, currentPointer - basicPointer);
            log.info("<错误, " + cur + ">");
            result.add(cur);
            startState(basicPointer + 1, currentPointer + 1);
        }
    }

    private void orOperatorState(int i) {
        log.info("<或运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void andOperatorState(int i) {
        log.info("<与运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void diffOperatorState(int i) {
        log.info("<差运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void prodOperatorState(int i) {
        log.info("<笛卡尔积运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void divOperatorState(int i) {
        log.info("<除运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void joinOperatorState(int i) {
        log.info("<连接运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void projectOperatorState(int i) {
        log.info("<投影运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void selectOperatorState(int i) {
        log.info("<选择运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void comma(int i) {
        log.info("<逗号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void conditionalAndState(int i) {
        log.info("<条件与运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void conditionalOrState(int i) {
        log.info("<条件或运算符, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void conditionalEqualState(int i) {
        log.info("<等号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void conditionalInequalityState(int i) {
        log.info("<不等号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void conditionalGreaterThanSign(int i) {
        log.info("<大于号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void conditionalLessThanSign(int i) {
        log.info("<小于号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void conditionalGreaterOrEqualSign(int i) {
        log.info("<大于等于号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);    }

    private void conditionalLessOrEqualSign(int i) {
        log.info("<小于等于号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void lParentState(int i) {
        log.info("<左括号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void rParentState(int i) {
        log.info("<右括号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void lBracket(int i) {
        log.info("<左方括号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void rBracket(int i) {
        log.info("<右方括号, " + code[i] + ">");
        String tmp = String.valueOf(code[i]);
        result.add(tmp);
        startState(i + 1, i + 1);
    }

    private void identState(int basicPointer, int currentPointer) {
        if (isDigit(currentPointer) || isAlpha(currentPointer)) {
            identState(basicPointer, currentPointer + 1);
        } else {
            String curIdent = new String(code, basicPointer, currentPointer - basicPointer);
            log.info("<标识符, " + curIdent + ">");
            result.add("id");
            startState(currentPointer, currentPointer);
        }
    }

    private void intState(int basicPointer, int currentPointer) {
        if (isDigit(currentPointer)) {
            intState(basicPointer, currentPointer + 1);
        } else if (code[currentPointer] == '.') {
            pointState(basicPointer, currentPointer + 1);
        } else {
            String curInt = new String(code, basicPointer, currentPointer - basicPointer);
            log.info("<实数, " + curInt + ">");
            result.add("num");
            startState(currentPointer, currentPointer);
        }
    }

    private void pointState(int basicPointer, int currentPointer) {
        if (!isDigit(currentPointer)) {
            isIllegal = false;
            String curInt = new String(code, basicPointer, currentPointer - basicPointer);
            log.info("<错误, " + curInt + ">");
            result.add(curInt);
            startState(currentPointer, currentPointer);
        } else {
            floatState(basicPointer, currentPointer + 1);
        }
    }

    private void floatState(int basicPointer, int currentPointer) {
        if (isDigit(currentPointer)) {
            floatState(basicPointer, currentPointer + 1);
        } else {
            String curFloat = new String(code, basicPointer, currentPointer - basicPointer);
            log.info("<浮点数, " + curFloat + ">");
            result.add("num");
            startState(currentPointer, currentPointer);
        }
    }

    private boolean isAlpha(int index) {
        return Character.isUpperCase(code[index]) || Character.isLowerCase(code[index]);
    }

    private boolean isDigit(int index) {
        return Character.isDigit(code[index]);
    }

    public List<String> getResult() {
        return result;
    }

    public static void main(String[] args) {
        LexicalAnalysisService service = new LexicalAnalysisServiceImpl();
        String expression = "A∩Student∪B−(C×Course)⋈σ[A=10.50][Go]×π[Sno,Cno12][B]×σ[A=10AB∧B=20∨C=39][A∩B]";
//        String expression = "A";
        service.analysis(expression);
        System.out.println(service.getResult());
    }
}
