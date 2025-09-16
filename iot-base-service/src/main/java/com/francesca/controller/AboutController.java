package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.Health1hDao;
import com.francesca.dao.Power5minDao;
import com.francesca.model.DTO.Health1hEntity;
import com.francesca.model.DTO.Power5minEntity;
import com.francesca.model.VO.about.AuthorVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author francesca

 * 2025-08-15
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.About)
@Validated
@Api(tags = "关于接口")
public class AboutController {


    @ApiOperation(value = "查询作者  最多3个")
    @PostMapping( "getAuthor")
    public List<AuthorVO> getAuthor() {

        List<AuthorVO> res = new ArrayList<>();

        AuthorVO authorVO1 = new AuthorVO();

        authorVO1.setPicUrl("http://101.43.122.193:9000/about/blue.JPG");

        authorVO1.setAuthor("XXX，青年作家、自由撰稿人，现居[城市]。毕业于[院校]文学系，擅长散文、小说及文化评论写作，<br> 文字风格细腻深邃，兼具理性思辨与诗意表达。作品散见于《[杂志名]》《[报刊名]》等刊物，曾获[奖项名称]提名。<br>2022年出版首部短篇集《[书名]》，以敏锐的观察力探讨当代人的精神困境，引发读者共鸣。<br>日常关注城市文化、身份认同等议题，业余从事文学翻译与独立出版策划。认为写作是“对抗遗忘的温柔仪式”，<br>持续在虚构与非虚构的边界探索叙事的可能性。");

        res.add(authorVO1);

        AuthorVO authorVO2 = new AuthorVO();

        authorVO2.setPicUrl("http://101.43.122.193:9000/about/blue.JPG");

        authorVO2.setAuthor("我是作者2");

        res.add(authorVO2);

        AuthorVO authorVO3 = new AuthorVO();

        authorVO3.setPicUrl("http://101.43.122.193:9000/about/blue.JPG");

        authorVO3.setAuthor("我是作者3");

        res.add(authorVO3);

        return res;

    }

    @ApiOperation(value = "查询架构 最多5个")
    @PostMapping( "getarch")
    public List<AuthorVO> getarch() {

        List<AuthorVO> res = new ArrayList<>();

        AuthorVO authorVO1 = new AuthorVO();

        authorVO1.setPicUrl("http://101.43.122.193:9000/about/blue.JPG");

        authorVO1.setAuthor("我是作者1");

        res.add(authorVO1);

        AuthorVO authorVO2 = new AuthorVO();

        authorVO2.setPicUrl("http://101.43.122.193:9000/about/blue.JPG");

        authorVO2.setAuthor("我是作者2");

        res.add(authorVO2);

        AuthorVO authorVO3 = new AuthorVO();

        authorVO3.setPicUrl("http://101.43.122.193:9000/about/blue.JPG");

        authorVO3.setAuthor("我是作者3");

        res.add(authorVO3);

        return res;

    }

    @ApiOperation(value = "查询模型 最多5个")
    @PostMapping( "getmodel")
    public List<AuthorVO> getmodel() {
        List<AuthorVO> res = new ArrayList<>();

        AuthorVO authorVO1 = new AuthorVO();

        authorVO1.setPicUrl("http://101.43.122.193:9000/about/blue.JPG");

        authorVO1.setAuthor("我是作者1");

        res.add(authorVO1);

        AuthorVO authorVO2 = new AuthorVO();

        authorVO2.setPicUrl("http://101.43.122.193:9000/about/blue.JPG");

        authorVO2.setAuthor("我是作者2");

        res.add(authorVO2);

        AuthorVO authorVO3 = new AuthorVO();

        authorVO3.setPicUrl("http://101.43.122.193:9000/about/blue.JPG");

        authorVO3.setAuthor("我是作者3");

        res.add(authorVO3);

        return res;


    }

    }
