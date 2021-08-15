package com.supercode.bto.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supercode.bto.web.entity.ScJldjb;
import com.supercode.bto.web.pojos.restful.RestResult;

import java.util.List;

/**
 * @description: TODO
 * @author pengyongbo
 * @date 2021/7/19 22:48
 * @version 1.0
 */
public interface IScJldjbService extends IService<ScJldjb> {

    public String insert(ScJldjb scJldjb);

    public List<ScJldjb> getByDjbh(String djbh);

    /**
     * @description: 查询生产登记表信息
     * @author pengyongbo
     * @param: [ddbh, rybh, gxbh, gzdh, jjdh]
     * @date: 2021/8/8 20:43
     * @return:
     */
    List<ScJldjb> queryScjlbList(String ddbh,String rybh,String gxbh,String gzdh,String jjdh);

}
