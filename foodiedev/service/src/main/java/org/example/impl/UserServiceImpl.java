package org.example.impl;


import org.example.mapper.UsersMapper;
import org.example.pojo.Users;
import org.example.pojo.bo.UserBO;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UsersMapper usersMapper;


    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setId("1908017YR51G1XWH");
        user = (Users) usersMapper.select(user).get(0);
        System.out.println(user.getId());
        return user != null;
    }

    @Override
    public Users createUser(UserBO userBO) {
        return null;
    }

    @Override
    public Users queryUserForLogin(String username, String password) {
        return null;
    }
}
