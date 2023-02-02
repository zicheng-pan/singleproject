package org.example.controller;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.example.pojo.bo.UserBO;
import org.example.service.UserService;
import org.example.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassPortController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username) {

        if (StringUtils.isBlank(username)) {
            /*
                状态码返回，封装成自己的对象一个状态码 status和data内容
                return HttpStatus.HTTP_INTERNAL_ERROR;
                return 500;
             */
            return JSONResult.errorMsg("用户名不能为空");
        }

        if (userService.queryUsernameIsExist(username)) {
            return JSONResult.errorMsg("用户名已经存在");
        }

        return JSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBO userBO) {

        String userName = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPWD = userBO.getConfirmPassword();

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPWD))
            return JSONResult.errorMsg("用户名或密码不能为空");


        // 1. 查询用户是否存在
        boolean isExist = userService.queryUsernameIsExist(userName);
        if (isExist)
            return JSONResult.errorMsg("用户名已存在");

        // 2. 密码长度不能少于6位
        if (password.length() < 6)
            return JSONResult.errorMsg("密码长度不能少于6位");

        // 3. 判断两次密码是否一致
        if (!password.equals(confirmPWD))
            return JSONResult.errorMsg("两次密码输入不一致");

        // 4. 实现注册
        userService.createUser(userBO);

        return JSONResult.ok();
    }

}
