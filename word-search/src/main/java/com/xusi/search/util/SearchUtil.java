package com.xusi.search.util;

import com.xusi.search.entity.ExampleSentence;
import com.xusi.search.entity.Transfer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description: 搜索工具
 * @author: xusi
 * @create:2020-10-27 12:46
 **/

public class SearchUtil {
    String word = "bigger";
    public Transfer search(String word) throws IOException {

        String url = "http://www.iciba.com/word?w=";
        List<String> list = new ArrayList<>();
        List<ExampleSentence> egsList = new ArrayList<>();

        Document document = Jsoup.connect(url + word).get();
        Elements mean = document.body().getElementsByClass("Mean_part__1RA2V");
        // 意思
        Elements element = document.select(".Mean_part__1RA2V");
        Elements span = element.select("span");
        span.forEach(item -> {
            list.add(item.text().replace(";",""));
        });
        // 例句
        Elements egParent = document.select(".SceneSentence_scene__1Dnz6");
        Elements egs = egParent.select(".NormalSentence_sentence__3q5Wk");
        egs.forEach(item -> {
            ExampleSentence exampleSentence = new ExampleSentence();
            exampleSentence.setEn(item.select(".NormalSentence_en__3Ey8P > span").text());
            exampleSentence.setCn(item.select(".NormalSentence_cn__27VpO").text().replace(" ",""));
            exampleSentence.setQuote(item.select(".NormalSentence_from__2-ibN").text());
            egsList.add(exampleSentence);
        });

        // 封装查询结果
        Transfer transfer = new Transfer();
        transfer.setName(word);
        transfer.setMeans(list);
        transfer.setEgs(egsList);
        return transfer;
    }
}
