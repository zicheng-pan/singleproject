package org.example.service;

import org.example.pojo.Category;
import org.example.pojo.vo.CategoryVO;
import org.example.pojo.vo.NewItemsVO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有一级分类
     *
     * @return
     */
    public List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id查询子分类信息
     * @param rootCatId
     * @return
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);

    @Transactional(propagation = Propagation.SUPPORTS)
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
