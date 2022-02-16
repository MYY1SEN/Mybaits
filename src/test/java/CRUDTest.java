import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myq.mapper.EmployeeMapper;
import com.myq.pojo.Employee;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDTest {
    private ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    private EmployeeMapper employeeMapper =
            applicationContext.getBean("employeeMapper",EmployeeMapper.class);
    @Test
    public void testEnv() throws SQLException {
        //创建实体类
        Employee employee = new Employee();
        //设置相关属性
        employee.setEmpName("myq");
        employee.setUseremail("myq@myq.com");
        employee.setGender("m");
        employee.setAge(21);
        employeeMapper.insert(employee);
        //获取主键
        System.out.println(employee.getId());
    }
    @Test
    public void testupdate(){
        Employee employee = new Employee();
        //设置需要更新的属性
        employee.setEmpName("YQM");
        employee.setUseremail("yqm@myq.com");
        employee.setId(6);
        employeeMapper.updateById(employee);
    }
    @Test
    public void testpage(){
        //分页数据
        Page page = new Page(0,2);
        IPage<Employee> res = employeeMapper.selectPage(page, null);
        //从分页获取结果集
        List<Employee> emps = res.getRecords();
        System.out.println(emps);
    }
}
