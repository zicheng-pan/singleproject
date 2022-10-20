package org.example.mapper;


import org.apache.ibatis.annotations.Param;
import org.example.my.mapper.MyMapper;
import org.example.pojo.ItemsComments;
import org.example.pojo.vo.MyCommentVO;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public void saveComments(Map<String, Object> map);

    public List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}