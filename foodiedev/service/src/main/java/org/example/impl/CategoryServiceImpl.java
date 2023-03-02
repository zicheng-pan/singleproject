package org.example.impl;

import org.example.mapper.CategoryMapper;
import org.example.pojo.Category;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 都是查询所以使用SUPPORTS级别就可以了
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {

        // 使用Example作为查询条件，查询数据库中的数据
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("type", 1);

        return categoryMapper.selectByExample(example);
    }
}
