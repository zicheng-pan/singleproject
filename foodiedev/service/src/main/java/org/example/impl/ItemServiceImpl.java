package org.example.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.enums.CommentLevel;
import org.example.mapper.*;
import org.example.pojo.*;
import org.example.pojo.vo.CommentLevelCountsVO;
import org.example.pojo.vo.ItemCommentVO;
import org.example.service.ItemService;
import org.example.utils.DesensitizationUtil;
import org.example.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example itemsImgExp = new Example(ItemsImg.class);
        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(itemsImgExp);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example itemSpecExp = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemSpecExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(itemSpecExp);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsParam queryItemParam(String itemId) {
        Example itemParamExp = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemParamExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(itemParamExp);
    }

    @Override
    public PagedGridResult queryItemComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("itemId", itemId);
        paramsMap.put("level", level);

        /**
         * 查询之前需要封装
         * page：第几页
         * pageSize：每页显示个数
         */
        PageHelper.startPage(page, pageSize);

        List<ItemCommentVO> itemCommentVOS = itemsMapperCustom.queryItemComments(paramsMap);

        /**
         * 添加对用户名匿名化
         */
        for (ItemCommentVO vo : itemCommentVOS) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        /**
         * 查询之后需要将数据封装到PagedGridResult.java中
         */
        PagedGridResult result = setterPagedGrid(itemCommentVOS, page);
        return result;
    }

    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        PagedGridResult result = new PagedGridResult();
        result.setPage(page);
        result.setRows(list);
        /**
         * 这里定义反了
         * 这里的list其实是Page对象，是mybatis分页的对象，Page继承的list，所以我们这里可以当作list来使用
         */
        result.setTotal((int) pageInfo.getPages());
        result.setRecords(pageInfo.getTotal());
        return result;
    }


    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = queryCommentCountsByLevel(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = queryCommentCountsByLevel(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = queryCommentCountsByLevel(itemId, CommentLevel.BAD.type);

        Integer totalCounts = goodCounts + normalCounts + badCounts;
        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
        commentLevelCountsVO.setBadCounts(badCounts);
        commentLevelCountsVO.setNormalCounts(normalCounts);
        commentLevelCountsVO.setGoodCounts(goodCounts);
        commentLevelCountsVO.setTotalCounts(totalCounts);
        return commentLevelCountsVO;
    }

    private Integer queryCommentCountsByLevel(String itemId, Integer level) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        itemsComments.setCommentLevel(level);
        return itemsCommentsMapper.selectCount(itemsComments);
    }
}
