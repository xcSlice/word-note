package com.xusi.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.xusi.system.entity.Word;
import com.xusi.system.service.ESService;
import com.xusi.system.utils.ExcelUtil;
import com.xusi.system.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-24 13:26
 **/
@Api(tags = "ES搜索")
@RestController("/es")
public class ESController {
    @Resource
    private ESService esService;
    @Resource
    private ExcelUtil excelUtil;
    @Resource
    private JsonUtil jsonUtil;

    @ApiOperation("将excel中的数据上传到es服务器中")
    @PostMapping("/upload")
    public ResponseEntity upload() {
        try{
            List<Word> words = excelUtil.read();
            esService.bulk(words);
            return ok();
        } catch (Exception e){
            return error();
        }
    }
    @ApiOperation("将es服务器中的数据下载到本地")
    @PostMapping("/download")
    public ResponseEntity download() {
        try{
            SearchResponse response = esService.searchAll();
            List<Word> list = jsonUtil.parseWordList(response);
            excelUtil.write(list);
            return ok();
        } catch (Exception e){
            return error();
        }
    }


    @ApiOperation("上传一个单词到es服务器中")
    @PostMapping("/post")
    public ResponseEntity post(Word word){
        try {
            esService.post(word);
            return ok();
        }catch (IOException e){
            return error();
        }
    }



    @ApiOperation("根据名字查询并高亮,其余属性值为实现接口,service中有具体实现")
    @PostMapping("/name/h")
    public ResponseEntity searchByNameAndHighlight(String value){
        try{
            SearchResponse searchResponse = esService.searchByName(value, true);
            List<JSONObject> list = jsonUtil.parseHighlightList(searchResponse,"name");
            return ResponseEntity.ok(list);
        } catch (IOException e){
            return error();
        }
    }

    @ApiOperation("根据传入的参数和值进行查询,返回值高亮")
    @PostMapping("/param/h")
    public ResponseEntity searchAndHighlight(String name , String value){
        try{
            SearchResponse searchResponse = esService.matchSearchHighlight(name, value);
            List<JSONObject> jsonObjects = jsonUtil.parseHighlightList(searchResponse, name);
            return ResponseEntity.ok(jsonObjects);
        } catch (IOException e) {
            e.printStackTrace();
            return error();
        }
    }

    @ApiOperation("根据传入的参数和值进行查询")
    @PostMapping("/param")
    public ResponseEntity search(String name , String value){
        try{
            SearchResponse searchResponse = esService.matchSearchNoHighlight(name, value);
            List<Word> list = jsonUtil.parseWordList(searchResponse);
            return ResponseEntity.ok(list);
        } catch (IOException e) {
            e.printStackTrace();
            return error();
        }
    }


    @ApiOperation("查询所有")
    @GetMapping("/all")
    public ResponseEntity searchAll(){
        try{
            SearchResponse response = esService.searchAll();
            List<Word> list = jsonUtil.parseWordList(response);
            return ResponseEntity.ok(list);
        } catch (IOException e){
            return error();
        }
    }

    @ApiOperation("根据属性和值查询id")
    public ResponseEntity getId(String name ,String value){
        try{
            List<String> id = esService.getId(name, value);
            return ResponseEntity.ok(id);
        }catch (IOException e){
            e.printStackTrace();
            return error();
        }
    }


    @ApiOperation("根据id删除")
    @DeleteMapping
    public ResponseEntity deleteById(String id){
        try{
            esService.deleteOne(id);
            return ok();
        } catch (IOException e){
            return error();
        }

    }
    @ApiOperation("根据List<id>删除")
    @DeleteMapping("/ids")
    public ResponseEntity deleteByIds(List<String> ids){
        try{
            esService.deleteBatch(ids);
            return ok();
        } catch (IOException e){
            return error();
        }

    }
    @ApiOperation("删除所有")
    @DeleteMapping("/all")
    public ResponseEntity deleteAll(){
        try{
            esService.deleteAll();
            return ok();
        } catch (IOException e){
            return error();
        }

    }











    private ResponseEntity error(){
        return ResponseEntity.status(500).build();
    }

    private ResponseEntity ok(){
        return ResponseEntity.ok().build();
    }
}
