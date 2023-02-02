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

        // 使用Example作为查询条件，查询数据库中的数据
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username", username);

        Users result = usersMapper.selectOneByExample(userExample);

        return result != null;

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
