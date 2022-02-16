package com.MYQ.text;

import com.MYQ.service.UsersService;
import com.MYQ.service.impL.UsersServiceImpL;

public class DeleteUsers {
    public static void main(String[] args) {
        UsersService usersService = new UsersServiceImpL();
        usersService.cutusers(1);
    }
}
