package com.xusi.system.service;

import com.xusi.system.entity.Word;
import com.xusi.system.utils.ESUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-24 13:16
 **/
@Service
public class ESService {
    @Resource
    private ESUtil esUtil;

    /**
    * @Description: 初始化索引
    * @Params: []
    * @return: void
    * @Author: xusi
    * @Date: 2020/10/24
    */
    public void init() throws IOException {
        esUtil.init();
    }

    /**
    * @Description: 批量更新
    * @Params: [list]
    * @return: void
    * @Author: xusi
    * @Date: 2020/10/24
    */
    public void bulk(List<Word> list) throws IOException {
        esUtil.bulk(list);
    }

    /**
    * @Description: 单一实体更新
    * @Params: [word]
    * @return: void
    * @Author: xusi
    * @Date: 2020/10/24
    */
    public void post(Word word) throws IOException {
        esUtil.post(word);
    }

    // 查询所有字段

    // 查询 name
    public SearchResponse searchByName(String value, boolean highlight) throws IOException {
        return isHighlight("name",value,highlight);

    }
    // 查询 mean
    public SearchResponse searchByMean(String value, boolean highlight) throws IOException {
        return isHighlight("mean",value,highlight);

    }

    // 查询 wordType
    public SearchResponse searchByWordType(String value, boolean highlight) throws IOException {
        return isHighlight("wordType",value,highlight);

    }
    // 查询 prefix
    public SearchResponse searchByPrefix(String value, boolean highlight) throws IOException {
        return isHighlight("prefix",value,highlight);

    }
    // 查询 suffix
    public SearchResponse searchBySuffix(String value, boolean highlight) throws IOException {
        return isHighlight("suffix",value,highlight);

    }
    // 是否高亮
    public SearchResponse isHighlight(String name, String value, boolean highlight) throws IOException {
        if (highlight){
            return matchSearchHighlight(name, value);
        } else {
            return matchSearchNoHighlight(name, value);
        }
    }

    // 查询属性
    public SearchResponse matchSearchNoHighlight(String name , String value) throws IOException {
        return esUtil.searchByMatch(name,value,false);

    }

    public SearchResponse matchSearchHighlight(String name, String value) throws IOException {
        return esUtil.searchByMatch(name,value,true);

    }

    // 查询属性 不高亮
    public SearchResponse termSearchNoHighlight(String name , String value) throws IOException {
        return esUtil.searchByTerm(name,value,false);

    }

    public SearchResponse termSearchHighlight(String name, String value) throws IOException {
        return esUtil.searchByTerm(name,value,true);

    }

    // 查询所有
    public SearchResponse searchAll() throws IOException {
        return esUtil.searchAll();
    }

    // 查询id
    public List<String> getId(String name, String value) throws IOException {
        return esUtil.getId(name, value);
    }

    // 删除所有
    public void deleteAll() throws IOException{
        esUtil.deleteAll();
    }

    // 删除单个
    public void deleteOne(String id) throws IOException{
        esUtil.deleteOne(id);
    }
    // 删除多个
    public void deleteBatch(List<String> ids) throws IOException{
        esUtil.deleteBatch(ids);
    }

}
