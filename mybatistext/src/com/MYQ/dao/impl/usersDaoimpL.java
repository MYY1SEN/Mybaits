package com.MYQ.dao.impl;

import com.MYQ.dao.usersDao;
import com.MYQ.pojo.users;
import com.MYQ.utils.mybatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    /**
     * 添加用户
     * @param users
     */
    @Override
    public void insertUsers(users users) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        sqlSession.insert("com.MYQ.mapper.usersMapper.insertUsers",users);

    }

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

    /**
     * 更新用户
     * @param users
     */
    @Override
    public void usersUpdateById(users users) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        int update = sqlSession.update("com.MYQ.mapper.usersMapper.updateUserById", users);
    }

    /**
     * 删除用户
     * @param userid
     */
    @Override
    public void deleteUserByid(int userid) {
        SqlSession sqlSession = mybatisUtils.getSqlSession();
        int delete = sqlSession.delete("com.MYQ.mapper.usersMapper.deleteUsers", userid);
    }
}
