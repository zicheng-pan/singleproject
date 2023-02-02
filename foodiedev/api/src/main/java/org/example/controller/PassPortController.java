package org.example.controller;

import cn.hutool.http.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.example.service.UserService;
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
    public int usernameIsExist(@RequestParam String username) {

        if (StringUtils.isBlank(username)) {
            /*
                状态码返回，封装成自己的对象一个状态码 status和data内容
                return HttpStatus.HTTP_INTERNAL_ERROR;
                return 500;
             */

        }

        if (!userService.queryUsernameIsExist(username)) {
            return 500;
        }

        return 200;
    }

}
