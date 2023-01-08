package com.example.operation_system.util;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.constant.Constant;
import com.example.operation_system.exception.ComputingException;
import com.example.operation_system.exception.IllegalOperationException;
import com.example.operation_system.exception.ParamLenException;
import com.example.operation_system.exception.WrongColumnNameException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @program: operation_system
 * @description: 计算工具类，负责关系代数运算
 * @author: Xuan
 * @create: 2022-10-18 21:23
 **/
@Slf4j
public class ComputingUtil {

    //1 -----------交-----------
    //选出两个关系内相同的元组
    public static RelationBo and(RelationBo r1, RelationBo r2) throws IllegalOperationException {
        // 两个表结构相同才有and的必要（列名集相同，但可不同序）：如果不同，直接抛异常
        if (!checkColName(r1, r2)) {
            throw new IllegalOperationException();
        }
        // todo --------------------------- @manqi 修改：满足列名顺序不同也可以比较，想不出来怎么做可以和我讨论下，可能用到哈希表HashMap
        //1.将r1列与r2列对应，temp[a]=b：r1的第a列对应r2的第b列
        int[] temp = new int[r1.getColLen()];
        for(int i=0;i<r1.getColLen();i++){
            for(int j=0;j<r2.getColLen();j++){
                if(Objects.equals(r1.getColName()[i],r2.getColName()[j])){
                    temp[i] = j;
                }
            }
        }
        //2.取r1一行与r2的每一行对比，如果重复则加入字符串str，用逗号分开
        int rowLen = 0;
        String str = "";
        for(int i=0;i<r1.getRowLen();i++){
            for(int j=0;j<r2.getRowLen();j++){
                if(isSameTwo(r1,r2,i,j,temp)){
                    str = addStr(r1,i,str);
                    rowLen++;
                }
            }
        }
        // todo --------------------------- 修改两条直线之间的code
        RelationBo r3 = null;
        try {
            r3 = new RelationBo(rowLen, r1.getColLen(), r1.getColName(), str);
        }catch (ParamLenException e){
            System.out.print("参数长度错误");
        }
        return r3;
    }
    //2 -----------并-----------
    //合并两个集合，去掉重复的
    public static RelationBo or(RelationBo r1, RelationBo r2) throws IllegalOperationException {
        // 两个表结构相同才有and的必要（列名集相同，但可不同序）：如果不同，直接抛异常
        if (!checkColName(r1, r2)) {
            throw new IllegalOperationException();
        }
        // todo --------------------------- @manqi 同上and，修改：满足列名顺序不同也可以比较，想不出来怎么做可以和我讨论下，可能用到哈希表HashMap
        //1.将r1列与r2列对应，temp[a]=b：r1的第a列对应r2的第b列
        int[] temp = new int[r1.getColLen()];
        for(int i=0;i<r1.getColLen();i++){
            for(int j=0;j<r2.getColLen();j++){
                if(Objects.equals(r1.getColName()[i],r2.getColName()[j])){
                    temp[i] = j;
                }
            }
        }
        //2.取r1一行与r2的每一行对比，如果无重复则加入字符串str，用逗号分开
        int rowLen = 0;
        String str = "";
        for(int i=0;i<r1.getRowLen();i++){
            boolean isRepead = false;
            for(int j=0;j<r2.getRowLen();j++){
                if(isSameTwo(r1,r2,i,j,temp)){
                    isRepead = true;
                }
            }
            if(!isRepead){
                str = addStr(r1,i,str);
                rowLen++;
            }
        }
        for(int i=0;i<r2.getRowLen();i++){
            str = addStrTwo(r2,i,str,temp);
            rowLen++;
        }
        // todo ---------------------------
        RelationBo r3 = null;
        try {
            r3 = new RelationBo(rowLen, r1.getColLen(), r1.getColName(), str);
        }catch (ParamLenException e){
            System.out.print("参数长度错误");
        }
        return r3;
    }

    //3 -----------差-----------
    //保留前者中与后者不重复的项
    public static RelationBo diff(RelationBo r1, RelationBo r2) throws IllegalOperationException {
        // 两个表结构相同才有and的必要（列名集相同，但可不同序）：如果不同，直接抛异常
        if (!checkColName(r1, r2)) {
            throw new IllegalOperationException();
        }
        // todo --------------------------- @manqi 同上，修改：满足列名顺序不同也可以比较，想不出来怎么做可以和我讨论下，可能用到哈希表HashMap
        //1.将r1列与r2列对应，temp[a]=b：r1的第a列对应r2的第b列
        int[] temp = new int[r1.getColLen()];
        for(int i=0;i<r1.getColLen();i++){
            for(int j=0;j<r2.getColLen();j++){
                if(Objects.equals(r1.getColName()[i],r2.getColName()[j])){
                    temp[i] = j;
                }
            }
        }
        //2.取r1一行与r2的每一行对比，如果不重复则加入字符串str，用逗号分开
        int rowLen = 0;
        String str = "";
        for(int i=0;i<r1.getRowLen();i++){
            Boolean isRepead = false;
            for(int j=0;j<r2.getRowLen();j++){
                if(isSameTwo(r1,r2,i,j,temp)){
                    isRepead = true;
                }
            }
            if(!isRepead){
                str = addStr(r1,i,str);
                rowLen++;
            }
        }
        // todo ---------------------------
        RelationBo r3 = null;
        try {
            r3 = new RelationBo(rowLen, r1.getColLen(), r1.getColName(), str);
        }catch (ParamLenException e){
            System.out.print("参数长度错误");
        }
        return r3;
    }


    //4 -----------笛卡尔积-----------
    //三元组*三元组=九元组
    public static RelationBo prod(RelationBo r1, RelationBo r2,int count) {
        //处理必要数据
        int rowLen = r1.getRowLen()*r2.getRowLen();
        int colLen = r1.getColLen()+r2.getColLen();
        //整理列名(改）
        //1.判断相同列
        String[] colName = new String[colLen];
        String[] r1ColName = r1.getColName();
        String[] r2ColName = r2.getColName();
        //修改部分↓
        boolean[] temp1 = new boolean[r1ColName.length];
        boolean[] temp2 = new boolean[r2ColName.length];
        for(int i=0;i<r1ColName.length;i++){
            for(int j=0;j<r2ColName.length;j++){
                if(Objects.equals(r1.getColName()[i],r2.getColName()[j])){
                    temp1[i] = true;
                    temp2[j] = true;
                }
            }
        }
        int num = 0;
        for(int i=0;i<r1.getColLen();i++){
            if(!temp1[i]){
                colName[num++] = r1ColName[i];
            }else{
                colName[num++] = "Relation"+count+"."+r1ColName[i];
            }
        }
        for(int i=0;i<r2.getColLen();i++){
            if(!temp2[i]){
                colName[num++] = r2ColName[i];
            }else{
                colName[num++] = "Relation"+(count+1)+"."+r2ColName[i];
            }
        }
        //修改部分↑
        //双循环将所有情况加入字符串，然后再赋给新表
        String str = "";
        for(int i=0;i<r1.getRowLen();i++){
            for(int j=0;j<r2.getRowLen();j++){
                str = addStr(r1,i,str);
                str = addStr(r2,j,str);
            }
        }
        RelationBo r3 = null;
        try {
            r3 = new RelationBo(rowLen, colLen, colName, str);
        }catch (ParamLenException e){
            System.out.print("参数长度错误");
        }
        return r3;
    }

    //5 -----------投影-----------
    //取其中的指定列组成新表
    public static RelationBo project(RelationBo r, int[] cols) throws WrongColumnNameException{
        //-----修改部分：判断cols内列是否合法
        int colNum = r.getColLen();
        if(cols.length==0){
            throw new WrongColumnNameException();
        }
        for(int i=0;i<cols.length;i++)
            if(cols[i]>=colNum){
                throw new WrongColumnNameException();
            }
        //-----修改部分
        //5.1 遍历各行找指定列元素加入字符串str
        String str = "";
        for(int i=0;i<r.getRowLen();i++){
            for(int j=0;j<cols.length;j++){
                str+=r.getContent()[i][cols[j]];
                str+=",";
            }
        }
        //5.2 处理列名
        String[] colName = new String[cols.length];
        for(int i=0;i<cols.length;i++){
            colName[i] = r.getColName()[cols[i]];
        }
        //5.3 赋给新表
        RelationBo r3 = null;
        try {
            r3 = new RelationBo(r.getRowLen(), cols.length, colName, str);
        }catch (ParamLenException e){
            System.out.print("参数长度错误");
        }
        return r3;
    }

    //6 -----------除法-----------
    public static RelationBo div(RelationBo r1, RelationBo r2) {
        //6.1 先求相同列
        String temp1 = "";        //相同列在r1中的索引
        String temp2 = "";        //相同列在r2中的索引
        String temp3 = "";        //不相同列的名字
        for(int i=0;i<r1.getColLen();i++){
            Boolean isColSame = false;
            for(int j=0;j<r2.getColLen();j++){
                if(Objects.equals(r1.getColName()[i],r2.getColName()[j])){
                    temp1+=i;
                    temp1+=",";
                    temp2+=j;
                    temp2+=",";
                    isColSame = true;
                    break;
                }
            }
            if(!isColSame){
                temp3+=r1.getColName()[i];
                temp3+=",";
            }
        }
        //处理无相同列的情况
        if(temp1==""&&temp2==""){
            return RelationBo.EMPTY_RELATION;
        }
        String[] r1ColName = temp1.split(",");
        String[] r2ColName = temp2.split(",");
        String[] newColName = temp3.split(",");
        //6.2 求r2对相同列的投影
        int[] r1Temp = new int[r1ColName.length];
        int[] r2Temp = new int[r2ColName.length];
        for(int i=0;i<r1Temp.length;i++){
            r1Temp[i] = Integer.parseInt(r1ColName[i]);
            r2Temp[i] = Integer.parseInt(r2ColName[i]);
        }
        try{
            RelationBo  r2New = project(r2,r2Temp);
            //6.3 如果r1中某一行的相同列与r2投影的某行相同，则将该行去除相同列加入字符串
            String str = "";
            int rowLen = 0;
            //对于r1的每一行的特定列元素都需要与r2New的每一行对比
            for(int i=0;i<r1.getRowLen();i++){
                if(isHasSpecial(r1,r2New,i,r1Temp)){
                    str = deleteSpecialAdd(r1,i,r1Temp,str);
                    rowLen++;
                }
            }
            //6.4 赋给新表
            RelationBo r3 = null;
            try {
                r3 = new RelationBo(rowLen, r1.getColLen()-r1Temp.length, newColName, str);
            }catch (ParamLenException e){
                System.out.print("参数长度错误");
            }
        }catch (WrongColumnNameException e) {
            e.printStackTrace();
        }
        return null;
    }

    //7 -----------连接-----------
    public static RelationBo join(RelationBo r1, RelationBo r2) {
        
        //7.1 求相同列
        String temp1 = "";        //相同列在r1中的索引
        String temp2 = "";        //相同列在r2中的索引
        for(int i=0;i<r1.getColLen();i++){
            for(int j=0;j<r2.getColLen();j++){
                if(Objects.equals(r1.getColName()[i],r2.getColName()[j])){
                    temp1+=i;
                    temp1+=",";
                    temp2+=j;
                    temp2+=",";
                    break;
                }
            }
        }
        //处理无相同列的情况
        if(temp1==""&&temp2==""){
            return prod(r1,r2,0);
        }
        String[] temp1Col = temp1.split(",");
        String[] temp2Col = temp2.split(",");
        int[] r1Col = new int[temp1Col.length];
        int[] r2Col = new int[temp2Col.length];
        for(int i=0;i<temp1Col.length;i++){
            r1Col[i] = Integer.parseInt(temp1Col[i]);
            r2Col[i] = Integer.parseInt(temp2Col[i]);
        }
        //7.2 将特定列完全相同的两表中的两行按 A表非特定列+特定列+B表非特定列 的顺序加入字符串
        String str = "";
        int rowLen = 0;
        for(int i=0;i<r1.getRowLen();i++){
            for(int j=0;j<r2.getRowLen();j++){
                if(isSpecialSame(r1,r2,i,j,r1Col,r2Col)){
                    str = deleteSpecialAdd(r1,i,r1Col,str);
                    str = AddSpecial(r1,i,r1Col,str);
                    str = deleteSpecialAdd(r2,j,r2Col,str);
                    rowLen++;
                }
            }
        }
        //7.3 计算新列名
        int colLen = r1.getColLen()+r2.getColLen()-r1Col.length;
        String[] colName = new String[colLen];
        int n = 0;
        //先赋表r1中非特殊的部分
        for(int i=0;i<r1.getColLen();i++){
            Boolean isSpecial = false;
            for(int j=0;j<r1Col.length;j++){
                if(i==r1Col[j]){
                    isSpecial = true;
                    break;
                }
            }
            if(!isSpecial){
                colName[n++] = r1.getColName()[i];
            }
        }
        for(int i=0;i<r1Col.length;i++){
            colName[n++] = r1.getColName()[r1Col[i]];
        }
        for(int i=0;i<r2.getColLen();i++){
            Boolean isSpecial = false;
            for(int j=0;j<r2Col.length;j++){
                if(i==r2Col[j]){
                    isSpecial = true;
                    break;
                }
            }
            if(!isSpecial){
                colName[n++] = r2.getColName()[i];
            }
        }
        //7.3 赋给新表
        RelationBo r3 = null;
        try {
            r3 = new RelationBo(rowLen,colLen,colName,str);
        }catch (ParamLenException e){
            System.out.print("参数长度错误");
        }
        return r3;
    }

    //8 -----------选择-----------
    public static RelationBo select(RelationBo r, String conditions) throws WrongColumnNameException {
        // 条件为空，返回原表
        if (conditions.equals("")) {
            return r;
        }
        // 空表直接返回原表
        if (r.getRowLen() == 0) {
            return r;
        }
        List<String> post = parsePostExpression(conditions);
        String str = "";
        int rowLen = 0;
        for(int i=0;i<r.getRowLen();i++){
            if(judgeMultipleCondition(post,r,i)) {
                str = addStr(r, i, str);
                rowLen++;
            }
        }
        RelationBo r3 = null;
        try {
            r3 = new RelationBo(rowLen,r.getColLen(),r.getColName(),str);
        }catch (ParamLenException e){
            System.out.print("参数长度错误");
        }
        return r3;
    }

    //辅助方法1：对比表r1的第a行和表r2的第b行是否相同
    //-已测试
    private static boolean isSame(RelationBo r1,RelationBo r2,int a,int b){
        int colNum = r1.getColLen();
        for(int i=0;i<colNum;i++){
            if(!Objects.equals(r1.getContent()[a][i], r2.getContent()[b][i])){
                return false;
            }
        }
        return true;
    }

    //辅助方法1.5：对比表r1的第a行和表r2的第b行是否相同（乱序列时
    private static boolean isSameTwo(RelationBo r1,RelationBo r2,int a,int b,int[] temp){
        int colName = r1.getColLen();
        for(int i=0;i<colName;i++){
            if(!Objects.equals(r1.getContent()[a][i], r2.getContent()[b][temp[i]])){
                return false;
            }
        }
        return true;
    }

    //辅助方法2：把表r第x行加入字符串str
    //-已测试
    private static String addStr(RelationBo r,int x,String str){
        for(int i=0;i<r.getColLen();i++){
            str+=r.getContent()[x][i];
            str+=",";
        }
        return str;
    }

    //辅助方法2.5：把表r第x行加入字符串str
    //-已测试
    private static String addStrTwo(RelationBo r,int x,String str,int[] temp){
        for(int i=0;i<r.getColLen();i++){
            str+=r.getContent()[x][temp[i]];
            str+=",";
        }
        return str;
    }

    //辅助方法3：检查表r2New中是否含有表r1的第x行的特定列
    //已测试
    public static Boolean isHasSpecial(RelationBo r1,RelationBo r2New,int x,int[] r1Temp){
        //依次检查r2New的每一行是否含有r1指定行的特定列
        Boolean isHas = false;
        for(int i=0;i<r2New.getRowLen();i++){
            isHas = true;
            for(int j=0;j<r1Temp.length;j++){
                if(!Objects.equals(r2New.getContent()[i][j],r1.getContent()[x][r1Temp[j]])){
                    isHas = false;
                    break;
                }
            }
            if(isHas) return true;
        }
        return false;
    }

    //辅助方法4：判断表r1第x行的特定列int[]r1Col和表r2第y行的特定列int[]r2Col是否相等
    private static Boolean isSpecialSame(RelationBo r1,RelationBo r2,int x,int y,int[] r1Col,int[] r2Col){
        for(int i=0;i<r1Col.length;i++){
            if(!Objects.equals(r1.getContent()[x][r1Col[i]],r2.getContent()[y][r2Col[i]])){
                return false;
            }
        }
        return true;
    }

    //辅助方法5：对于表r的第x行，去除特定列int[]，加入字符串str
    //已测试
    private static String deleteSpecialAdd(RelationBo r,int x,int[] colName,String str){
        //检查列i是否为特定列
        for(int i=0;i<r.getColLen();i++){
            Boolean isSpecial = false;
            for(int j=0;j<colName.length;j++){
                if(i==colName[j]){
                    isSpecial = true;
                    break;
                }
            }
            if(!isSpecial){
                str+=r.getContent()[x][i];
                str+=",";
            }
        }
        return str;
    }

    //辅助方法6：对于表r的第x行，将其特定列int[]，加入字符串str
    private static String AddSpecial(RelationBo r,int x,int[] colName,String str){
        for(int i=0;i<colName.length;i++){
            str+=r.getContent()[x][colName[i]];
            str+=",";
        }
        return str;
    }

    /**
     * 辅助方法7：条件表达式中缀转后缀
     * @param expression 中缀表达式
     * @return 后缀表达式
     */
    private static List<String> parsePostExpression(String expression) {
        String[] elems = expression.split("\\|");
        List<String> res = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        for (String elem : elems) {
            // elem为非运算符
            if (elem.charAt(0) != '$' && !elem.equals("(") && !elem.equals(")")) {
                res.add(elem);
                continue;
            }
            // elem为括号或是栈空且elem为And或Or时，直接入栈
            if (elem.equals("(") || (elem.charAt(0) == '$') && stack.isEmpty()) {
                stack.push(elem);
                continue;
            }
            // elem为右括号，出栈直到遇到左括号
            if (elem.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    res.add(stack.poll());
                }
                stack.poll();
                continue;
            }
            // 其余情况，将优先级比elem较大的弹栈，最后入栈elem
            while (!stack.isEmpty()
                    && !stack.peek().equals("(")
                    && elem.equals("$or") && (stack.peek().equals("$and") || stack.peek().equals("$or"))) {
                res.add(stack.poll());
            }
            stack.push(elem);
        }
        // 清空栈
        while (!stack.isEmpty()) {
            res.add(stack.poll());
        }
        return res;
    }

    /**
     * 辅助方法8：当前行是否满足复合条件表达式
     * @param conditions 包含and与or与括号的复合条件表达式
     * @param bo 当前所计算的关系
     * @param curRow 当前判断行
     * @return 当前行是否符合结果
     */
    private static boolean judgeMultipleCondition(List<String> conditions, RelationBo bo, int curRow) throws WrongColumnNameException {
        Deque<Boolean> stack = new ArrayDeque<>();
        for (String elem : conditions) {
            if (elem.charAt(0) != '$') {
                stack.push(judgeSingleCondition(elem, bo, curRow));
                continue;
            }
            Boolean b1 = stack.poll(), b2 = stack.poll();
            if (b1 == null || b2 == null) {
                try {
                    throw new ComputingException();
                } catch (ComputingException e) {
                    log.error("条件表达式判断错误", e);
                }
            }
            Boolean res = null;
            if (elem.equals("$and")) {
                res = Boolean.TRUE.equals(b1) && Boolean.TRUE.equals(b2);
            } else {
                res = Boolean.TRUE.equals(b1) || Boolean.TRUE.equals(b2);
            }
            stack.push(res);
        }
        return Boolean.TRUE.equals(stack.peek());
    }

    /**
     * 辅助方法9：单个条件判断
     * @param condition 单个条件，含大于(>)、小于(<)、等于(=)、小于等于(<=)、大于等于(>=)、不等于(!=)
     * @param bo 当前关系
     * @param curRow 需要判断的行
     * @return 当前行是否符合条件
     */
    private static boolean judgeSingleCondition(String condition, RelationBo bo, int curRow) throws WrongColumnNameException {
        //9.1 处理condition: 列名 符号 内容
        String sysmbol = isWhat(condition);
        //9.2 判断
        if(Objects.equals(sysmbol,">")){
            String[] temp = condition.split(">");
            int colNum = bo.getColIndexByName(temp[0]);
            double num = Double.parseDouble(temp[1]);
            if(Double.parseDouble(bo.getContent()[curRow][colNum])>num){
                return true;
            }
            return false;
        }
        else if(Objects.equals(sysmbol,"<")){
            String[] temp = condition.split("<");
            int colNum = bo.getColIndexByName(temp[0]);
            double num = Double.parseDouble(temp[1]);
            if(Double.parseDouble(bo.getContent()[curRow][colNum])<num){
                return true;
            }
            return false;
        }
        else if(Objects.equals(sysmbol,"<=")){
            String[] temp = condition.split("<=");
            int colNum = bo.getColIndexByName(temp[0]);
            double num = Double.parseDouble(temp[1]);
            if(Double.parseDouble(bo.getContent()[curRow][colNum])<=num){
                return true;
            }
            return false;
        }
        else if(Objects.equals(sysmbol,">=")){
            String[] temp = condition.split(">=");
            int colNum = bo.getColIndexByName(temp[0]);
            double num = Double.parseDouble(temp[1]);
            if(Double.parseDouble(bo.getContent()[curRow][colNum])>=num){
                return true;
            }
            return false;
        }
        else if(Objects.equals(sysmbol,"=")){
            String[] temp = condition.split("=");
            int colNum = bo.getColIndexByName(temp[0]);
            if(Objects.equals(bo.getContent()[curRow][colNum],temp[1])){
                return true;
            }
            return false;
        }
        else if(Objects.equals(sysmbol,"!=")){
            String[] temp = condition.split("!=");
            int colNum = bo.getColIndexByName(temp[0]);
            if(!Objects.equals(bo.getContent()[curRow][colNum],temp[1])){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 辅助方法10：判断单个条件符号是什么
     * 单个条件，含大于(>)、小于(<)、等于(=)、小于等于(<=)、大于等于(>=)、不等于(!=)
     * @param condition 条件
     * @return 符号
     */
    private static String isWhat(String condition){
        String[] temp1 = condition.split(">=");
        if(temp1.length!=1) return ">=";
        String[] temp2 = condition.split("<=");
        if(temp2.length!=1) return "<=";
        String[] temp3 = condition.split("!");
        if(temp3.length!=1) return "!=";
        String[] temp4 = condition.split(">");
        if(temp4.length!=1) return ">";
        String[] temp5 = condition.split("<");
        if(temp5.length!=1) return "<";
        return "=";
    }

    // 辅助方法11：检查两个表的列是否相同
    private static boolean checkColName(RelationBo bo1, RelationBo bo2) {
        if (bo1.getColName().length != bo2.getColName().length) {
            return false;
        }
        Map<String, Integer> fq = new HashMap<>();
        for (String name : bo1.getColName()) {
            fq.put(name, 1);
        }
        for (String name : bo2.getColName()) {
            if (!fq.containsKey(name)) {
                return false;
            }
            fq.put(name, 0);
        }
        for (Integer i : fq.values()) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}
