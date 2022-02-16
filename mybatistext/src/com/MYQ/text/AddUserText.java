package com.MYQ.text;

import com.MYQ.pojo.users;
import com.MYQ.service.UsersService;
import com.MYQ.service.impL.UsersServiceImpL;

public class AddUserText {
    public static void main(String[] args) {
        UsersService usersService = new UsersServiceImpL();
        users user = new users();
        user.setUsername("马烨乾");
        user.setUsersex("male");
        usersService.addUsers(user);
    }
}
