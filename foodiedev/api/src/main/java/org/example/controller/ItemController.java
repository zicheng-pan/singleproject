package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.example.pojo.Items;
import org.example.pojo.ItemsImg;
import org.example.pojo.ItemsParam;
import org.example.pojo.ItemsSpec;
import org.example.pojo.vo.CommentLevelCountsVO;
import org.example.pojo.vo.ItemInfoVO;
import org.example.service.ItemService;
import org.example.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品详情页", tags = {"用于查看商品详情"})
@RestController
@RequestMapping("items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult subCat(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId
    ) {
        if (StringUtils.isBlank(itemId))
            return JSONResult.errorMsg(null);

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        ItemInfoVO infoVO = new ItemInfoVO();
        infoVO.setItem(item);
        infoVO.setItemParams(itemsParam);
        infoVO.setItemImgList(itemsImgs);
        infoVO.setItemSpecList(itemsSpecs);
        return JSONResult.ok(infoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId
    ) {

        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }

        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return JSONResult.ok(commentLevelCountsVO);
    }
}
