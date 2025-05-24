package com.francesca.controller;


import com.francesca.constant.UrlConstant;
import com.francesca.dao.AreaDao;
import com.francesca.dao.SubsysDao;
import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.SubsysEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author francesca

 * 2025-05-24
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Area)
@Validated
@Api(tags = "area")
public class AreaController {

    @Autowired
    private AreaDao areaDao;

    @ApiOperation(value = "列出所有空间")
    @GetMapping( "getall")
    public List<AreaEntity> getall() {

        return areaDao.selectAll();
    }

    @ApiOperation(value = "增加空间")
    @PostMapping( "add")
    public boolean add(@RequestBody AreaEntity entity) {
        areaDao.insert(entity);

        return true;
    }

    @ApiOperation(value = "修改空间")
    @PostMapping( "update")
    public boolean update(@RequestBody AreaEntity entity) {
        areaDao.update(entity);

        return true;
    }

    @ApiOperation(value = "删除空间")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {

        return areaDao.delete(id);
    }


}
