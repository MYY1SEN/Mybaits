package com.MYQ.text;

import com.MYQ.dao.impl.usersDaoimpL;
import com.MYQ.dao.usersDao;
import com.MYQ.pojo.users;

import java.io.IOException;

public class text {
    public static void main(String[] args) throws IOException {
        usersDao usersDao = new usersDaoimpL();
//        List<users> list = usersDao.selectUserAll();
//        for (users users:list){
//            System.out.println(users.getUserid()+"\t"+users.getUsername()+"\t"+users.getUsersex());
//        }
        users users = usersDao.selectUsersById(1);
        System.out.println(users.getUserid()+"\t"+users.getUsername()
                +"\t"+users.getUsersex());

    }
}
