package com.xusi.system.controller;

import com.xusi.search.entity.Transfer;
import com.xusi.system.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: IntelliJ IDEA
 * @description: 查询单词
 * @author: xusi
 * @create:2020-10-28 22:06
 **/
@Api(tags = "单词查询")
@RestController("/search-word")
public class SearchWordController {

    @Resource
    private SearchService searchService;

    @ApiOperation("查询单词,返回单词意思和例句")
    @PostMapping
    public ResponseEntity<Transfer> search(@ApiParam("需要查询的单词") String word){
        try{
            Transfer search = searchService.search(word);
            return ResponseEntity.ok(search);
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
}
