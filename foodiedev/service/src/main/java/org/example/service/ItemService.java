package org.example.service;

import org.example.enums.CommentLevel;
import org.example.pojo.Items;
import org.example.pojo.ItemsImg;
import org.example.pojo.ItemsParam;
import org.example.pojo.ItemsSpec;
import org.example.pojo.vo.CommentLevelCountsVO;
import org.example.pojo.vo.ItemCommentVO;
import org.example.utils.PagedGridResult;

import java.util.List;
import java.util.Map;

public interface ItemService {

    /**
     * 根据商品ID查询详情
     *
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     *
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     *
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     *
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询全部评论
     *
     * @param itemId
     * @return
     */
    public PagedGridResult queryItemComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 根据商品id查询评价等级数量
     *
     * @param itemId
     * @return
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);
}
