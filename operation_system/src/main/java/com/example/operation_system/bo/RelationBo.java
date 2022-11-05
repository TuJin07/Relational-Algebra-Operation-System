package com.example.operation_system.bo;

import com.example.operation_system.exception.ParamLenException;
import com.example.operation_system.vo.RelationVo;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: operation_system
 * @description: 表示一个关系（表）的类
 * @author: Xuan
 * @create: 2022-10-17 15:32
 **/

@Slf4j
@Getter
@ToString
public class RelationBo {

    private final int rowLen;

    private final int colLen;

    private final String[] colName;

    private final String[][] content;

    public static RelationVo toRelationVo(RelationBo bo, String name) {
        RelationVo res = new RelationVo();
        res.setName(name);
        res.setColLen(bo.getColLen());
        res.setRowLen(bo.getRowLen());
        String content = null;
        for (int i = 0; i < bo.getRowLen(); i++) {
            for (int j = 0; j < bo.getColLen(); j++) {
                // todo
            }
        }
        String colName = null;
        for (int i = 0; i < bo.getColName().length; i++) {
            // todo
        }
        res.setContent(content);
        res.setColName(colName);
        return res;
    }

    public static RelationBo toRelationBo(RelationVo vo, String name) {
        // todo @manqi
        return null;
    }

    public RelationBo(RelationBo r) {
        rowLen = r.rowLen;
        colLen = r.colLen;
        colName = r.colName;
        content = r.content;
    }

    public RelationBo(int rowLen, int colLen, String[] colName, String relation) throws ParamLenException {
        this.rowLen = rowLen;
        this.colLen = colLen;
        content = new String[rowLen][colLen];
        setContent(relation);
        this.colName = colName;
    }

    public RelationBo(int rowLen, int colLen, String[] colName, String[][] content) throws ParamLenException {
        this.rowLen = rowLen;
        this.colLen = colLen;
        if (content.length != rowLen || (content.length != 0 && content[0].length != colLen)) {
            throw new ParamLenException();
        }
        this.content = content;
        this.colName = colName;
    }

    public String getElem(int row, int col) {
        return content[row][col];
    }

    public String[] getRow(int row) {
        return content[row];
    }

    public int getColIndexByName(String name) {
        int index = 0;
        for (int i = 0; i < colName.length; i++) {
            if (name.equals(colName[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void setContent(String relation) throws ParamLenException {
        String[] temp = relation.split(",");
        if (temp.length != rowLen * colLen) {
            throw new ParamLenException();
        }
        int k = 0;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                content[i][j] = temp[k++];
            }
        }
    }

}
