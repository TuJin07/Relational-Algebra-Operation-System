package com.example.operation_system.service.impl;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.exception.ParamLenException;
import com.example.operation_system.service.RelationService;
import com.example.operation_system.vo.RelationVo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: operation_system
 * @description:
 * @author: Xuan
 * @create: 2022-10-18 21:09
 **/
public class RelationServiceImpl implements RelationService {

    /**
     * 存储已有关系的Map，Key为关系名，Value为Bo
     */
    private static final ConcurrentHashMap<String, RelationBo> relations = new ConcurrentHashMap<>();

    @Override
    public void insertRelation(RelationVo[] relationVos) throws ParamLenException {
        for (RelationVo vo : relationVos) {
            // TODO: 2022/10/18 把vo->bo的过程转移到bo类内
            String[] colName = getColName(vo);
            String[][] content = getContent(vo);
            RelationBo bo = new RelationBo(vo.getRowLen(), vo.getColLen(), colName, content);
            relations.put(vo.getName(), bo);
        }
    }

    @Override
    public void deleteRelation() {
        relations.clear();
    }

    public RelationBo get(String name) {
        return relations.get(name);
    }

    private String[] getColName(RelationVo vo) throws ParamLenException {
        String[] res = vo.getColName().split(",");
        if (res.length != vo.getColLen()) {
            throw new ParamLenException();
        }
        return res;
    }


    private String[][] getContent(RelationVo vo) throws ParamLenException {
        String[] temp = vo.getContent().split(",");
        String[][] res = new String[vo.getRowLen()][vo.getColLen()];
        if (temp.length != vo.getRowLen() * vo.getColLen()) {
            throw new ParamLenException();
        }
        int k = 0;
        for (int i = 0; i < vo.getRowLen(); i++) {
            for (int j = 0; j < vo.getColLen(); j++) {
                 res[i][j] = temp[k++];
            }
        }
        return res;
    }
}
