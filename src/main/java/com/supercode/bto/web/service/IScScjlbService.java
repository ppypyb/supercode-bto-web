package com.supercode.bto.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supercode.bto.web.entity.ScScjlb;
import com.supercode.bto.web.pojos.restful.RestResult;

import java.util.List;

/**
 * @description: TODO
 * @author pengyongbo
 * @date 2021/7/18 11:52
 * @version 1.0
 */
public interface IScScjlbService extends IService<ScScjlb> {

    public RestResult insert(ScScjlb scScjlb);

    /**
     * @description: 根据登记编号和工序编号查询生产记录信息
     * @author pengyongbo
     * @param: [djbh, gxbh]
     * @date: 2021/7/25 17:18
     * @return:
     */
    List<ScScjlb> selectByDjbhAndGxbh(String djbh,String gxbh);

}
