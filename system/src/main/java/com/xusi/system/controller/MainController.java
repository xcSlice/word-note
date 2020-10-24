package com.xusi.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-23 12:37
 **/
@Controller
public class MainController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
