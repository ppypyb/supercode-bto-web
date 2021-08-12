package com.supercode.bto.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supercode.bto.web.entity.ZgFcdzb;
import com.supercode.bto.web.mapper.ScCpgxdeMapper;
import com.supercode.bto.web.mapper.ZgFcdzbMapper;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IZgFcdzbService;
import com.supercode.bto.web.utils.restful.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/19 22:53
 */
@Service
public class ZgFcdzbServiceImpl extends ServiceImpl<ZgFcdzbMapper, ZgFcdzb> implements IZgFcdzbService {

    @Autowired
    private ZgFcdzbMapper zgFcdzbMapper;

    @Override
    public RestResult insert(ZgFcdzb zgFcdzb) {
        try {
            zgFcdzbMapper.insert(zgFcdzb);
            return ResultUtil.success("插入成功 "+zgFcdzb.getGx_bh());
        }catch (Exception e){
            return ResultUtil.error(ResultCodeEnum.DATA_EXCEPTION);
        }
    }
}
