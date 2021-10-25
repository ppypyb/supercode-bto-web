package com.supercode.bto.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supercode.bto.web.entity.ScCpgxde;
import com.supercode.bto.web.entity.ScGxzdb;
import com.supercode.bto.web.pojos.restful.RestResult;

import java.util.List;

/**
 * @description: TODO
 * @author pengyongbo
 * @date 2021/7/19 22:51
 * @version 1.0
 */
public interface IScGxzdbService extends IService<ScGxzdb> {



    /**
     * @description: 根据工序编号查询工序字典信息
     * @author pengyongbo
     * @param: [gxbh]
     * @date: 2021/7/25 15:35
     * @return:
     */
    public List<ScGxzdb> selectByGxbh(String gxbh);


}
