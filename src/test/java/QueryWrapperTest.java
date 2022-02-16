import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.List;

public class QueryWrapperTest {
    private ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    private EmployeeMapper employeeMapper =
            applicationContext.getBean("employeeMapper",EmployeeMapper.class);
    @Test
    public void testselectlist(){
        QueryWrapper<Employee> qw = new QueryWrapper<>();
        //查询性别为女且年龄小于35
        qw.like("gender", "f").le("age",35);
        List<Employee> employees = employeeMapper.selectList(qw);
        System.out.println(employees);
    }
    @Test
    public void testPage(){
        Page page = new Page(1,2);
        IPage<Employee> emps = employeeMapper.selectPage(page, new QueryWrapper<Employee>
                ().like("emp_name", "Black"));
        List<Employee> r = emps.getRecords();
        System.out.println(r);
    }
}
