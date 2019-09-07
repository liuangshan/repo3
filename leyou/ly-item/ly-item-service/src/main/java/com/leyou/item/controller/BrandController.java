package com.leyou.item.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    //分页查询品牌
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows, //当前页,一页查几个
            @RequestParam(value = "sortBy",required = false) String  sortBy, //排序方式，根据哪个字段排序
            @RequestParam(value = "desc",defaultValue = "false") boolean desc, //是否降序
            @RequestParam(value = "key",required = false) String key

    )
    {

        PageResult<Brand> result=brandService.queryBrandByPage(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(result);
    }

    //新增品牌
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids) //cids是品牌种类
    {

        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据cid(种类)查询品牌
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid)
    {
        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }
}
