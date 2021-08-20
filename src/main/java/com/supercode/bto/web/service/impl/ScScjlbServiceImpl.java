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
 * @date 2021/7/18 11:53
 */
@Service
public class ScScjlbServiceImpl extends ServiceImpl<ScScjlbMapper, ScScjlb> implements IScScjlbService {
    private Logger logger = LoggerFactory.getLogger(ScScjlbServiceImpl.class);

    @Autowired
    ScScjlbMapper scScjlbMapper;

    @Override
    public String insert(ScScjlb scScjlb) {
        try {
            scScjlbMapper.insert(scScjlb);
            return "插入成功 "+scScjlb.getDj_jlbh();
        }catch (Exception e){
            logger.error("ScScjlb insert error {}",e);
        }
        return null;


    }

    @Override
    public List<ScScjlb> selectByDjbhAndGxbh(String djbh, String gxbh) {
        QueryWrapper<ScScjlb> wrapper = new QueryWrapper<ScScjlb>();
        if(StringUtils.isNotBlank(djbh)){
            wrapper.eq("DJ_BH",djbh);
        }
        if(StringUtils.isNotBlank(gxbh)){
            wrapper.eq("GX_BH",gxbh);
        }
        wrapper.eq("MX_TJBZ","0");
        return scScjlbMapper.selectList(wrapper);
    }

    @Override
    public List<ScScjlb> selectByDjbhAndYybh(String djbh, String fclb, String yybh) {

        QueryWrapper<ScScjlb> wrapper = new QueryWrapper<ScScjlb>();
        if(StringUtils.isNotBlank(djbh)){
            wrapper.eq("DJ_BH",djbh);
        }
        if(StringUtils.isNotBlank(fclb)){
            wrapper.eq("MX_FCLB",fclb);
        }
        if(StringUtils.isNotBlank(yybh)){
            wrapper.eq("MX_YY",yybh);
        }

        return scScjlbMapper.selectList(wrapper);
    }
}
