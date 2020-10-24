package com.xusi.system;

import com.alibaba.fastjson.JSON;
import com.xusi.system.entity.Word;
import com.xusi.system.utils.ESUtil;
import com.xusi.system.utils.ExcelUtil;
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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

    final String PATH = "./excel/";

    @Test
    void contextLoads() {
    }

    @Test
    void testExcelRead() throws IOException {
        List<Word> l = excelUtil.read();
        l.forEach(item -> {
            System.out.println("item = " + item);
        });
    }

    @Test
    void testWrite() throws IOException{
        List<Word> list = new ArrayList<>();
        list.add(new Word("orange","orange","or","or","or"));
        list.add(new Word("orange","orange","or","or","or"));
        list.add(new Word("orange","orange","or","or","or"));
        System.out.println("excelUtil.write(list) = " + excelUtil.write(list));
    }

    @Test
    void testEs() throws IOException {
        esUtil.init();
        List<Word> list = excelUtil.read();
        esUtil.bulk(list);
        System.out.println("success");
    }

    @Test
    void testEsSearch() throws IOException {
        SearchHits searchHits = esUtil.searchByTerm("name", "orange");
        List<String> list = new ArrayList<>();
        searchHits.forEach( item -> {
            list.add(item.getSourceAsString());
        });
        list.forEach( item -> {
            System.out.println("item = " + item);
            Object parse = JSON.parse(item);
            Word word = (Word) parse;
            System.out.println(word.getMean());
        });
    }








}
