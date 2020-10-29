package com.xusi.system.utils;

import com.xusi.system.entity.Word;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-23 11:36
 **/
@Component
public class ExcelUtil {
    public final static String SEVEN_SUFFIX = ".xlsx";
    public final static String THREE_SUFFIX = ".xls";
    public final static String SHEET_NAME = "word";
    public final static String SAVE_ADDRESS = "./static/excel/word";

    @Resource
    private JwtUtil jwtUtil;

    String userId = "";

    // 返回读取的数据
    public List<Word> read() throws IOException {
        List<Word> list = new ArrayList<>();
        // 创建一个输入流 来获取工作表
        FileInputStream fis = new FileInputStream(fullName());

        // get data
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
        // 通过遍历来获取
        sheet.forEach( row -> {
            Word word = new Word();
            word.setName(row.getCell(0).getStringCellValue());
            word.setMean(row.getCell(1).getStringCellValue());
            word.setWordType(row.getCell(2).getStringCellValue());
            word.setPrefix(row.getCell(3).getStringCellValue());
            word.setPostfix(row.getCell(4).getStringCellValue());
            System.out.println("word = " + word);
            list.add(word);
        });

        return list;
    }

    public boolean write(List<Word> list) throws IOException{

        // 1. 创建工作表
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet(SHEET_NAME);

        // 2. 循环写入
        for (int i = 0, j = 0; i < list.size(); i++) {
            SXSSFRow row = sheet.createRow(i);
            j = 0;
            row.createCell(j++).setCellValue(list.get(i).getName());
            row.createCell(j++).setCellValue(list.get(i).getMean());
            row.createCell(j++).setCellValue(list.get(i).getWordType());
            row.createCell(j++).setCellValue(list.get(i).getPrefix());
            row.createCell(j).setCellValue(list.get(i).getPostfix());
        }

        // 3. 创建输出流
        FileOutputStream fos;
        try {
            // 4. 写入工作表
            File file = new File(fullName());
            fileIsNotExistToCreate(fullName());
            fos = new FileOutputStream(fullName());
            workbook.write(fos);
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

        fos.close();
        return true;
    }

    // 文件不存在则创建
    public void fileIsNotExistToCreate(String filename){
        File file = new File(filename);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
    }



    // 获取登录用户id
    private String getUserId(){
        if (userId.equals("")){
            String token = SecurityUtils.getSubject().getPrincipal().toString();
            userId = jwtUtil.getSubject(token);
        }
        return userId;
    }

    public String fullName(){
        return SAVE_ADDRESS + getUserId() + SEVEN_SUFFIX;
    }

}
