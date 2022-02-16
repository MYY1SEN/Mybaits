import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class GenertorTest {
    @Test
    public void test(){
        //全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)//是否开启AR模式
              .setAuthor("MYQ")//设置作者
              .setOutputDir(System.getProperty("employee.dir")+"/src/main/java1")//设置输出目录
              .setFileOverride(true)//是否文件覆盖
              .setOpen(false)//生成是否打开资源管理器
              .setIdType(IdType.AUTO)//主键策略
              .setBaseResultMap(true);//是否生成基本的sql映射文件
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)//设置数据源为mysql
                        .setUrl("jdbc:mysql://localhost:3306/mysql1")//设置url
                        .setDriverName("com.mysql.jdbc.Driver")//驱动
                        .setUsername("root")
                        .setPassword("myq1314666");
        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("tb_employee")//指定映射的数据表
                        .setNaming(NamingStrategy.underline_to_camel)//命名下划线转驼峰
                        .setColumnNaming(NamingStrategy.underline_to_camel)//列名规则
                        .setEntityLombokModel(true)//生成lombok注解
                        .setTablePrefix("tb_");//表明前缀
        //包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.myq.generator")//设置根包名
                    .setMapper("mapper")//mapper接口位置
                    .setEntity("pojo")//实体类
                    .setController("controller")//控制器位置
                    .setService("service")//服务位置
                    .setXml("mapper");//映射文件在mapper当中
        //整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.execute();
    }
}
