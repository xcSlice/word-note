package com.xusi.system.service;

import com.xusi.system.entity.Word;
import com.xusi.system.utils.ExcelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description: excel读写
 * @author: xusi
 * @create:2020-10-23 17:29
 **/
@Service
public class ExcelService {

    @Resource
    private ExcelUtil excelUtil;

    /**
    * @Description: 返回excel文件中的所有数据
    * @Params: []
    * @return: java.util.List<com.xusi.system.entity.Word>
    * @Author: xusi
    * @Date: 2020/10/24
    */
    public List<Word> read() throws IOException {
        return excelUtil.read();
    }


    /**
    * @Description: 传入一个list写入excel文件中,返回执行结果
    * @Params: [list]
    * @return: boolean
    * @Author: xusi
    * @Date: 2020/10/24
    */
    public boolean write(List<Word> list) throws IOException {
        return excelUtil.write(list);
    }
}
