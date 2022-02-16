package com.myq.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
//继承model类，开启AR模式
public class Employee extends Model<Employee> {
    //指定主键，value是数据中主键列名
    //指定type为自增类型
    @TableId
    private Integer id;
    private String empName;
    @TableField(value = "email")
    private String useremail;
    private Integer age;
    private String gender;
    //自动填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;
    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
