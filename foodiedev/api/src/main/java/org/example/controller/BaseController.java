package org.example.controller;

import org.springframework.stereotype.Controller;

/**
 * 静态数据放在这个类中
 */
@Controller
public class BaseController {

    /**
     * 请求商品评价的page size数量
     */
    public static final Integer COMMENT_PAGE_SIZE = 10;
}
