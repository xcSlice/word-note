package com.xusi.system.service;

import com.xusi.system.utils.ExcelUtil;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-23 11:47
 **/
@Service
public class FileService {
    String path = ExcelUtil.SAVE_ADDRESS + ExcelUtil.SEVEN_SUFFIX;
    public boolean uploadFile(byte[] file,String filename) throws IOException {

        // 文件校验不通过
        if (!fileCheck(filename) || file.length == 0) {
            return false;
        }
        // 写入文件目录校验
        fileIsExist(path);
        FileOutputStream fos = new FileOutputStream(path);

        try {
            fos.write(file);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        return true;
    }

    /**
    * @Description: 文件目录不存在则创建
    * @Params: [filename]
    * @return: void
    * @Author: xusi
    * @Date: 2020/10/23
    */
    private void fileIsExist(String filename) {
        File file = new File(filename);
        // 父目录不存在则创建
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
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

    public boolean downloadFile(String filename) throws IOException {
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
