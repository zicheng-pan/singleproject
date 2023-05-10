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
import org.example.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品详情页", tags = {"用于查看商品详情"})
@RestController
@RequestMapping("items")
public class ItemController extends BaseController {

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


    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam(required = false)  Integer level,
            @ApiParam(name = "page", value = "查询评价的第几页", required = false)
            @RequestParam(required = false) Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(required = false)  Integer pageSize
    ) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult result = itemService.queryItemComments(itemId, level, page, pageSize);
        return JSONResult.ok(result);
    }
}
