import com.myq.pojo.Employee;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class AppTest {
    private ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    @Test
    //测试环境
    public void testEnv() throws SQLException {
        //从spring容器获取datasource
        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
        //获取链接
        Connection con = dataSource.getConnection();
        System.out.println(con);
    }
    @Test
    public void testtime(){
        Employee employee = new Employee();
        employee.setEmpName("xinla");
        employee.setUseremail("xinla@myq.com");
        employee.setGender("m");
        employee.insert();
    }
}
