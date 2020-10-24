package com.xusi.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: IntelliJ IDEA
 * @description: 读写Excel
 * @author: xusi
 * @create:2020-10-23 11:33
 **/
@Controller("/excel")
public class ExcelController {
    @PostMapping("/test")
    public void test() {
        System.out.println("test");
    }

    @PostMapping("/excel/test")
    public void tt() {
        System.out.println("excel/test");

    }
}