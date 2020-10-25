//package com.xusi.system.config;
//
//import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * @program: IntelliJ IDEA
// * @description:
// * @author: xusi
// * @create:2020-10-25 18:08
// **/
//@Slf4j
//@Component
//public class CustomIdGenerator implements IdentifierGenerator {
//
//    private final AtomicLong al = new AtomicLong(17);
//
//    @Override
//    public Long nextId(Object entity) {
//        //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
//        String bizKey = entity.getClass().getName();
//        log.info("bizKey:{}", bizKey);
//        MetaObject metaObject = SystemMetaObject.forObject(entity);
//        String name = (String) metaObject.getValue("username");
//        final long id = al.getAndAdd(1*17);
////        final long id = al.getAndAdd(1);
//        log.info("为{}生成主键值->:{}", name, id);
//        return id;
//    }
//}