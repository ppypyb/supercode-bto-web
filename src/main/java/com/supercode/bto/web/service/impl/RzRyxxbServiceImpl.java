package com.supercode.bto.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supercode.bto.web.entity.RzRyxxb;
import com.supercode.bto.web.mapper.RzRyxxbMapper;
import com.supercode.bto.web.service.IRzRyxxbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/8 20:56
 */
@Service
public class RzRyxxbServiceImpl extends ServiceImpl<RzRyxxbMapper, RzRyxxb> implements IRzRyxxbService {
    Logger logger = LoggerFactory.getLogger(RzRyxxbServiceImpl.class);
    @Autowired
    RzRyxxbMapper rzRyxxbMapper;

    @Override
    public List<RzRyxxb> selectByRybh(String rybh) {
        QueryWrapper<RzRyxxb> wrapper = new QueryWrapper<>();
        wrapper.eq("RY_BH",rybh);
        return rzRyxxbMapper.selectList(wrapper);
    }

    @Override
    public List<RzRyxxb> selectByRyxm(String ryxm) {
        QueryWrapper<RzRyxxb> wrapper = new QueryWrapper<>();
        wrapper.eq("RY_XM",ryxm);
        return rzRyxxbMapper.selectList(wrapper);
    }
}
