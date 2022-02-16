package com.myq.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

public class MyMetaObjectHandle implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //自动填充逻辑
        //通过MetaObject获取需要被填充字段的值 updatetime
        Object fieldvalue = this.getFieldValByName("updatetime", metaObject);
        if (fieldvalue == null){
            //获取当前时间
            Date now = new Date();
            //赋值
            this.setFieldValByName("updatetime", now, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //自动填充逻辑
        //通过MetaObject获取需要被填充字段的值 updatetime
        Object fieldvalue = this.getFieldValByName("updatetime", metaObject);
        if (fieldvalue == null){
            //获取当前时间
            Date now = new Date();
            //赋值
            this.setFieldValByName("updatetime", now, metaObject);
        }
    }
}
