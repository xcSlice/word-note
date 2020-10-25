package com.xusi.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: IntelliJ IDEA
 * @description: 单词
 * @author: xusi
 * @create:2020-10-23 11:29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Word implements Serializable {
    private String name;
    private String mean;
    private String wordType;
    private String prefix;
    private String postfix;
}
