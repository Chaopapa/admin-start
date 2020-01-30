package com.le.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName MyMetaObjectHandler
 * @Author lz
 * @Description 填充器
 * @Date 2018/9/30 10:03
 * @Version V1.0
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.debug("start insert fill ....");
        Date date = new Date();

        Object createDate = getFieldValByName("createDate", metaObject);
        if (createDate == null) {
            setFieldValByName("createDate", date, metaObject);
        }

        Object modifyDate = getFieldValByName("modifyDate", metaObject);
        if (modifyDate == null) {
            setFieldValByName("modifyDate", date, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.debug("start update fill ....");
        this.setFieldValByName("modifyDate", new Date(), metaObject);
    }
}
