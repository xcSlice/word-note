//package com.xusi.system.controller;
//
//import com.xusi.system.entity.Word;
//import com.xusi.system.service.ExcelService;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.function.EntityResponse;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.List;
//
///**
// * @program: IntelliJ IDEA
// * @description: 读写Excel
// * @author: xusi
// * @create:2020-10-23 11:33
// **/
//@Controller("/excel")
//public class ExcelController {
//    @Resource
//    ExcelService excelService;
//
//    @ApiOperation("同步es服务器的数据到本地excel中")
//    @GetMapping
//    public ResponseEntity get() {
//        try {
//            List<Word> wordList = excelService.read();
//            return ResponseEntity.ok(excelService.read());
//        } catch (Exception e){
//            return ResponseEntity.status(500).build();
//        }
//    }
//    @ApiOperation("同步本地数据到es服务器中")
//    @PostMapping
//    public ResponseEntity post(List<Word> list){
//        try{
//            return ResponseEntity.ok(excelService.write(list));
//        } catch (IOException e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
//}