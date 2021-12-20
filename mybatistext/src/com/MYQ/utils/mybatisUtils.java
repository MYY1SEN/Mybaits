package com.MYQ.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

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
