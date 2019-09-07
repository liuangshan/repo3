package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryListByPid(Long pid) {

        Category t=new Category();
        t.setParentId(pid);

        List<Category> list= categoryMapper.select(t);
        if(CollectionUtils.isEmpty(list))//这个是判断list是否为空
        {
            throw new  LyException(ExceptionEnum.CATEGORY_NOY_FOUND);
        }
        return list;
    }

    public List<Category> queryByIds(List<Long> ids)
    {
        List<Category> list = categoryMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(list))
        {

            throw new LyException(ExceptionEnum.CATEGORY_NOY_FOUND);
        }
        return  list;
    }
}
