package com.xusi.system.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @program: IntelliJ IDEA
 * @description: mybatis plus 配置类
 * @author: xusi
 * @create:2020-09-06 11:00
 **/
@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
//        配置 新增 自动填充

        this.strictInsertFill(metaObject,"gmtCreate",LocalDateTime.class,LocalDateTime.now());
        this.strictUpdateFill(metaObject,"gmtModified",LocalDateTime.class,LocalDateTime.now());
        //mybatis plus 会自动写入
//        this.strictUpdateFill(metaObject,"id", Random.class,new Random().nextInt()*100);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//      配置 更新 自动填充
        this.strictUpdateFill(metaObject,"gmt_modified",LocalDateTime.class,LocalDateTime.now());
    }
}
