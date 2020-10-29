package com.xusi.system.service;

import com.xusi.search.entity.Transfer;
import com.xusi.search.util.SearchUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @program: IntelliJ IDEA
 * @description: 查询单词接口
 * @author: xusi
 * @create:2020-10-28 22:04
 **/
@Service
public class SearchService {



    public Transfer search(String word) throws IOException {
        SearchUtil searchUtil = new SearchUtil();
        return searchUtil.search(word);
    }
}
