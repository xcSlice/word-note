package com.xusi.system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xusi.system.entity.Word;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-24 12:38
 **/
@Component
public class JsonUtil {

    /**
    * @Description: 解析查询的结果返回 List
    * @Params: [response]
    * @return: java.util.List<com.xusi.system.entity.Word>
    * @Author: xusi
    * @Date: 2020/10/24
    */
    public List<Word> parseWordList(SearchResponse response){
        List<Word> list = new ArrayList<>();
        response.getHits().forEach(word -> {
            list.add(JSON.parseObject(word.getSourceAsString(),Word.class));
        });
        return list;
    }

    // 通用性更强, 类型转换麻烦
    public List<Object> parseList(SearchResponse response,Class clazz){
        List<Object> list = new ArrayList<>();
        response.getHits().forEach(word -> {
            list.add(JSON.parseObject(word.getSourceAsString(),clazz));
        });
        return list;
    }

    public List<String> parseStringList(SearchResponse response){
        List<String> list = new ArrayList<>();
        response.getHits().forEach( w -> {
            list.add(w.getSourceAsString());
        });
        return list;
    }

    // 高亮解析
    public List<JSONObject> parseHighlightList(SearchResponse response,String name){
        List<JSONObject> list = new ArrayList<>();
        response.getHits().forEach( w -> {
            JSONObject word = JSON.parseObject(w.getSourceAsString());
            word.put(name,w.getHighlightFields().get(name).fragments()[0].toString());
            list.add(word);
        });
        return list;
    }
}
