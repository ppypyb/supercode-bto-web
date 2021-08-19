package com.supercode.bto.web.service;

import com.supercode.bto.web.entity.ScScjlb;
import com.supercode.bto.web.pojos.restful.RestResult;

import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @author pengyongbo
 * @date 2021/7/8 0:07
 * @version 1.0
 */
public interface IBtoWebService {
    /**
     * @description: 验证注册码是否正确
     * @author pengyongbo
     * @param: [registrationCode]
     * @date: 2021/7/8 0:12
     * @return:
     */
    RestResult verifyRegistrationCode(String registrationCode);

    /**
     * @description: 根据用户名和密码登录系统
     * @author pengyongbo
     * @param: [yhmc, yhmm]
     * @date: 2021/7/9 19:04
     * @return:
     */
    RestResult login(String yhmc,String yhmm);

    /**
     * @description: 根据条件查询订单信息
     * @author pengyongbo
     * @param: [khbh, htbh, ddbh, cpmc, startDate, endDate]
     * @date: 2021/7/14 14:41
     * @return:
     */
    Map<String,Object> queryOrderList(String khbh, String htbh, String ddbh,String rybh, String cpmc,String status, String startDate, String endDate);

    /**
     * @description: 根据订单编号查询订单进度
     * @author pengyongbo
     * @param: [ddbh]
     * @date: 2021/7/17 10:37
     * @return:
     */
    Map<String,Object> queryOrderProgressInformation( String ddbh);

    /**
     * @description: 根据订单编号和部门编号查询工序明细
     * @author pengyongbo
     * @param: [ddbh, bmbh]
     * @date: 2021/7/17 14:25
     * @return:
     */
    List<Map<String,Object>> queryProcessDetailList(String ddbh, String bmbh);

    /**
     * @description: 根据顶顶顶编号和人员编号查询订单记录
     * @author pengyongbo
     * @param: [ddbh, rybh]
     * @date: 2021/7/17 17:42
     * @return:
     */
    Map<String,Object> queryOrderRecordInfo(String ddbh,String rybh);

    /**
     * @description: 根据岗位工序编号查询岗位工序记录新
     * @author pengyongbo
     * @param: [ddbh, rybh, gxbh]
     * @date: 2021/7/17 18:11
     * @return:
     */
    List<Map<String,Object>> queryJobProcessInfo(String ddbh,String rybh,String gxbh);

    /**
     * @description: 查询产品客户信息
     * @author pengyongbo
     * @param: []
     * @date: 2021/7/17 23:15
     * @return:
     */
    List<Map<String,Object>> queryCustomerList(String khbh);

    /**
     * @description: 根据人员编号查询人员所有岗位工序信息
     * @author pengyongbo
     * @param: [rybh]
     * @date: 2021/7/20 9:26
     * @return:
     */
    List<Map<String,Object>> queryPersonnelJobProcessList(String rybh);

    /**
     * @description: 根据订单编号和产品编号查询订单跟踪单号
     * @author pengyongbo
     * @param: [ddbh, cpbh]
     * @date: 2021/7/20 20:59
     * @return:
     */
    List<Map<String,Object>> queryTrackNumberList(String ddbh,String cpbh);

    /**
     * @description: 根据订单编号和产品编号查询订单交接单号
     * @author pengyongbo
     * @param: [ddbh, cpbh]
     * @date: 2021/7/20 21:00
     * @return:
     */
    List<Map<String,Object>> queryHandoverNumberList(String ddbh,String cpbh);

    /**
     * @description: 查询废次品原因信息
     * @author pengyongbo
     * @param: [gxbh, fclb]
     * @date: 2021/7/21 10:45
     * @return:
     */
    List<Map<String,Object>> queryScrapReasonList(String ddbh,String rybh,String gxbh,String gzdh,String jjdh,String fclb);

    /**
     * @description: 查询跟踪单号数量
     * @author pengyongbo
     * @param: [ddbh, gxbh, gzdh]
     * @date: 2021/7/22 0:41
     * @return:
     */
    List<Map<String,Object>> queryTrackOrderAmount(String ddbh,String cpbh,String gxbh,String gzdh);

    /**
     * @description: 查询交接单号数量
     * @author pengyongbo
     * @param: [ddbh, gxbh, jjdh]
     * @date: 2021/7/23 0:03
     * @return:
     */
    List<Map<String,Object>> queryHandoverOrderAmount(String ddbh,String cpbh,String gxbh,String gzbh,String jjdh);

    /**
     * @description: 根据订单编号，产品编号、人员编号查询登记的订单信息
     * @author pengyongbo
     * @param: [ddbh, cpbh, rybh]
     * @date: 2021/7/25 12:14
     * @return:
     */
    Map<String,Object> queryProductionRegistrationOrderInfo(String ddbh,String rybh);

    /**
     * @description: 更新生产登记订单信息
     * @author pengyongbo
     * @param: [djbh, gxbh, gzdh, jjdh, zpsl, fpsl, cpsl]
     * @date: 2021/7/25 15:25
     * @return:
     */
    RestResult updateProductionRegistrationOrderInfo(String djbh,String ddbh,String cpbh,String rybh,String bmbh,String gxbh,String gzdh,String jjdh,String zpsl,String fpsl,String cpsl);

    /**
     * @description: 废次品登记
     * @author pengyongbo
     * @param: [scScjlb]
     * @date: 2021/7/25 16:24
     * @return:
     */
    RestResult scrapRegister(Map<String,Object> params);

    /**
     * @description: 删除tibz=0的生产登记记录和生产记录
     * @author pengyongbo
     * @param: [djbh, ddbh, gxbh]
     * @date: 2021/7/25 21:13
     * @return:
     */
    RestResult cancelProductionRegistration(String djbh,String ddbh,String gxbh);

    /**
     * @description: 查询岗位工序
     * @author pengyongbo
     * @param: [ddbh, rybh, gxbh, gzdh, jjdh]
     * @date: 2021/8/19 10:48
     * @return:
     */
    List<Map<String,Object>> queryJobProcessList(String ddbh,String rybh,String gxbh,String gzdh,String jjdh);




}
