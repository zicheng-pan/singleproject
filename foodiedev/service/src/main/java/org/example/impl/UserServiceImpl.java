package org.example.impl;


import org.example.enums.Sex;
import org.example.mapper.UsersMapper;
import org.example.pojo.Users;
import org.example.pojo.bo.UserBO;
import org.example.service.UserService;
import org.example.utils.DateUtil;
import org.example.utils.MD5Utils;
import org.n3r.idworker.Sid;
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

    @Autowired
    public Sid sid;

    private static final String USER_FACE = "https://img1.baidu.com/it/u=3486651663,3991438881&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1675443600&t=881bbc6a6cff601b189d3a0a2f8fde1b";

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {

        // 使用Example作为查询条件，查询数据库中的数据
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username", username);

        Users result = usersMapper.selectOneByExample(userExample);

        return result != null;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users createUser(UserBO userBO) {
        // users 对象是对应数据库中的对象
        // userBO对象是对应前端传递过来的数据对象，做一次转换
        Users user = new Users();

        user.setId(sid.nextShort());
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 默认用户昵称和用户名相同
        user.setNickname(userBO.getUsername());
        // 设置头像
        user.setFace(USER_FACE);
        // 设置生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 设置性别 默认为保密
        user.setSex(Sex.secret.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        usersMapper.insert(user);

        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserForLogin(String username, String password) {

        // 使用Example作为查询条件，查询数据库中的数据
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username", username);
        // 属性名称需要和Users类中得属性名对应而不是和数据库名称对齐，因为通过框架生成后，数据库得字段名有可能是驼峰命名
        userCriteria.andEqualTo("password", password);

        return usersMapper.selectOneByExample(userExample);
    }
}
