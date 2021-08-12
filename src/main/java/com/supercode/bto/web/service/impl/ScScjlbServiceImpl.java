package com.supercode.bto.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supercode.bto.web.entity.ScCpgxde;
import com.supercode.bto.web.entity.ScScjlb;
import com.supercode.bto.web.mapper.ScScjlbMapper;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IScScjlbService;
import com.supercode.bto.web.utils.restful.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/18 11:53
 */
@Service
public class ScScjlbServiceImpl extends ServiceImpl<ScScjlbMapper, ScScjlb> implements IScScjlbService {

    @Autowired
    ScScjlbMapper scScjlbMapper;

    @Override
    public RestResult insert(ScScjlb scScjlb) {
        try {
            scScjlbMapper.insert(scScjlb);
            return ResultUtil.success("插入成功 "+scScjlb.getDj_jlbh());
        }catch (Exception e){
            return ResultUtil.error(ResultCodeEnum.DATA_EXCEPTION);
        }


    }

    @Override
    public List<ScScjlb> selectByDjbhAndGxbh(String djbh, String gxbh) {
        QueryWrapper<ScScjlb> wrapper = new QueryWrapper<ScScjlb>();
        wrapper.eq("DJ_BH",djbh);
        wrapper.eq("GX_BH",gxbh);
        wrapper.eq("MX_TJBZ","0");
        return scScjlbMapper.selectList(wrapper);
    }
}
