package com.myq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myq.pojo.Employee;

public interface EmployeeMapper extends BaseMapper<Employee> {
    void deleteAll();
}
