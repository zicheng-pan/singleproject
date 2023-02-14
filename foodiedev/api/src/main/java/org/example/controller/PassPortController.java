package org.example.controller;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.example.pojo.Users;
import org.example.pojo.bo.UserBO;
import org.example.service.UserService;
import org.example.utils.CookieUtils;
import org.example.utils.JSONResult;
import org.example.utils.JsonUtils;
import org.example.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassPortController {

    /*
        在log4j.properties 配置文件中配置 INFO级别，默认debug就不可以打印在这个日志中
        logger.debug();
        logger.info();
        logger.warn();
        logger.error();
        常用得looger日志级别
     */
    final static Logger logger = LoggerFactory.getLogger(PassPortController.class);

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
    public JSONResult regist(@RequestBody UserBO userBO, HttpServletRequest request,
                             HttpServletResponse response) {

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
        Users userResult = userService.createUser(userBO);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        return JSONResult.ok();
    }


    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBO userBO, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {

        String userName = userBO.getUsername();
        String password = userBO.getPassword();

        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password))
            return JSONResult.errorMsg("用户名或密码不能为空");

        Users userResult = userService.queryUserForLogin(userName, MD5Utils.getMD5Str(password));
        if (userResult == null) {
            return JSONResult.errorMsg("用户名或密码错误");
        }
        userResult = setNullProperty(userResult);

        // 向浏览器设置cookie, true表示加密
        /*
        '%7B%22id%22%3A%22230214G8WMPPR680%22%2C%22username%22%3A%22xiaoli%22%2C%22password%22%3Anull%2C%22nickname%22%3A%22xiaoli%22%2C%22realname%22%3Anull%2C%22face%22%3A%22https%3A%2F%2Fimg1.baidu.com%2Fit%2Fu%3D3486651663%2C3991438881%26fm%3D253%26app%3D138%26size%3Dw931%26n%3D0%26f%3DJPEG%26fmt%3Dauto%3Fsec%3D1675443600%26t%3D881bbc6a6cff601b189d3a0a2f8fde1b%22%2C%22mobile%22%3Anull%2C%22email%22%3Anull%2C%22sex%22%3A2%2C%22birthday%22%3Anull%2C%22createdTime%22%3Anull%2C%22updatedTime%22%3Anull%7D'
         */
        // 页面通过 decodeURIComponent(app.getCookie("user")) 解析加密cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        return JSONResult.ok(userResult);
    }

    private Users setNullProperty(Users userResult) {
        userResult.setBirthday(null);
        userResult.setEmail(null);
        userResult.setMobile(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setPassword(null);
        return userResult;
    }

}
