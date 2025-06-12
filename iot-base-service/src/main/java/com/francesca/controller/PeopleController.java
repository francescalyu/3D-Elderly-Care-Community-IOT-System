package com.francesca.controller;


import com.francesca.constant.UrlConstant;
import com.francesca.dao.AreaDao;
import com.francesca.dao.PeopleDao;
import com.francesca.model.DTO.AreaEntity;
import com.francesca.model.DTO.PeopleEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author francesca

 * 2025-06-12
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.People)
@Validated
@Api(tags = "people")
public class PeopleController {

    @Autowired
    private PeopleDao peopleDao;

    @ApiOperation(value = "列出所有维护人员")
    @GetMapping( "getall")
    public List<PeopleEntity> getall() {

        List<PeopleEntity> peopleEntities = peopleDao.selectAll();
        return peopleEntities.stream().filter(v -> v.getType() !=1 ).collect(Collectors.toList());

    }

    @ApiOperation(value = "列出当前租户")
    @GetMapping( "getTenant")
    public List<PeopleEntity> getTenant() {

        List<PeopleEntity> peopleEntities = peopleDao.selectAll();
        return peopleEntities.stream().filter(v -> v.getType() ==1 ).collect(Collectors.toList());

    }


    @ApiOperation(value = "增加人员")
    @PostMapping( "add")
    public boolean add(@RequestBody PeopleEntity entity) {
        peopleDao.insert(entity);

        return true;
    }

    @ApiOperation(value = "修改人员")
    @PostMapping( "update")
    public boolean update(@RequestBody PeopleEntity entity) {
        peopleDao.update(entity);

        return true;
    }

    @ApiOperation(value = "删除人员")
    @DeleteMapping( "delete")
    public boolean delete(@RequestParam int id) {

        return peopleDao.delete(id);
    }


}
