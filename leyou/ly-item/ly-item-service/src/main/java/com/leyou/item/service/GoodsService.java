package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    public ResponseEntity<Spu> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页

        PageHelper.startPage(page,rows);
        //过滤

        Example example=new Example((Spu.class));
        Example.Criteria criteria=example.createCriteria();

        //搜索字段过滤
        if(StringUtils.isNoneBlank(key))
        {
            criteria.andLike("title","%"+ key +"%");
        }

        //上下架过滤
        if(saleable!=null)
        {
            criteria.andEqualTo("saleable",saleable);
        }

        //默认排序
        example.setOrderByClause("last_update_time DESC");

        //查询
        List<Spu> spus=spuMapper.selectByExample(example);

        //判断
        if(CollectionUtils.isEmpty(spus))
        {
            throw new LyException((ExceptionEnum.GOODS_NOT_EXIST));
        }

        //解析分类和品牌名称
        loadCategoryAndBrandName(spus);
        //解析分页结果

        PageInfo<Spu> info=new PageInfo<>(spus);//将分类出来的结果放到pageInfi分页助手了里
        return new PageResult<>(info.getTotal(),spus);
    }

    private void loadCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu :spus)
        {
            //处理分类名称
                List<String> names=categoryService.queryByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names,"/"));
            //处理品牌名称
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());
        }
    }

    @Transactional
    public void saveGoods(Spu spu) {
        //新增suo
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);

       int count spuMapper.insert(spu);
        if(count!=1)
        {
            throw new LyException(ExceptionEnum.GOODS_NOT_CREATE);
        }
        //新增detail

        SpuDetail detail = spu.getSupDetail();
        detail.setSpuId(spu.getId());
        spuDetailMapper.insert(detail);
        //新增sku

        List<Stock> stockList=new ArrayList<>();
        //新增sku
        List<Sku> skus = spu.getSkus();
        for (Sku sku:skus)
        {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSupId(spu.getId());

            count=skuMapper.insert(sku);
            if (count!=1)
            {
                throw new LyException(ExceptionEnum.GOODS_NOT_CREATE);
            }

            //新增库存
            Stock stock=new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());

            stockList.add(stock);
        }

        //批量新增
        int temp= stockMapper.insertList(stockList);
        if (temp!=1)
        {
            throw new LyException(ExceptionEnum.GOODS_NOT_CREATE);
        }


    }
}
