package com.xusi.system.utils;

import com.alibaba.fastjson.JSON;
import com.xusi.system.entity.Word;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description: ElasticSearch工具类
 * @author: xusi
 * @create:2020-10-23 18:59
 **/
@Component
public class ESUtil {

    @Resource
    private RestHighLevelClient client;
    private String index;

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        if(index == null) index = "word";
        return index;
    }

    public void bulk(List<Word> list) throws IOException {
        // 1. 创建批量操作请求
        BulkRequest bulkRequest = new BulkRequest();
        // 2. 把 Index 请求添加到 bulk 中
        list.forEach(word -> {
            bulkRequest.add(new IndexRequest(getIndex()).source(JSON.toJSONString(word), XContentType.JSON));
        });
        // 3. 执行批量请求得到响应
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    public void post(Word word) throws IOException {
        // 1. 创建请求
        IndexRequest request = new IndexRequest(getIndex());
        request.source(JSON.toJSONString(word),XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
    }

    public void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(getIndex());
        client.indices().create(request,RequestOptions.DEFAULT);
    }

    // 初始化
    public void init() throws IOException{
        GetIndexRequest request = new GetIndexRequest(getIndex());
        boolean exist = client.indices().exists(request,RequestOptions.DEFAULT);
        // 如果不存在则创建索引
        if(!exist) {
            createIndex();
        }
    }

    public SearchResponse searchByTerm(String name,String value) throws IOException {
        // 1. 构建查询条件
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);
//        // 2. 搜索资源构造器
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(termQueryBuilder);
//
//        // 3. 查询请求
//        SearchRequest request = new SearchRequest(getIndex());
//        request.source(searchSourceBuilder);
//        // 4. 获取响应
//        return client.search(request, RequestOptions.DEFAULT);
        return search(termQueryBuilder);
    }

    // 传入查询条件 获取查询结果
    public SearchResponse search(QueryBuilder builder) throws IOException {
//        // 1. 构建查询条件
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);
//        // 2. 搜索资源构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(builder);

        // 3. 查询请求
        SearchRequest request = new SearchRequest(getIndex());
        request.source(searchSourceBuilder);
        // 4. 获取响应
        return client.search(request, RequestOptions.DEFAULT);
    }

}
