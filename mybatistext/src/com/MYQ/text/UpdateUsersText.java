package com.MYQ.text;

import com.MYQ.pojo.users;
import com.MYQ.service.UsersService;
import com.MYQ.service.impL.UsersServiceImpL;

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
