package com.xusi.system.utils;

import com.alibaba.fastjson.JSON;
import com.xusi.system.entity.Word;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
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

    public SearchResponse searchByTerm(String name,String value,boolean highlight) throws IOException {
        // 1. 构建查询条件
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);

        if(highlight){
            return search(termQueryBuilder,highlight(name));
        } else {
            return search(termQueryBuilder,null);
        }
    }

    // 传入查询条件 获取查询结果
    public SearchResponse search(QueryBuilder builder,HighlightBuilder highlightBuilder) throws IOException {
//        // 1. 构建查询条件
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);
//        // 2. 搜索资源构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(builder);
        // highlightbuilder not null
        if (highlightBuilder != null) {
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        // 3. 查询请求
        SearchRequest request = new SearchRequest(getIndex());
        request.source(searchSourceBuilder);
        // 4. 获取响应
        return client.search(request, RequestOptions.DEFAULT);
    }

    // 分词查询
    public SearchResponse searchByMatch(String name, String value,boolean highlight) throws IOException {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(name,value);
        if(highlight){
            return search(matchQueryBuilder,highlight(name));
        } else {
            return search(matchQueryBuilder,null);
        }
    }

    // 高亮, 可选项
    public HighlightBuilder highlight(String name){
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(name);
        highlightBuilder.preTags("<em>");
        highlightBuilder.postTags("</em>");
        return highlightBuilder;
    }

    // 查询所有
    public SearchResponse searchAll() throws IOException {
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        SearchResponse search = search(matchAllQueryBuilder, null);
        return search;
    }

    // 获取id
    public List<String> getId(String name,String value) throws IOException{
        List<String> list = new ArrayList<>();
        SearchResponse response = searchByTerm(name, value, false);
        response.getHits().forEach( word -> {
            list.add(word.getId());
        });
        return list;
    }
    // 获取所有id
    public List<String> getAllId() throws IOException{
        List<String> list = new ArrayList<>();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        SearchResponse search = search(matchAllQueryBuilder, null);
        search.getHits().forEach( word -> {
            list.add(word.getId());
        });
        return list;
    }

    // 根据id 获取删除请求
    public DeleteRequest getDeleteRequest(String id) throws IOException{
        DeleteRequest request = new DeleteRequest(getIndex());
        request.id(id);
        return request;
    }

    // 根据List<id> 多个删除
    public void deleteBatch(List<String> ids) throws IOException{
        BulkRequest bulkRequest = new BulkRequest();
        ids.forEach( id -> {
            try {
                bulkRequest.add(getDeleteRequest(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        client.bulk(bulkRequest,RequestOptions.DEFAULT);
    }

    //删除所有
    public void deleteAll() throws IOException {
        deleteBatch(getAllId());
    }

    // 根据id删除
    public void deleteOne(String id) throws IOException{
        DeleteRequest deleteRequest = getDeleteRequest(id);
        client.delete(deleteRequest,RequestOptions.DEFAULT);
    }
}
