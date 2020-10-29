package com.xusi.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-27 12:15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transfer {
    private String name;
    private List<String> means;
    private List<ExampleSentence> egs;
}
