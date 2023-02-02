package org.example.controller;

import cn.hutool.http.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.example.service.UserService;
import org.example.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassPortController {

    @Autowired
    private UserService userService;

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

}
