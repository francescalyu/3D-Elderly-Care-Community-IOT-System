package com.francesca.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.PointDao;
import com.francesca.dao.ProductDao;
import com.francesca.model.DTO.PointEntity;
import com.francesca.model.DTO.WarnRuleEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WarnRuleService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PointDao pointDao;


    private  String  formatRule(List<WarnRuleEntity> warnRuleEntities , String opValue ){

        String  out = "";

        for (int i = 0; i<warnRuleEntities.size(); i++){

           WarnRuleEntity warnRuleEntity = warnRuleEntities.get(i);

           if (ObjectUtil.isNotEmpty(warnRuleEntity.getPid()) &&  ObjectUtil.isNotEmpty(warnRuleEntity.getProd())){

               PointEntity pointEntity = pointDao.selectByUid(warnRuleEntity.getPid().intValue());

               // exit,  point not exist
               if (ObjectUtil.isEmpty(pointEntity)){
                   return null;
               }

               out = "if ";



           }


        }

        return out;
    }




}
