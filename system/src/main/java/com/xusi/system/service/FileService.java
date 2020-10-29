package com.xusi.system.service;

import com.xusi.system.utils.ExcelUtil;
import com.xusi.system.utils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-23 11:47
 **/
@Service
public class FileService {

    @Resource
    private ExcelUtil excelUtil;

    String path = "";



    private void setPath(){
        if(path.equals("")){
            // 路径 + 用户id + 文件名后缀
            this.path = excelUtil.fullName();
        }
    }

    public boolean uploadFile(byte[] file,String filename) throws IOException {

        // 文件校验不通过
        if (!fileCheck(filename) || file.length == 0) {
            return false;
        }
        // 写入文件目录校验
        setPath();
        excelUtil.fileIsNotExistToCreate(path);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(path);
            fos.write(file);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert fos != null;
            fos.close();
        }
        return true;
    }


    /**
    * @Description: 检查文件是否符合要求
    * @Params: [filename]
    * @return: boolean
    * @Author: xusi
    * @Date: 2020/10/23
    */
    private boolean fileCheck(String filename) {
        // 文件不为空
        if (filename == null){
            return false;
        }
        // 文件类型正确
        return filename.endsWith(ExcelUtil.SEVEN_SUFFIX) || filename.endsWith(ExcelUtil.THREE_SUFFIX);

    }


    /**
    * @Description: 下载文件
    * @Params: [filename]
    * @return: boolean
    * @Author: xusi
    * @Date: 2020/10/28
    */
    public boolean downloadFile(String filename) throws IOException {
        setPath();
        File file = new File(path);
        // 要下载的文件存在 且 传入的下载文件路径名正确
        if (!file.exists() && fileCheck(filename)){
            return false;
        }
        // 写入的文件
        FileInputStream fis = null;
        FileOutputStream fos  = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(filename);
            // 输出的文件
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = fis.read(data)) != -1) {
                fos.write(data, 0, len);
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        fis.close();
        fos.close();
        return true;
    }
}
