package com.example.operation_system.util;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.exception.ParamLenException;

import java.util.ArrayList;

/**
 * @program: operation_system
 * @description: 计算工具类，负责关系代数运算
 * @author: Xuan
 * @create: 2022-10-18 21:23
 **/
public class ComputingUtil {

    //1 -----------并-----------
    //合并两个集合，去掉重复的
    public static RelationBo and(RelationBo r1, RelationBo r2){
        // todo @manqi
        //首先取r1一行与r2的每一行对比，如果无重复则加入字符串str，用逗号分开
        String str = "";
        int rowLen = 0;
        for(int i=0;i<r1.getRowLen();i++){
            Boolean isRepead = false;
            for(int j=0;j<r2.getRowLen();j++){
                if(isSame(r1,r2,i,j)){
                    isRepead = true;
                    break;
                }
            }
            if(!isRepead){
                addStr(r1,i,str);
                rowLen++;
            }
        }
        //其次将r2加入str
        for(int i=0;i<r2.getRowLen();i++){
            addStr(r2,i,str);
            rowLen++;
        }
        try {
            RelationBo r3 = new RelationBo(rowLen,r1.getColLen(),r1.getColName(),str);
            return r3;
        }catch (ParamLenException e){
            System.out.print("不知道哪里有问题");
        }
        return null;
    }

    //2 -----------交-----------
    //选出两个关系内相同的元组
    public static RelationBo or(RelationBo r1, RelationBo r2) {
        // todo @manqi
        //取r1一行与r2的每一行对比，如果重复则加入字符串str，用逗号分开
        String str = "";
        int rowLen = 0;
        for(int i=0;i<r1.getRowLen();i++){
            Boolean isRepead = false;
            for(int j=0;j<r2.getRowLen();j++){
                if(isSame(r1,r2,i,j)){
                    isRepead = true;
                    break;
                }
            }
            if(isRepead){
                addStr(r1,i,str);
                rowLen++;
            }
        }
        try {
            RelationBo r3 = new RelationBo(rowLen,r1.getColLen(),r1.getColName(),str);
            return r3;
        }catch (ParamLenException e){
            System.out.print("不知道哪里有问题");
        }
        return null;
    }

    //3 -----------差-----------
    //保留前者中与后者不重复的项
    public static RelationBo diff(RelationBo r1, RelationBo r2) {
        // todo @manqi
        //取r1一行与r2的每一行对比，如果不重复则加入字符串str，用逗号分开
        String str = "";
        int rowLen = 0;
        for(int i=0;i<r1.getRowLen();i++){
            Boolean isRepead = false;
            for(int j=0;j<r2.getRowLen();j++){
                if(isSame(r1,r2,i,j)){
                    isRepead = true;
                    break;
                }
            }
            if(!isRepead){
                addStr(r1,i,str);
                rowLen++;
            }
        }
        try {
            RelationBo r3 = new RelationBo(rowLen,r1.getColLen(),r1.getColName(),str);
            return r3;
        }catch (ParamLenException e){
            System.out.print("不知道哪里有问题");
        }
        return null;
    }


    //4 笛卡尔积
    //三元组*三元组=九元组
    public static RelationBo prod(RelationBo r1, RelationBo r2) {
        // todo @manqi
        // 处理必要数据
        int rowLen = r1.getRowLen()*r2.getRowLen();
        int colLen = r1.getColLen()+r2.getColLen();
        //整理列名
        String[] colName = new String[colLen];
        String[] r1ColName = r1.getColName();
        String[] r2ColName = r2.getColName();
        int num = 0;
        for(int i=0;i<r1.getColLen();i++){
            colName[num++] = r1ColName[i];
        }
        for(int i=0;i<r2.getColLen();i++){
            colName[num++] = r2ColName[i];
        }

        // 双循环将所有情况加入字符串，然后再赋给新表
        String str = "";
        for(int i=0;i<r1.getRowLen();i++){
            for(int j=0;j<r2.getRowLen();j++){
                addStr(r1,i,str);
                addStr(r2,i,str);
            }
        }
        try {
            RelationBo r3 = new RelationBo(rowLen,colLen,colName,str);
            return r3;
        }catch (ParamLenException e){
            System.out.print("不知道哪里有问题");
        }
        return null;
    }

    //辅助方法1：对比表r1的第a行和表r2的第b行是否相同
    private static boolean isSame(RelationBo r1,RelationBo r2,int a,int b){
        int colLen = r1.getColLen();                //列数
        for(int i=0;i<colLen;i++){
            if(r1.getElem(a,i)!=r2.getElem(b,i)){
                return false;
            }
        }
        return true;
    }

    //辅助方法2：把表r第i行加入字符串str
    private static void addStr(RelationBo r,int i,String str){
        for(int j=0;j<r.getColLen();j++){
            str+=r.getElem(i,j);
            str+=",";
        }
    }

    public static RelationBo div(RelationBo r1, RelationBo r2) {
        // todo
        return null;
    }

    public static RelationBo select(RelationBo r, String condition) {
        // TODO: 2022/10/18 待确定接口
        return null;
    }

    public static RelationBo project(RelationBo r, int[] cols) {
        // TODO: 2022/10/18 待确定接口
        return null;
    }

    public static RelationBo join(RelationBo r1, RelationBo r2) {
        // TODO: 2022/10/18 待确定接口
        return null;
    }
}
