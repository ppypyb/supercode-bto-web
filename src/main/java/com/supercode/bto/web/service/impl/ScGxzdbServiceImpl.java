package com.supercode.bto.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supercode.bto.web.entity.ScGxzdb;
import com.supercode.bto.web.mapper.ScGxzdbMapper;
import com.supercode.bto.web.service.IScGxzdbService;
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
public class ScGxzdbServiceImpl extends ServiceImpl<ScGxzdbMapper, ScGxzdb> implements IScGxzdbService {
    @Autowired
    private ScGxzdbMapper scGxzdbMapper;

    @Override
    public List<ScGxzdb> selectByGxbh(String gxbh) {
        QueryWrapper<ScGxzdb> wrapper = new QueryWrapper<ScGxzdb>();
        if(StringUtils.isNotBlank(gxbh)){
            wrapper.eq("GX_BH",gxbh);
        }

        return scGxzdbMapper.selectList(wrapper);
    }
}
