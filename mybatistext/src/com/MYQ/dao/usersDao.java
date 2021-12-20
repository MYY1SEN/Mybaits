package com.MYQ.dao;

import com.MYQ.pojo.users;

import java.io.IOException;
import java.util.List;

public interface usersDao {
    List<users> selectUserAll() throws IOException;
    users selectUsersById(int userid) throws IOException;
    void insertUsers(users users);
    users selectUsersById2(int userid) throws IOException;
    void usersUpdateById(users users);
    void deleteUserByid(int userid);
}
