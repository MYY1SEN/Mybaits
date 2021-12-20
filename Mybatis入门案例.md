# Mybatis 入门案例

# 实现查询所有数据

## 创建properties文件

```java
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/myq
jdbc.username=root
jdbc.password=myq1314666
```

## 创建mybatis配置文件（mybatis-cfg.xml）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--    引入properties文件-->
    <properties resource="db.properties"></properties>
    <!--    环境配置-->
    <environments default="development">
        <environment id="development">
            <!--            配置事务-->
            <transactionManager type="JDBC"></transactionManager>
            <!--            配置数据源-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <!--    引入映射配置文件-->
    <mappers>
        <!--    使用相对路径引用-->
       <mapper resource="com/MYQ/mapper/usersMapper.xml"/>
    </mappers>
</configuration>
```

## 创建数据库表

```sql
create table users
(
    userid   int auto_increment
        primary key,
    username varchar(20) null,
    usersex  varchar(10) null
);
```

## 创建pojo类,可由数据库自动生成

```java
package com.MYQ.pojo;


public class users {

  private int userid;
  private String username;
  private String usersex;


  public long getUserid() {
    return userid;
  }

  public void setUserid(int userid) {
    this.userid = userid;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getUsersex() {
    return usersex;
  }

  public void setUsersex(String usersex) {
    this.usersex = usersex;
  }

}
```

## 创建mapper文件

```xaml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--suppress ALL -->
<mapper namespace="com.MYQ.mapper.usersMapper">
<!--查询所有用户-->
    <select id="selectUserAll" resultType="com.MYQ.pojo.users">
            select * from users
   </select>
</mapper>
```

## 创建DAO接口 userDao

```java
public interface usersDao {
    List<users> selectUserAll() throws IOException;
}
```

## 创建接口实现类impl

```java
/**
 * 查询所有用户
 */
public class usersDaoimpL implements usersDao  {
    @Override
    public List<users> selectUserAll() throws IOException {
        //创建SqlSessionFactory对象
        InputStream inputStream = Resources.getResourceAsStream("mybatis-cfg.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //通过SqlSession对象的API完成对数据库的操作
        List<users> list = sqlSession.selectList("com.MYQ.mapper.usersMapper.selectUserAll");
        //关闭对象
        sqlSession.close();
        return list;
    }
}
```

## 最后创建测试类

```java
public class text {
    public static void main(String[] args) throws IOException {
        usersDao usersDao = new usersDaoimpL();
        List<users> list = usersDao.selectUserAll();
        for (users users:list){
            System.out.println(users.getUserid()+"\t"+users.getUsername()+"\t"+users.getUsersex());
        }
    }
}
```

# 根据用户ID查询数据

## 修改mapper文件

```xml
 <!--    根据用户id查询用户-->
    <select id="selectUsersById" parameterType="int" resultType="com.MYQ.pojo.users">
        select *
        from users
        where userid = #{suibian}
    </select>
```

## 添加接口

```java
users selectUsersById(int userid) throws IOException;
```

## 添加接口实现类

```java
/**
     * 根据用户ID查询用户
     * @param userid
     * @return
     * @throws IOException
     */
    @Override
    public users selectUsersById(int userid) throws IOException {
        //创建SqlSessionFactory对象
        InputStream inputStream = Resources.getResourceAsStream("mybatis-cfg.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        users users = sqlSession.selectOne("com.MYQ.mapper.usersMapper.selectUsersById",userid);
        sqlSession.close();
        return users;
    }
```

## 测试类

```java
users users = usersDao.selectUsersById(1);
        System.out.println(users.getUserid()+"\t"+users.getUsername()
                +"\t"+users.getUsersex());
```

# 创建Mybatis的工具类

- 因为mybatis框架不需要频繁启动，SqlSession是单例模式，会一直存在，用单例模式的SqlSessionFactory去获取SqlSession。
- 通过Threadlocal来缓存SqlSession，来保证在一个线程中的数据库都会拿到相同的SqlSession。

```java
public class mybatisUtils {
    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();
   private static SqlSessionFactory sqlSessionFactory= null;


    static {
       //创建sqlSessionFactory
       InputStream is  = null;
       try {
            is = Resources.getResourceAsStream("mybatis-cfg.xml");
       }catch (IOException e){
           e.printStackTrace();
       }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
   }
   //获取sqlSession
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = threadLocal.get();
        if (sqlSession == null){
            sqlSession = sqlSessionFactory.openSession();
            threadLocal.set(sqlSession);
        }
        return sqlSession;
    }
    //关闭对象
    public static void closeSqlSession(){
        SqlSession sqlSession = threadLocal.get();
        if (sqlSession != null){
            sqlSession.close();
            threadLocal.set(null);
        }
    }
}
```

# 添加用户操作

## 添加mapper文件

```xml
<!--    添加用户-->
    <insert id="insertUsers">
        insert into users value (default,#{username},#{usersex})
    </insert>
```

## 添加Dao接口

```java
void insertUsers(users users);
```

## 添加接口实现类

```java
/**
     * 添加用户
     * @param users
     */
    @Override
    public void insertUsers(users users) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        sqlSession.insert("com.MYQ.mapper.usersMapper.insertUsers",users);

    }
```

- ## 因为mybatis事务提交是默认手动提交,所以要创建服务类来进行提交

## 创建处理事务服务类接口

```java
public interface UsersService {
    void addUsers(users users);
}
```

## 创建服务类接口实现类

```java
public class UsersServiceImpL implements UsersService {
    /**
     * 添加用户
     * @param users
     */
    @Override
    public void addUsers(users users) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        try {
            usersDao usersDao  = new usersDaoimpL();
            usersDao.insertUsers(users);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            mybatisUtils.closeSqlSession();
        }
    }
}
```

- ## 是在服务类中进行的添加操作，服务类执行持久层DAO的添加方法

## 创建测试类

```java
public class AddUserText {
    public static void main(String[] args) {
        UsersService usersService = new UsersServiceImpL();
        users user = new users();
        user.setUsername("马烨乾");
        user.setUsersex("male");
        usersService.addUsers(user);
    }
}
```

## 查看运行结果

![image-20211220184221778](C:\Users\10715\AppData\Roaming\Typora\typora-user-images\image-20211220184221778.png)

- ## 添加成功！

# 更新用户操作

## 修改mapper文件

```xml
 <!--    更新用户-->
    <update id="updateUserById">
        update users
        set username = #{username},
            usersex= #{usersex}
        where userid = #{userid}
    </update>
```

- ## 首先得进行预更新，先选择需要更新的user的userid

## 预更新操作DAO接口

```java
users selectUsersById2(int userid) throws IOException;
```

## 预更新操作DAO实现类

```java
/**
     * 预更新用户查询
     * @param userid
     * @return
     * @throws IOException
     */
    @Override
    public users selectUsersById2(int userid) throws IOException {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        users u = sqlSession.selectOne("com.MYQ.mapper.usersMapper.selectUserById2",userid);
        return u;
    }
```

## 创建预更新事务接口

```java
users preUpdateUsers(int userid);
```

## 创建事务接口实现类

```java
@Override
    public users preUpdateUsers(int userid) {
        users users = null;
        try {
            SqlSession sqlSession = mybatisUtils.getSqlSession();
            usersDao usersDao = new usersDaoimpL();
            users = usersDao.selectUsersById2(userid);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mybatisUtils.closeSqlSession();
        }
        return users;
    }
```



## 更新DAO接口

```java
void usersUpdateById(users users);
```

## 更新DAO接口实现类

```java
/**
     * 更新用户
     * @param users
     */
    @Override
    public void usersUpdateById(users users) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        int update = sqlSession.update("com.MYQ.mapper.usersMapper.updateUserById", users);
    }

```

## 创建更新事务服务接口

```java
void modifyUsers(users users);
```

## 创建更新事务服务接口实现类

```java
 @Override
    public void modifyUsers(users users) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        try {
            usersDao usersDao = new usersDaoimpL();
            usersDao.usersUpdateById(users);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            mybatisUtils.closeSqlSession();
        }
    }
```

## 创建测试类

```java
public class UpdateUsersText {
    public static void main(String[] args) {
        UsersService usersService = new UsersServiceImpL();
        users users = usersService.preUpdateUsers(3);
        System.out.println(users.getUsername());
        users.setUsername("乾烨马");
        users.setUsersex("MALE");
        usersService.modifyUsers(users);
    }
}
```

## 运行结果

![image-20211220193950016](C:\Users\10715\AppData\Roaming\Typora\typora-user-images\image-20211220193950016.png)

# 删除用户操作

## 添加mapper文件

```xml
<!--    删除用户-->
    <delete id="deleteUsers" >
        delete from users where userid = #{userid}
    </delete>
```

## 添加DAO持久层接口

```java
void deleteUserByid(int userid);
```

## 添加接口实现类

```java
 /**
     * 删除用户
     * @param userid
     */
    @Override
    public void deleteUserByid(int userid) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        int delete = sqlSession.delete("com.MYQ.mapper.usersMapper.deleteUsers", userid);
    }
```

## 添加服务层DAO接口

```java
void cutusers(int userid);
```

## 添加服务处DAO接口实现类

```java
@Override
    public void cutusers(int userid) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        try {
            usersDao usersDao = new usersDaoimpL();
            usersDao.deleteUserByid(userid);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }
```

## 测试类

```java
public class DeleteUsers {
    public static void main(String[] args) {
        UsersService usersService = new UsersServiceImpL();
        usersService.cutusers(1);
    }
```

