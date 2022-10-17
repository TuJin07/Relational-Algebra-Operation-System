package com.example.operation_system.Bo;

import com.example.operation_system.exception.ParamLenException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: operation_system
 * @description: 表示一个关系（表）的类
 * @author: Xuan
 * @create: 2022-10-17 15:32
 **/

@Slf4j
public class RelationBo {

    private final int rowLen;

    private final int colLen;

    private final String[][] content;

    public RelationBo(int rowLen, int colLen, String relation) throws ParamLenException {
        this.rowLen = rowLen;
        this.colLen = colLen;
        content = new String[rowLen][colLen];
        setContent(relation);
    }

    public RelationBo(int rowLen, int colLen, String[][] content) throws ParamLenException {
        this.rowLen = rowLen;
        this.colLen = colLen;
        if (content.length != rowLen || (content.length != 0 && content[0].length != colLen)) {
            throw new ParamLenException();
        }
        this.content = content;
    }

    public String getElem(int row, int col) {
        return content[row][col];
    }

    public String[] getRow(int row) {
        return content[row];
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
