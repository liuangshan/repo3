package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParaMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper GroupMapper;
    private SpecParaMapper ParaMapper;
    public List<SpecGroup> queryGroupByCid(Long cid) {

        SpecGroup group=new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> list=GroupMapper.select(group);
        if(CollectionUtils.isEmpty(list))
        {
            throw  new LyException(ExceptionEnum.GROUP_NOT_FOUND);
        }
return list;
    }

    public List<SpecParam> queryParamList(Long gid,Long cid,Boolean searching) {

        SpecParam param=new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list=ParaMapper.select(param);
        if(CollectionUtils.isEmpty(list))
        {
            throw  new LyException(ExceptionEnum.PARA_NOT_FOUND);
        }
        return list;
    }
}
