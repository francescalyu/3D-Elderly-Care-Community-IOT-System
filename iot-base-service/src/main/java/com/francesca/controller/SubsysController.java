package com.francesca.controller;


import com.francesca.constant.UrlConstant;
import com.francesca.dao.SubsysDao;
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
@RequestMapping(UrlConstant.Subsys)
@Validated
@Api(tags = "Subsys")
public class SubsysController {

    @Autowired
    private SubsysDao subsysDao;

    @ApiOperation(value = "列出所有子系统")
    @GetMapping( "getall")
    public List<SubsysEntity> getall() {

        return subsysDao.selectAll();
    }

    @ApiOperation(value = "增加子系统")
    @PostMapping( "add")
    public boolean add(@RequestBody SubsysEntity entity) {
        subsysDao.insert(entity);

        return true;
    }

    @ApiOperation(value = "修改子系统")
    @PostMapping( "update")
    public boolean update(@RequestBody SubsysEntity entity) {
        subsysDao.update(entity);

        return true;
    }

    @ApiOperation(value = "删除子系统")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {

        return subsysDao.delete(id);
    }


}
