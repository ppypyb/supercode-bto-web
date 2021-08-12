package com.supercode.bto.web.controller;




import com.supercode.bto.web.entity.RzRyxxb;
import com.supercode.bto.web.interceptor.UserLoginToken;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IBaiduAipService;
import com.supercode.bto.web.service.IBtoWebService;
import com.supercode.bto.web.service.IRzRyxxbService;
import com.supercode.bto.web.utils.restful.ResultUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/7 16:00
 */
@RestController
@RequestMapping(value = "btoweb")
@CrossOrigin(origins = "*",maxAge = 3600)
@Api(value = "订单生产管理", tags = { "订单生产管理" })
public class BtoWebController {
    private  Logger logger = LoggerFactory.getLogger(BtoWebController.class);

    @Autowired
    IBtoWebService btoWebService;

    @Autowired
    private IBaiduAipService baiduAipService;

    @Autowired
    IRzRyxxbService rzRyxxbService;

    private SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd");



    @ApiOperation(value = "验证注册码是否可用",notes = "验证注册码是否可用")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registrationCode"  , paramType = "query" , required = true , dataType = "String")
    })
    @RequestMapping(value = "verifyRegistrationCode",method = RequestMethod.GET)
    public RestResult verifyRegistrationCode(@RequestParam(name = "registrationCode")String registrationCode){
        try {
            logger.info("verifyRegistrationCode registrationCode {}",registrationCode);
            return btoWebService.verifyRegistrationCode(registrationCode);
        } catch (Exception e) {
            logger.error("verifyRegistrationCode {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "用户登录",notes = "用户登录")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yhmc" , value = "用户名" , paramType = "query" , required = true , dataType = "String"),
            @ApiImplicitParam(name = "yhmm" , value = "密码" , paramType = "query" , required = true , dataType = "String")
    })
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public RestResult login(@RequestParam(name = "yhmc")String yhmc,
                            @RequestParam(name = "yhmm")String yhmm){
        try {
            logger.info("login ryxm {} yhmm  {}",yhmc,yhmm);
            return btoWebService.login(yhmc,yhmm);
        } catch (Exception e) {
            logger.error("login {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "订单查询",notes = "订单查询")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "khbh" , value = "客户编号" , paramType = "query" , required = false , dataType = "String"),
            @ApiImplicitParam(name = "htbh" , value = "合同编号" , paramType = "query" , required = false , dataType = "String"),
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = false , dataType = "String"),
            @ApiImplicitParam(name = "cpmc" , value = "产品名称(支持模糊查询)" , paramType = "query" , required = false , dataType = "String"),
            @ApiImplicitParam(name = "status" , value = "订单状态（1：执行者；2：已完成；3：逾期；4：临期）" , paramType = "query" , required = false , dataType = "String"),
            @ApiImplicitParam(name = "startDate" , value = "起始日期(格式：yyyy-mm-dd)" , paramType = "query" , required = false , dataType = "String"),
            @ApiImplicitParam(name = "endDate" , value = "结束日期(格式：yyyy-mm-dd)" , paramType = "query" , required = false , dataType = "String"),
            @ApiImplicitParam(name = "rybh" , value = "人员编号" , paramType = "query" , required = false , dataType = "String"),

    })
    @RequestMapping(value = "queryOrderList",method = RequestMethod.GET)
    public RestResult queryOrderList(@RequestParam(name = "khbh",required = false,defaultValue = "") String khbh,
                                     @RequestParam(name = "htbh",required = false,defaultValue = "") String htbh,
                                     @RequestParam(name = "ddbh",required = false,defaultValue = "") String ddbh,
                                     @RequestParam(name = "rybh",required = false,defaultValue = "") String rybh,
                                     @RequestParam(name = "cpmc",required = false,defaultValue = "") String cpmc,
                                     @RequestParam(name = "status",required = false,defaultValue = "") String status,
                                     @RequestParam(name = "startDate",required = false,defaultValue = "") String startDate,
                                     @RequestParam(name = "endDate",required = false,defaultValue = "") String endDate
                                     ){
        try {
            logger.info("queryOrderList khbh {} htbh  {} ddbh {} rybh {} cpmc {} status {} startDate {} endDate {}",khbh,htbh,ddbh,rybh,cpmc,status,startDate,endDate);
            Map<String,Object> resultMap = btoWebService.queryOrderList(khbh,htbh,ddbh,rybh,cpmc,status,startDate,endDate);
            return ResultUtil.success(resultMap);
        } catch (Exception e) {
            logger.error("queryOrderList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }


    @ApiOperation(value = "订单进度查询",notes = "订单进度查询")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true , dataType = "String")
    })
    @RequestMapping(value = "queryOrderProgressInformation",method = RequestMethod.GET)
    public RestResult queryOrderProgressInformation(
                                     @RequestParam(name = "ddbh") String ddbh){
        try {
            logger.info("queryOrderProgressInformation  ddbh {} ",ddbh);
            Map<String,Object> resultMap = btoWebService.queryOrderProgressInformation(ddbh);
            return ResultUtil.success(resultMap);
        } catch (Exception e) {
            logger.error("queryOrderProgressInformation {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }


    @ApiOperation(value = "工序明细查询",notes = "工序明细查询")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "bmbh" , value = "部门编号" , paramType = "query" , required = true, dataType = "String")
    })
    @RequestMapping(value = "queryProcessDetailList",method = RequestMethod.GET)
    public RestResult queryProcessDetailList(@RequestParam(name = "ddbh") String ddbh,
            @RequestParam(name = "bmbh") String bmbh){
        try {
            logger.info("queryProcessDetailList  ddbh {} bmbh {} ",ddbh,bmbh);
            List<Map<String,Object>> resultMap = btoWebService.queryProcessDetailList(ddbh,bmbh);
            return ResultUtil.success(resultMap);
        } catch (Exception e) {
            logger.error("queryProcessDetailList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }


    @ApiOperation(value = "订单记录查询",notes = "订单记录查询")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "rybh" , value = "人员编号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryOrderRecordInfo",method = RequestMethod.GET)
    public RestResult queryOrderRecordInfo(@RequestParam(name = "ddbh") String ddbh,
                                             @RequestParam(name = "rybh") String rybh){
        try {
            logger.info("queryOrderRecordInfo  ddbh {} rybh {} ",ddbh,rybh);
            Map<String,Object> resultMap = btoWebService.queryOrderRecordInfo(ddbh,rybh);
            if(resultMap != null && resultMap.size() > 0){
                return ResultUtil.success(resultMap);
            }
            return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);

        } catch (Exception e) {
            logger.error("queryOrderRecordInfo {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }


    @ApiOperation(value = "岗位工序记录查询",notes = "岗位工序记录查询")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true, dataType = "String"),
            @ApiImplicitParam(name = "rybh" , value = "人员编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gxbh" , value = "岗位工序编号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryJobProcessInfo",method = RequestMethod.GET)
    public RestResult queryJobProcessInfo(@RequestParam(name = "ddbh") String ddbh,
                                          @RequestParam(name = "rybh") String rybh,
                                          @RequestParam(name = "gxbh") String gxbh){
        try {
            logger.info("queryJobProcessInfo  ddbh {} rybh {} gxbh {}",ddbh,rybh,gxbh);
            List<Map<String,String>> result = new ArrayList<Map<String,String>>();
            List<Map<String,Object>> resultList = btoWebService.queryJobProcessInfo(ddbh,rybh,gxbh);
            if(resultList != null && resultList.size() > 0){
                return ResultUtil.success(resultList);
            }
            return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);

        } catch (Exception e) {
            logger.error("queryJobProcessInfo {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "查询产品客户信息",notes = "查询产品客户信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rybh" , value = "人员编号" , paramType = "query" , required = true, dataType = "String")
    })
    @RequestMapping(value = "queryCustomerList",method = RequestMethod.GET)
    public RestResult queryCustomerList(@RequestParam(name = "rybh") String rybh){
        try {
            logger.info("queryCustomerList ");
            List<RzRyxxb> rzRyxxbList = rzRyxxbService.selectByRybh(rybh);
            if(rzRyxxbList != null && rzRyxxbList .size() > 0){
                for(RzRyxxb rzRyxxb : rzRyxxbList){
                    String rykhbh = rzRyxxb.getRy_khbh();
                    if(StringUtils.isNotBlank(rykhbh)){
                        List<Map<String,Object>> resultList = btoWebService.queryCustomerList(rykhbh);
                        if(resultList != null && resultList.size() > 0){
                            Map<String,Object> map = new HashMap<>();
                            map.put("khbh","");
                            map.put("khmc","");
                            map.put("khdz","");
                            resultList.add(0,map);
                            return ResultUtil.success(resultList);
                        }

                    }else{
                        List<Map<String,Object>> resultList = btoWebService.queryCustomerList(null);
                        if(resultList != null && resultList.size() > 0){
                            Map<String,Object> map = new HashMap<>();
                            map.put("khbh","");
                            map.put("khmc","");
                            map.put("khdz","");
                            resultList.add(0,map);
                            return ResultUtil.success(resultList);
                        }
                    }

                }

            }


            return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);

        } catch (Exception e) {
            logger.error("queryCustomerList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "查询人员岗位工序信息",notes = "查询人员岗位工序信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rybh" , value = "人员编号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryPersonnelJobProcessList",method = RequestMethod.GET)
    public RestResult queryPersonnelJobProcessList(@RequestParam(name = "rybh") String rybh){
        try {
            logger.info("queryPersonnelJobProcessList rybh {} ",rybh);
            List<Map<String,Object>> resultList = btoWebService.queryPersonnelJobProcessList(rybh);
            if(resultList != null && resultList.size() > 0){
                return ResultUtil.success(resultList);
            }
            return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);

        } catch (Exception e) {
            logger.error("queryPersonnelJobProcessList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "查询生产登记中岗位工序信息",notes = "查询生产登记中岗位工序信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "rybh" , value = "人员编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gxbh" , value = "岗位工序编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gzdh" , value = "跟踪单号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "jjdh" , value = "交接单号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryJobProcessList",method = RequestMethod.GET)
    public RestResult queryJobProcessList(@RequestParam(name = "ddbh") String ddbh,
                                                      @RequestParam(name = "rybh") String rybh,
                                                      @RequestParam(name = "gxbh") String gxbh,
                                                      @RequestParam(name = "gzdh") String gzdh,
                                                      @RequestParam(name = "jjdh") String jjdh
                                                      ){
        try {
            logger.info("queryJobProcessList ddbh {} rybh {} gxbh {} gzdh {} jjdh {}",ddbh,rybh,gxbh,gzdh,jjdh);
            List<Map<String,Object>> jobProcessList = btoWebService.queryJobProcessList(ddbh,rybh,gxbh,gzdh,jjdh);
            if(jobProcessList != null && jobProcessList.size() > 0){
                return ResultUtil.success(jobProcessList);
            }else{
                List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
                Map<String,String> map = new HashMap<>();
                map.put("zpsl","0");
                map.put("fpsl","0");
                map.put("cpsl","0");
                map.put("djrq",sdf.format(new Date()));
                resultList.add(map);
                return ResultUtil.success(resultList);
            }
        } catch (Exception e) {
            logger.error("queryTrackingNumberList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "查询订单跟踪和交接单单号信息",notes = "查询订单跟踪和交接单单号信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "cpbh" , value = "产品编号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryTrackAndHandoverNumberList",method = RequestMethod.GET)
    public RestResult queryTrackAndHandoverNumberList(@RequestParam(name = "ddbh") String ddbh,
                                              @RequestParam(name = "cpbh") String cpbh){
        try {
            logger.info("queryTrackingNumberList ddbh {} cpbh {}",ddbh,cpbh);
            Map<String,Object> result = new HashMap<String,Object>();
            List<Map<String,Object>> trackNumberList = btoWebService.queryTrackNumberList(ddbh,cpbh);
            if(trackNumberList != null && trackNumberList.size() > 0){
                result.put("gzdhList",trackNumberList);
            }
            List<Map<String,Object>> handoverNumberList = btoWebService.queryHandoverNumberList(ddbh,cpbh);
            if(handoverNumberList != null && handoverNumberList.size() > 0){
                result.put("jjdhList",handoverNumberList);
            }
            if(result != null && result.size() > 0){
                return ResultUtil.success(result);
            }
            return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);


        } catch (Exception e) {
            logger.error("queryTrackingNumberList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "查询订单跟踪单号信息",notes = "查询订单跟踪单号信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "cpbh" , value = "产品编号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryTrackingNumberList",method = RequestMethod.GET)
    public RestResult queryTrackingNumberList(@RequestParam(name = "ddbh") String ddbh,
                                              @RequestParam(name = "cpbh") String cpbh){
        try {
            logger.info("queryTrackingNumberList ddbh {} cpbh {}",ddbh,cpbh);
            List<Map<String,Object>> resultList = btoWebService.queryTrackNumberList(ddbh,cpbh);
            if(resultList != null && resultList.size() > 0){
                return ResultUtil.success(resultList);
            }
            return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);

        } catch (Exception e) {
            logger.error("queryTrackingNumberList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "查询订单交接单号信息",notes = "查询订单交接单号信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "cpbh" , value = "产品编号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryHandoverNumberList",method = RequestMethod.GET)
    public RestResult queryHandoverNumberList(@RequestParam(name = "ddbh") String ddbh,
                                              @RequestParam(name = "cpbh") String cpbh){
        try {
            logger.info("queryTrackingNumberList ddbh {} cpbh {}",ddbh,cpbh);
            List<Map<String,Object>> resultList = btoWebService.queryHandoverNumberList(ddbh,cpbh);
            if(resultList != null && resultList.size() > 0){
                return ResultUtil.success(resultList);
            }
            return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);

        } catch (Exception e) {
            logger.error("queryHandoverNumberList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "查询废次品原因信息",notes = "查询废次品原因信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "rybh" , value = "人员编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gxbh" , value = "岗位工序编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gzdh" , value = "跟踪单号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "jjdh" , value = "交接单号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "fclb" , value = "废次品类别（0：废品、1：次品）" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryScrapReasonList",method = RequestMethod.GET)
    public RestResult queryScrapReasonList(@RequestParam(name = "ddbh") String ddbh,
                                           @RequestParam(name = "rybh") String rybh,
                                           @RequestParam(name = "gxbh") String gxbh,
                                           @RequestParam(name = "gzdh") String gzdh,
                                           @RequestParam(name = "jjdh") String jjdh,
                                           @RequestParam(name = "fclb") String fclb){
        try {
            logger.info("queryScrapReasonList ddbh {} rybh {} gxbh {} gzdh {} jjdh {} fclb {}",ddbh,rybh,gxbh,gzdh,jjdh,fclb);
            List<Map<String,Object>> resultList = btoWebService.queryScrapReasonList(ddbh,rybh,gxbh,gzdh,jjdh,fclb);
            if(resultList != null && resultList.size() > 0){
                return ResultUtil.success(resultList);
            }
            return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);

        } catch (Exception e) {
            logger.error("queryScrapReasonList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "查询跟踪单和交接单数量",notes = "查询跟踪单号数量")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gxbh" , value = "岗位工序编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "cpbh" , value = "产品编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gzdh" , value = "跟踪单号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "jjdh" , value = "交接单号" , paramType = "query" , required = false,  dataType = "String"),

    })
    @RequestMapping(value = "queryTrackAndHandoverOrderAmount",method = RequestMethod.GET)
    public RestResult queryTrackAndHandoverOrderAmount(@RequestParam(name = "gxbh") String gxbh,
                                            @RequestParam(name = "ddbh") String ddbh,
                                            @RequestParam(name = "cpbh") String cpbh,
                                            @RequestParam(name = "gzdh") String gzdh,
                                            @RequestParam(name = "jjdh",required = false,defaultValue = "") String jjdh){
        try {
            logger.info("queryTrackOrderAmount gxbh {} ddbh {} cpbh {} gzdh {} {}",gxbh,ddbh,cpbh,gzdh,jjdh);
            Map<String,String> result = new HashMap<String,String>();
            List<Map<String,Object>> trackOrderAmountList = btoWebService.queryTrackOrderAmount(ddbh,cpbh,gxbh,gzdh);
            if(trackOrderAmountList == null || trackOrderAmountList.size() ==0){
                trackOrderAmountList = new ArrayList<Map<String,Object>>();
                Map<String,Object> trackAmountMap = new HashMap<String,Object>();
                trackAmountMap.put("wcsl","0");
                trackOrderAmountList.add(trackAmountMap);
            }
            String gzdwcsl = "0";
            if(trackOrderAmountList.get(0) != null && trackOrderAmountList.get(0).size() >0){
                gzdwcsl = String.valueOf(trackOrderAmountList.get(0).get("wcsl"));
            }

            result.put("gzdwcsl",gzdwcsl);

            List<Map<String,Object>> handoverOrderAmountList = btoWebService.queryHandoverOrderAmount(ddbh,cpbh,gxbh,gzdh,jjdh);
            if(handoverOrderAmountList == null || handoverOrderAmountList.size() ==0){
                handoverOrderAmountList = new ArrayList<Map<String,Object>>();
                Map<String,Object> trackAmountMap = new HashMap<String,Object>();
                trackAmountMap.put("wcsl","0");
                handoverOrderAmountList.add(trackAmountMap);
            }
            String jjdwcsl = "0";
            if(handoverOrderAmountList.get(0) != null && handoverOrderAmountList.get(0).size() >0){
                jjdwcsl = String.valueOf(handoverOrderAmountList.get(0).get("wcsl"));
            }
            result.put("jjdwcsl",jjdwcsl);
            return ResultUtil.success(result);

        } catch (Exception e) {
            logger.error("queryScrapReasonList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }


    @ApiOperation(value = "查询跟踪单号数量",notes = "查询跟踪单号数量")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gxbh" , value = "岗位工序编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "cpbh" , value = "产品编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gzdh" , value = "跟踪单号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryTrackOrderAmount",method = RequestMethod.GET)
    public RestResult queryTrackOrderAmount(@RequestParam(name = "gxbh") String gxbh,
                                           @RequestParam(name = "ddbh") String ddbh,
                                            @RequestParam(name = "cpbh") String cpbh,
                                           @RequestParam(name = "gzdh") String gzdh){
        try {
            logger.info("queryTrackOrderAmount gxbh {} ddbh {} cpbh {} gzdh {}",gxbh,ddbh,cpbh,gzdh);
            List<Map<String,Object>> resultList = btoWebService.queryTrackOrderAmount(ddbh,cpbh,gxbh,gzdh);
            if(resultList == null || resultList.size() ==0){
                resultList = new ArrayList<Map<String,Object>>();
                Map<String,Object> trackAmountMap = new HashMap<String,Object>();
                trackAmountMap.put("wcsl","0");
                resultList.add(trackAmountMap);
            }
            return ResultUtil.success(resultList);

        } catch (Exception e) {
            logger.error("queryScrapReasonList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "查询交接单号数量",notes = "查询交接单号数量")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gxbh" , value = "岗位工序编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "cpbh" , value = "产品编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gzbh" , value = "跟踪编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "jjdh" , value = "交接单号" , paramType = "query" , required = false,  dataType = "String")
    })
    @RequestMapping(value = "queryHandoverOrderAmount",method = RequestMethod.GET)
    public RestResult queryHandoverOrderAmount(@RequestParam(name = "gxbh") String gxbh,
                                               @RequestParam(name = "ddbh") String ddbh,
                                               @RequestParam(name = "cpbh") String cpbh,
                                               @RequestParam(name = "gzbh") String gzbh,
                                               @RequestParam(name = "jjdh",required = false,defaultValue = "") String jjdh){
        try {
            logger.info("queryHandoverOrderAmount gxbh {} ddbh {} cpbh {} gzbh {} jjdh {}",gxbh,ddbh,cpbh,gzbh,jjdh);
            List<Map<String,Object>> resultList = btoWebService.queryHandoverOrderAmount(ddbh,cpbh,gxbh,gzbh,jjdh);
            if(resultList == null || resultList.size() ==0){
                resultList = new ArrayList<Map<String,Object>>();
                Map<String,Object> trackAmountMap = new HashMap<String,Object>();
                trackAmountMap.put("wcsl","0");
                resultList.add(trackAmountMap);
            }
            return ResultUtil.success(resultList);

        } catch (Exception e) {
            logger.error("queryScrapReasonList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }


    @ApiOperation(value = "查询生产登记订单信息",notes = "查询生产登记订单信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "rybh" , value = "人员编号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "queryProductionRegistrationOrderInfo",method = RequestMethod.GET)
    public RestResult queryProductionRegistrationOrderInfo(@RequestParam(name = "ddbh") String ddbh,
                                                           @RequestParam(name = "rybh") String rybh){
        try {
            logger.info("queryProductionRegistrationOrderInfo ddbh  {} rybh {} ",ddbh,rybh);
            Map<String,Object> result = new HashMap<String,Object>();
            Map<String,Object> orderInfo = btoWebService.queryProductionRegistrationOrderInfo(ddbh,rybh);
            result.putAll(orderInfo);
            List<Map<String,Object>> jobProcessList = btoWebService.queryPersonnelJobProcessList(rybh);
            result.put("gwgx",jobProcessList);

            if(result == null || result.size() ==0){
                return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);
            }
            return ResultUtil.success(result);

        } catch (Exception e) {
            logger.error("queryScrapReasonList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "更新生产登记订单信息",notes = "更新生产登记订单信息")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "cpbh" , value = "产品编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "rybh" , value = "人员编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "bmbh" , value = "部门编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gxbh" , value = "岗位工序编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gzdh" , value = "跟踪单号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "jjdh" , value = "交接单号" , paramType = "query" , required = false,  dataType = "String"),
            @ApiImplicitParam(name = "zpsl" , value = "正品数量" , paramType = "query" , required = false,  dataType = "String"),
            @ApiImplicitParam(name = "fpsl" , value = "废品数量" , paramType = "query" , required = false,  dataType = "String"),
            @ApiImplicitParam(name = "cpsl" , value = "次品数量" , paramType = "query" , required = false,  dataType = "String")
    })
    @RequestMapping(value = "updateProductionRegistrationOrderInfo",method = RequestMethod.GET)
    public RestResult updateProductionRegistrationOrderInfo(@RequestParam(name = "ddbh") String ddbh,
                                                            @RequestParam(name = "cpbh") String cpbh,
                                                            @RequestParam(name = "rybh") String rybh,
                                                            @RequestParam(name = "bmbh") String bmbh,
                                                            @RequestParam(name = "gxbh") String gxbh,
                                                            @RequestParam(name = "gzdh") String gzdh,
                                                            @RequestParam(name = "jjdh") String jjdh,
                                                            @RequestParam(name = "zpsl") String zpsl,
                                                            @RequestParam(name = "fpsl") String fpsl,
                                                            @RequestParam(name = "cpsl") String cpsl){
        try {
            logger.info("updateProductionRegistrationOrderInfo ddbh  {} cpbh {} gxbh {} gzdh {} jjdh {} zpsl {} fpsl {} cpsl {} ",ddbh,cpbh,gxbh,gzdh,jjdh,zpsl,fpsl,cpsl);
            return btoWebService.updateProductionRegistrationOrderInfo(ddbh,cpbh,rybh,bmbh,gxbh,gzdh,jjdh,zpsl,fpsl,cpsl);

        } catch (Exception e) {
            logger.error("queryScrapReasonList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "废次品登记",notes = "废次品登记")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @RequestMapping(value = "scrapRegister" ,method = RequestMethod.POST)
    public RestResult scrapRegister(@RequestBody Map<String,Object> params){
        try {
            logger.info("scrapRegister params  {}  ",params);
            if(params == null || params.size() ==0){
                return ResultUtil.error(ResultCodeEnum.PARAM_EMPTY);
            }
            return btoWebService.scrapRegister(params);

        } catch (Exception e) {
            logger.error("queryScrapReasonList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @ApiOperation(value = "取消生产登记",notes = "取消生产登记")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "djbh" , value = "登记编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "ddbh" , value = "订单编号" , paramType = "query" , required = true,  dataType = "String"),
            @ApiImplicitParam(name = "gxbh" , value = "岗位工序编号" , paramType = "query" , required = true,  dataType = "String")
    })
    @RequestMapping(value = "cancelProductionRegistration" ,method = RequestMethod.GET)
    public RestResult cancelProductionRegistration(@RequestParam(name = "djbh") String djbh,
                                                   @RequestParam(name = "ddbh") String ddbh,
                                                   @RequestParam(name = "gxbh") String gxbh){
        try {
            logger.info("cancelProductionRegistration djbh  {} ddbh {} gxbh {} ",djbh,ddbh,gxbh);

            return btoWebService.cancelProductionRegistration(djbh,ddbh,gxbh);

        } catch (Exception e) {
            logger.error("queryScrapReasonList {}",e);
            return   ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    /**
     * 按段落解析一个word文档
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "百度OCR测试",notes = "百度OCR测试")
    @ApiResponses({@ApiResponse(code = 10000, message = "查询成功"),
            @ApiResponse(code = 30001, message = "查询的资源不存在"),
            @ApiResponse(code = 40001, message = "参数为空"),
            @ApiResponse(code = 50001, message = "内部接口调用异常")})
    @RequestMapping(value = "ocrNumbers", method = RequestMethod.POST)
    public RestResult ocrNumbers(@RequestParam(value = "file", required = true) MultipartFile file){
        Map<String,String> picNumbers = new HashMap<String,String>();
        try {
            logger.info("接收到文件开始识别：{}",System.currentTimeMillis());
            String numbers = baiduAipService.nubmers(file);
            picNumbers.put("numbers",numbers);
            logger.info("识别结束：{}",System.currentTimeMillis());
            return ResultUtil.success(picNumbers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
    }


    @UserLoginToken
    @RequestMapping(value = "check",method = RequestMethod.GET)
    public String healthCheck()
    {
        return "btoweb service is run ok!";
    }


}
