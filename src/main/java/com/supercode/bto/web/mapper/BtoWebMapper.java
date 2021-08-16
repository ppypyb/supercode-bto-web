package com.supercode.bto.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/7 16:09
 */
@Repository
public interface BtoWebMapper {

    /**
     * @description: 查询注册码信息
     * @author pengyongbo
     * @param: [registrationCode]
     * @date: 2021/7/8 0:20
     * @return:
     */
    List<Map<String,Object>> queryRegistrationCode(@Param("registrationCode") String registrationCode);

    /**
     * @description: 根据条件查询订单信息
     * @author pengyongbo
     * @param: [khbh, htbh, ddbh, cpmc, startDate, endDate]
     * @date: 2021/7/14 17:14
     * @return:
     */
    List<Map<String,Object>> queryOrderList(@Param("khbh") String khbh,
                                            @Param("htbh") String htbh,
                                            @Param("ddbh") String ddbh,
                                            @Param("rybh") String rybh,
                                            @Param("cpmc") String cpmc,
                                            @Param("startDate") String startDate,
                                            @Param("endDate") String endDate
                                            );

    /**
     * @description: 动态查询工段订单信息
     * @author pengyongbo
     * @param: [ddbh, jhwcsjFiled, scwcsjFiled, scjjslFile, scwcslFiled, scfpslFiled]
     * @date: 2021/7/17 17:24
     * @return:
     */
    List<Map<String,Object>> queryDepartmentOrderList(@Param("ddbh") String ddbh,
                                                      @Param("jhwcsjFiled") String jhwcsjFiled,
                                                      @Param("scwcsjFiled") String scwcsjFiled,
                                                      @Param("scjjslFiled") String scjjslFile,
                                                      @Param("scwcslFiled") String scwcslFiled,
                                                      @Param("scfpslFiled") String scfpslFiled);

    /**
     * @description: 根据订单编号和部门编号查询工序明细
     * @author pengyongbo
     * @param: [ddbh, bmbh]
     * @date: 2021/7/17 17:25
     * @return:
     */
    List<Map<String,Object>> queryProcessDetailList(@Param("ddbh") String ddbh,
                                                      @Param("bmbh") String bmbh);


    /**
     * @description: 根据订单编号和人员编号查询订单信息
     * @author pengyongbo
     * @param: [ddbh, rybh]
     * @date: 2021/7/17 17:27
     * @return:
     */
    List<Map<String,Object>> queryOrderRecordInfo(@Param("ddbh") String ddbh,
                                                    @Param("rybh") String rybh);

    /**
     * @description: 根据订单编号和人员编号查询岗位工序列表
     * @author pengyongbo
     * @param: [ddbh, rybh]
     * @date: 2021/7/17 17:30
     * @return:
     */
    List<Map<String,Object>> queryOrderProcessList(@Param("ddbh") String ddbh,
                                                   @Param("rybh") String rybh);

    /**
     * @description: 根据订单编号、人员编号以及岗位工序编号查询岗位工序信息
     * @author pengyongbo
     * @param: [ddbh, rybh, gxbh]
     * @date: 2021/7/17 17:33
     * @return:
     */
    List<Map<String,Object>> queryJobProcessList(@Param("ddbh") String ddbh,
                                                 @Param("rybh") String rybh,
                                                 @Param("gxbh") String gxbh,
                                                 @Param("gzdh") String gzdh,
                                                 @Param("jjdh") String jjdh);

    /**
     * @description: 根据客户分类查询客户信息
     * @author pengyongbo
     * @param: [khfl]
     * @date: 2021/7/17 23:17
     * @return:
     */
    List<Map<String,Object>> queryCustomerList(@Param("khfl") String khfl,@Param("khbh") String khbh);

    /**
     * @description: 根据人员编号查询人员所有岗位信息
     * @author pengyongbo
     * @param: [rybh]
     * @date: 2021/7/20 9:34
     * @return:
     */
    List<Map<String,Object>> queryPersonnelJobProcessList(@Param("rybh") String rybh);

    /**
     * @description: 查询订单的跟踪单号信息
     * @author pengyongbo
     * @param: [ddbh, cpbh]
     * @date: 2021/7/20 21:08
     * @return:
     */
    List<Map<String,Object>> queryTrackNumberList(@Param("ddbh") String ddbh,
                                                     @Param("cpbh") String cpbh);

    /**
     * @description: 查询订单的交接单号信息
     * @author pengyongbo
     * @param: [ddbh, cpbh]
     * @date: 2021/7/20 21:08
     * @return:
     */
    List<Map<String,Object>> queryHandoverNumberList(@Param("ddbh") String ddbh,
                                                     @Param("cpbh") String cpbh);
    /**
     * @description: 查询废次品原因信息
     * @author pengyongbo
     * @param: [gxbh, fclb]
     * @date: 2021/7/21 10:47
     * @return:
     */
    List<Map<String,Object>> queryScrapReasonList(@Param("gxbh") String gxbh,
                                                  @Param("fclb") String fclb);

    /**
     * @description: 查询跟踪单号数量
     * @author pengyongbo
     * @param: [ddbh, gxbh, gzdh]
     * @date: 2021/7/22 0:43
     * @return:
     */
    List<Map<String,Object>> queryTrackOrderAmount(@Param("ddbh") String ddbh,
                                                   @Param("cpbh") String cpbh,
                                                   @Param("gxbh") String gxbh,
                                                   @Param("gzdh") String gzdh);

    /**
     * @description: 查询岗位工序的交接工序
     * @author pengyongbo
     * @param: [ddbh, cpbh, gxbh]
     * @date: 2021/7/23 0:35
     * @return:
     */
    List<Map<String,Object>> queryHandoverOrderProcessNumber(@Param("ddbh") String ddbh,
                                                             @Param("cpbh") String cpbh,
                                                             @Param("gxbh") String gxbh);

    /**
     * @description: 查询生产登记工单信息
     * @author pengyongbo
     * @param: [ddbh, rybh]
     * @date: 2021/7/25 13:56
     * @return:
     */
    List<Map<String,Object>> queryProductionRegistrationOrderInfo(@Param("ddbh") String ddbh,
                                                                  @Param("rybh") String rybh);

    /**
     * @description: 查询订单相关数量
     * @author pengyongbo
     * @param: [ddbh, gxbh, fclb]
     * @date: 2021/7/25 20:40
     * @return:
     */
    List<Map<String,Object>> queryProcessAmount(@Param("ddbh") String ddbh,
                                                @Param("gxbh") String gxbh,
                                                @Param("fclb") String fclb);

    /**
     * @description: 查询订单交接数量
     * @author pengyongbo
     * @param: [ddbh, gxbh]
     * @date: 2021/7/25 20:47
     * @return:
     */
    List<Map<String,Object>> queryProcessHandoverAmount(@Param("ddbh") String ddbh,
                                                        @Param("gxbh") String gxbh);

    /**
     * @description: 根据登记编号和废次类别
     * @author pengyongbo
     * @param: [djbh, fclb]
     * @date: 2021/8/8 21:41
     * @return:
     */
    List<Map<String,Object>> queryScrapReasons(@Param("ddbh") String ddbh,
                                               @Param("rybh") String rybh,
                                               @Param("gxbh") String gxbh,
                                                     @Param("fclb") String fclb);






}
