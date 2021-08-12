package com.supercode.bto.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supercode.bto.web.entity.RzBmxxb;
import com.supercode.bto.web.entity.RzRyxxb;
import com.supercode.bto.web.mapper.RzBmxxbMapper;
import com.supercode.bto.web.mapper.RzRyxxbMapper;
import com.supercode.bto.web.service.IRzBmxxbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/17 11:04
 */
@Service
public class RzBmxxbServiceImpl extends ServiceImpl<RzBmxxbMapper, RzBmxxb> implements IRzBmxxbService {
    Logger logger = LoggerFactory.getLogger(RzRyxxbServiceImpl.class);
    @Autowired
    RzBmxxbMapper rzBmxxbMapper;

    @Override
    public List<RzBmxxb> selectByBmbh(String bmbh) {
        QueryWrapper<RzBmxxb> wrapper = new QueryWrapper<>();
        wrapper.eq("BM_BH",bmbh);
        return rzBmxxbMapper.selectList(wrapper);
    }

    @Override
    public List<RzBmxxb> selectByBmmc(String bmmc) {
        QueryWrapper<RzBmxxb> wrapper = new QueryWrapper<>();
        wrapper.like("BM_MC",bmmc);
        return rzBmxxbMapper.selectList(wrapper);
    }

    @Override
    public List<RzBmxxb> selectByBmfl(String bmfl) {
        QueryWrapper<RzBmxxb> wrapper = new QueryWrapper<>();
        wrapper.like("BM_FL",bmfl);
        wrapper.orderByAsc("BM_JJXH");
        return rzBmxxbMapper.selectList(wrapper);
    }
}
