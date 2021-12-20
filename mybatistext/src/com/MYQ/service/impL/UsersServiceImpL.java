package com.MYQ.service.impL;

import com.MYQ.dao.impl.usersDaoimpL;
import com.MYQ.dao.usersDao;
import com.MYQ.pojo.users;
import com.MYQ.service.UsersService;
import com.MYQ.utils.mybatisUtils;
import org.apache.ibatis.session.SqlSession;

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
}
