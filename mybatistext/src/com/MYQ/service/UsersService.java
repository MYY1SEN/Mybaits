package com.MYQ.service;

import com.MYQ.pojo.users;

public interface UsersService {
    void addUsers(users users);
    users preUpdateUsers(int userid);
    void modifyUsers(users users);
    void cutusers(int userid);
}
