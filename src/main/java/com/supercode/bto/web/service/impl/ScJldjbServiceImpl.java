package com.supercode.bto.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supercode.bto.web.entity.ScJldjb;
import com.supercode.bto.web.mapper.ScJldjbMapper;
import com.supercode.bto.web.service.IScJldjbService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/19 22:56
 */
@Service
public class ScJldjbServiceImpl extends ServiceImpl<ScJldjbMapper, ScJldjb> implements IScJldjbService {

    private Logger logger = LoggerFactory.getLogger(ScJldjbServiceImpl.class);
    @Autowired
    private ScJldjbMapper scJldjbMapper;

    @Override
    public String insert(ScJldjb scJldjb) {
        try {
            scJldjbMapper.insert(scJldjb);
            return scJldjb.getDj_bh();
        }catch (Exception e){
            logger.error("scjlb insert djbh {} error {}",scJldjb.getDj_bh(),e);
        }
        return null;

    }

    @Override
    public List<ScJldjb> getByDjbh(String djbh) {
        QueryWrapper<ScJldjb> wrapper = new QueryWrapper<ScJldjb>();
        wrapper.eq("DJ_BH",djbh);
        return scJldjbMapper.selectList(wrapper);
    }

    @Override
    public List<ScJldjb> queryScjlbList(String ddbh, String rybh, String gxbh, String gzdh, String jjdh) {
        QueryWrapper<ScJldjb> wrapper = new QueryWrapper<ScJldjb>();
        if(StringUtils.isNotBlank(ddbh)){
            wrapper.eq("DD_DDDH",ddbh);
        }
        if(StringUtils.isNotBlank(rybh)){
            wrapper.eq("RY_BH",rybh);
        }
        if(StringUtils.isNotBlank(gxbh)){
            wrapper.eq("GX_BH",gxbh);
        }
        if(StringUtils.isNotBlank(gzdh)){
            wrapper.eq("DJ_GZDH",gzdh);
        }
        if(StringUtils.isNotBlank(jjdh)){
            wrapper.eq("DJ_CH",jjdh);
        }

        return scJldjbMapper.selectList(wrapper);
    }

    @Override
    public List<ScJldjb> queryOtherGzdhScjldjbList(String ddbh, String rybh, String gxbh, String gzdh, String jjdh) {
        QueryWrapper<ScJldjb> wrapper = new QueryWrapper<ScJldjb>();
        if(StringUtils.isNotBlank(ddbh)){
            wrapper.eq("DD_DDDH",ddbh);
        }
        if(StringUtils.isNotBlank(rybh)){
            wrapper.eq("RY_BH",rybh);
        }
        if(StringUtils.isNotBlank(gxbh)){
            wrapper.eq("GX_BH",gxbh);
        }
        if(StringUtils.isNotBlank(gzdh)){
            wrapper.ne("DJ_GZDH",gzdh);
        }
        if(StringUtils.isNotBlank(jjdh)){
            wrapper.eq("DJ_CH",jjdh);
        }

        return scJldjbMapper.selectList(wrapper);
    }
}
