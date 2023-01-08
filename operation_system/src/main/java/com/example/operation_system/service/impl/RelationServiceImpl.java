package com.example.operation_system.service.impl;

import com.example.operation_system.bo.RelationBo;
import com.example.operation_system.exception.ParamLenException;
import com.example.operation_system.exception.RelationAlreadyExistsException;
import com.example.operation_system.exception.RepeatedColumnNameException;
import com.example.operation_system.service.RelationService;
import com.example.operation_system.vo.RelationVo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: operation_system
 * @description:
 * @author: Xuan
 * @create: 2022-10-18 21:09
 **/

@Service
public class RelationServiceImpl implements RelationService {

    /**
     * 存储已有关系的Map，Key为关系名，Value为Bo
     */
    private static final ConcurrentHashMap<String, RelationBo> relations = new ConcurrentHashMap<>();

    @Override
    public void insertRelation(RelationVo relationVo) throws ParamLenException, RelationAlreadyExistsException, RepeatedColumnNameException {
        if (!checkRepeatedColumnName(relationVo)) {
            throw new RepeatedColumnNameException();
        }
        String[] colName = getColName(relationVo);
        String[][] content = getContent(relationVo);
        RelationBo bo = new RelationBo(relationVo.getRowLen(), relationVo.getColLen(), colName, content);
        if (relations.containsKey(relationVo.getName())) {
            throw new RelationAlreadyExistsException();
        }
        relations.put(relationVo.getName(), bo);
    }

    @Override
    public void insertRelation(RelationBo relationBo, String name) throws ParamLenException {
        relations.put(name, relationBo);
    }

    @Override
    public void deleteRelation(String name) {
        relations.remove(name);
    }

    @Override
    public void deleteAll() {
        relations.clear();
    }

    @Override
    public boolean contains(String key) {
        return relations.containsKey(key);
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

    private boolean checkRepeatedColumnName(RelationVo vo) {
        String[] colName = vo.getColName().split(",");
        Set<String> set = new HashSet<>(Arrays.asList(colName));
        return set.size() == colName.length;
    }
}
