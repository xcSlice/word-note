package com.xusi.system.controller;

import com.xusi.system.service.FileService;
import com.xusi.system.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: IntelliJ IDEA
 * @description: 上传下载
 * @author: xusi
 * @create:2020-10-23 11:33
 **/
@Api(tags = "文件管理模块")
@Controller
public class FileController {
    @Resource
    FileService fileService;

    // 上传文件
    @ApiOperation("文件上传转发")
    @GetMapping("/upload")
    public String uploading() {
        //跳转到 templates 目录下的 uploading.html
        return "uploading";
    }

    //处理文件上传
    @ApiOperation("文件上传,不能在 swagger 中测试, 需要传入一个 excel 文件")
    @PostMapping("/uploading")
    @ResponseBody
    public ResponseEntity<Boolean> uploading(@RequestParam("file") MultipartFile file,
                                      HttpServletRequest request) {
        boolean result = false;
        try {
             result = fileService.uploadFile(file.getBytes(),file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("文件下载")
    @PostMapping("downloading")
    public ResponseEntity<Boolean> download(@ApiParam("文件下载地址") String filename) throws IOException {
        boolean result = fileService.downloadFile(filename);
        return ResponseEntity.ok(result);
    }

}
