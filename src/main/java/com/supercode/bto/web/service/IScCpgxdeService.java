package com.supercode.bto.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supercode.bto.web.entity.ScCpgxde;
import com.supercode.bto.web.entity.ScJldjb;
import com.supercode.bto.web.pojos.restful.RestResult;

import java.util.List;

/**
 * @description: TODO
 * @author pengyongbo
 * @date 2021/7/19 22:51
 * @version 1.0
 */
public interface IScCpgxdeService extends IService<ScCpgxde> {

    public RestResult insert(ScCpgxde scCpgxde);

    /**
     * @description: 根据订单编号和岗位工序编号查询产品工序定额信息
     * @author pengyongbo
     * @param: [ddbh, gxbh]
     * @date: 2021/7/25 15:35
     * @return:
     */
    public List<ScCpgxde> selectByDdbhAndGxbh(String ddbh,String cpbh, String gxbh);

    /**
     * @description: 根据订单编号、产品编号、yl1查找工序定额信息
     * @author pengyongbo
     * @param: [ddbh, cpbh, gxbh]
     * @date: 2021/8/19 3:56
     * @return:
     */
    public List<ScCpgxde> selectByDdbhAndYl1(String ddbh,String cpbh, String gxbh);

}
