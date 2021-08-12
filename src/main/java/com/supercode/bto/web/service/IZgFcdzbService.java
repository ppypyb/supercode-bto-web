package com.supercode.bto.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supercode.bto.web.entity.ScScjlb;
import com.supercode.bto.web.entity.ZgFcdzb;
import com.supercode.bto.web.pojos.restful.RestResult;

/**
 * @description: TODO
 * @author pengyongbo
 * @date 2021/7/19 22:50
 * @version 1.0
 */
public interface IZgFcdzbService extends IService<ZgFcdzb> {

    public RestResult insert(ZgFcdzb zgFcdzb);

}
