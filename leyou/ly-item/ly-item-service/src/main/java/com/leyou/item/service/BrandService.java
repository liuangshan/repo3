package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    //分页查询
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, boolean desc, String key) {
        //分页
//page是当前页 ，rows是每页大小
        PageHelper.startPage(page,rows);



        //过滤
//        WHERE 'name' LIKE  "%x%" RE letter == 'x' ORDER BY id DESC
        Example example= new Example(Brand.class);
        //获取Brand.class的字节码
        if(StringUtils.isNoneBlank(key))
        {
            //过滤条件
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter" , key.toUpperCase());
            //相当于WHERE 'name' LIKE  "%x%" RE letter == 'x' ORDER BY id DESC
        }


        //排序
        if(StringUtils.isNoneBlank(sortBy))
        {
            String orderByClause=sortBy + (desc?"DESC" : "ASC");
            example.setOrderByClause(orderByClause);

        }

        //查询
        List<Brand> list=brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list))
        {
           throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<Brand> info=new PageInfo<>(list);//把查出来的list放进PageInfo（分页助手）里,info里有总条数，总页数等等一系列参数
        return new PageResult<>(info.getTotal(),list);
    }

    //新增品牌
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {

        brand.setId(null);//先设定为空让它自增
       int count= brandMapper.insert(brand);
       if(count!=1)
       {
           throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
       }

       //新增中间表
        for (Long cid : cids)
        {
            brandMapper.insertCategoryBrand(cid,brand.getId());
            if(count!=1)
            {
                throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }
    }
    public Brand queryById(Long id)
    {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if(brand==null)
        {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);

        }
        return brand;
    }

    // SELECT b.* FROM tb_brand b INNER JOIN tb_category_brand cb ON b.id=cb.brand_id WHERE cb.category_id=#{cid}
    public Object queryBrandByCid(Long cid) {
        List<Brand> list = brandMapper.queryByCategoryId(cid);
        if(CollectionUtils.isEmpty(list))
        {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);

        }
        return list;
    }
}
