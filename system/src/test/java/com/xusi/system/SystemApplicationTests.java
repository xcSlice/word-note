package com.xusi.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xusi.system.entity.Word;
import com.xusi.system.utils.ESUtil;
import com.xusi.system.utils.ExcelUtil;
import com.xusi.system.utils.JsonUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.fetch.subphase.highlight.HighlighterContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SystemApplicationTests {

    @Resource
    private RestHighLevelClient client;

    @Resource
    private ESUtil esUtil;

    @Resource
    private ExcelUtil excelUtil;
    @Resource
    private JsonUtil jsonUtil;
//
//    final String PATH = "./excel/";

    @Test
    void contextLoads() throws  IOException{
        SearchResponse searchResponse = esUtil.searchByMatch("name", "peach", true);
        searchResponse.getHits().forEach(
                x->{
                    JSONObject jsonObject = JSON.parseObject(x.getSourceAsString());
//                    jsonObject.getJSONObject("name").put("name",x.getHighlightFields().get("name").fragments()[0].toString());
                    jsonObject.put("name","helima");
                    System.out.println("jsonObject = " + jsonObject);

                    System.out.println("jsonObject.get(\"name\") = " + jsonObject.get("name"));
                }
        );
    }

    @Test
    void searchAll() throws IOException {
        SearchResponse response = esUtil.searchAll();
        List<Word> list = jsonUtil.parseWordList(response);
        for (Word word : list) {
            System.out.println("word = " + word);
        }

    }

    @Test
    void getId() throws IOException {
        List<String> allId = esUtil.getAllId();
        allId.forEach(w -> {
            System.out.println("w = " + w);
        });
        List<String> id = esUtil.getId("name", "peach");
        System.out.println("id.get(0) = " + id.get(0));

    }
    @Test
    void deleteAll() throws IOException{
        esUtil.deleteAll();
    }












}
