package com.supercode.bto.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supercode.bto.web.entity.ScCpgxde;
import com.supercode.bto.web.entity.ScJldjb;
import com.supercode.bto.web.mapper.ScCpgxdeMapper;
import com.supercode.bto.web.mapper.ScJldjbMapper;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IScCpgxdeService;
import com.supercode.bto.web.utils.restful.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/19 22:58
 */
@Service
public class ScCpgxdeServiceImpl extends ServiceImpl<ScCpgxdeMapper, ScCpgxde> implements IScCpgxdeService {
    @Autowired
    private ScCpgxdeMapper scCpgxdeMapper;

    @Override
    public RestResult insert(ScCpgxde scCpgxde) {
        try {
            scCpgxdeMapper.insert(scCpgxde);
            return ResultUtil.success("插入成功 "+scCpgxde.getDd_dddh());
        }catch (Exception e){
            return ResultUtil.error(ResultCodeEnum.DATA_EXCEPTION);
        }

    }

    @Override
    public List<ScCpgxde> selectByDdbhAndGxbh(String ddbh,String cpbh, String gxbh) {
        QueryWrapper<ScCpgxde> wrapper = new QueryWrapper<ScCpgxde>();
        if(StringUtils.isNotBlank(ddbh)){
            wrapper.eq("DD_DDDH",ddbh);
        }
        if(StringUtils.isNotBlank(cpbh)){
            wrapper.eq("CP_CPBH",cpbh);
        }
        if(StringUtils.isNotBlank(gxbh)){
            wrapper.eq("GX_BH",gxbh);
        }
        return scCpgxdeMapper.selectList(wrapper);
    }

    @Override
    public List<ScCpgxde> selectByDdbhAndYl1(String ddbh, String cpbh, String gxbh) {
        QueryWrapper<ScCpgxde> wrapper = new QueryWrapper<ScCpgxde>();
        if(StringUtils.isNotBlank(ddbh)){
            wrapper.eq("DD_DDDH",ddbh);
        }
        if(StringUtils.isNotBlank(cpbh)){
            wrapper.eq("CP_CPBH",cpbh);
        }
        if(StringUtils.isNotBlank(gxbh)){
            wrapper.eq("YL1",gxbh);
        }

        return scCpgxdeMapper.selectList(wrapper);
    }
}
