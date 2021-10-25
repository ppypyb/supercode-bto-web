package com.supercode.bto.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.supercode.bto.web.common.SystemConstants;
import com.supercode.bto.web.entity.*;
import com.supercode.bto.web.mapper.BtoWebMapper;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.*;
import com.supercode.bto.web.utils.restful.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/8 0:13
 */
@Service
public class BtoWebSereviceImpl implements IBtoWebService {
    private Logger logger = LoggerFactory.getLogger(BtoWebSereviceImpl.class);

    @Autowired
    private BtoWebMapper btoWebMapper;

    @Autowired
    IRzRyxxbService rzRyxxbService;

    @Autowired
    TokenService tokenService;

    @Autowired
    IRzBmxxbService rzBmxxbService;

    @Autowired
    private IScJldjbService scJldjbService;

    @Autowired
    private IScCpgxdeService scCpgxdeService;

    @Autowired
    private IScScjlbService scScjlbService;

    @Autowired
    private IScGxzdbService scGxzdbService;

    private SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd");
    /** 表主键**/
    private SimpleDateFormat keySdf = new SimpleDateFormat("yyMMddHHmmssSSS");

    private SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public RestResult verifyRegistrationCode(String registrationCode) {
        try {
            List<Map<String,Object>> registrationCodeList = btoWebMapper.queryRegistrationCode(registrationCode);
            if(registrationCodeList != null && registrationCodeList.size() > 0){
                return ResultUtil.success(registrationCodeList.get(0));
            }else{
                return ResultUtil.error(ResultCodeEnum.DATA_NON_EXISTENT);
            }
        }catch (Exception e){
            logger.error("verifyRegistrationCode registrationCode {} error {}",registrationCode,e);
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @Override
    public RestResult login(String yhmc, String yhmm) {
        List<RzRyxxb> rzRyxxbList = rzRyxxbService.selectByRyxm(yhmc);
        Map<String,Object> userMap = new HashMap<>();
        boolean passCorrectFlag = false;
        if(rzRyxxbList != null && rzRyxxbList.size() > 0){
            for(RzRyxxb rzRyxxb : rzRyxxbList){
                if(StringUtils.equals(rzRyxxb.getRy_mm(),yhmm)){
                    String yhbh = rzRyxxb.getRy_bh();
                    List<Map<String,Object>> personnelJobProcessList=btoWebMapper.queryPersonnelJobProcessList(yhbh);
                    if(personnelJobProcessList != null && personnelJobProcessList.size() > 0){
                        rzRyxxb.setRy_scgx("1");
                    }else{
                        rzRyxxb.setRy_scgx("0");
                    }
                    userMap.put("ryxx",rzRyxxb);
                    String token = tokenService.getToken(rzRyxxb);
                    userMap.put("token",token);
                    passCorrectFlag = true;
                    return ResultUtil.success(userMap);
                }
            }
            if(!passCorrectFlag){
                /** 密码错误 **/
                return ResultUtil.error(ResultCodeEnum.PASS_ERROR);
            }
        }else{
            return ResultUtil.error(ResultCodeEnum.USER_NON_EXISTENT);
        }
        return ResultUtil.error(ResultCodeEnum.UNKNOW_ERROR);
    }

    @Override
    public Map<String, Object> queryOrderList(String khbh, String htbh, String ddbh,String rybh,String cpmc, String status,String startDate, String endDate) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Map<String,Object>> orderList = btoWebMapper.queryOrderList(khbh,htbh,ddbh,rybh,cpmc,startDate,endDate);
        Set<String> orderDdbhList = new HashSet<String>();

        List<Map<String,Object>> resultOrderList = new ArrayList<Map<String,Object>>();

        if(orderList != null && orderList.size() > 0){
            for(Map<String, Object> orderMap : orderList){
                Map<String,Object> resultOrderMap = new ConcurrentHashMap<String,Object>();
                resultOrderMap.putAll(orderMap);
                String orderDdbh = "";
                String orderJhrq = "";
                String orderZt = "";
                String cptp = "default.png";
                String orderStatus = "";

                if(orderMap.get("cptp") != null){
                    cptp = String.valueOf(orderMap.get("cptp"));
                    if(!cptp.endsWith("jpg") && !cptp.endsWith("png") && !cptp.endsWith("JPG") && !cptp.endsWith("PNG")){
                        cptp = "default.png";
                    }
                }

                cptp = "http://"+ SystemConstants.serviceInternetIp+":"+SystemConstants.serviceInternetPort+SystemConstants.serviceContextPath+"/images/"+cptp;
                resultOrderMap.put("cptp",cptp);


                if(orderMap.get("jhrq") != null){
                    orderJhrq = String.valueOf(orderMap.get("jhrq"));
                }
                if(orderMap.get("ddzt") != null){
                    orderZt = String.valueOf(orderMap.get("ddzt"));
                }
                /** 执行中订单**/
                if(StringUtils.equals(status,"1")){
                    if(StringUtils.equals(orderZt,"1")){
                        resultOrderMap.put("status","1");
                        orderStatus = "1";
                    }
                }
                /** 已完成订单**/
                if(StringUtils.equals(status,"4")){
                    if(StringUtils.equals(orderZt,"2")){
                        resultOrderMap.put("status","2");
                        orderStatus = "2";
                    }
                }
                /** 逾期订单**/
                if(StringUtils.equals(status,"3")){
                    if(StringUtils.equals(orderZt,"1")){
                        if(StringUtils.isNotBlank(orderJhrq)){
                            try {
                                Date ddjhrqDate = sdf.parse(orderJhrq);
                                Date nowDate = new Date();
                                boolean flag = nowDate.getTime() > ddjhrqDate.getTime();
                                if(flag){
                                    /** 当前时间大于计划交货时间称为逾期**/
                                    resultOrderMap.put("status","3");
                                    orderStatus = "3";
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
                /** 临期订单 **/
                if(StringUtils.equals(status,"2")){
                    if(StringUtils.equals(orderZt,"1")){
                        if(StringUtils.isNotBlank(orderJhrq)){
                            /** 交货日期与当前时间相差7天之内为临期订单**/
                            Long diffDays = getDay(orderJhrq);
                            if(diffDays > 0 && diffDays < 8){
                                resultOrderMap.put("status","4");
                                orderStatus = "4";
                            }
                        }
                    }

                }
                if(StringUtils.isNotBlank(status) ){
                    if(StringUtils.equals(orderStatus,status)){
                        if(orderMap.get("ddbh") != null){
                            orderDdbh = String.valueOf(orderMap.get("ddbh"));
                            orderDdbhList.add(orderDdbh);
                        }
                        resultOrderList.add(resultOrderMap);
                    }
                }else{
                    if(orderMap.get("ddbh") != null){
                        orderDdbh = String.valueOf(orderMap.get("ddbh"));
                        orderDdbhList.add(orderDdbh);
                    }
                    resultOrderList.add(resultOrderMap);
                }

            }
            resultMap.put("ddbhList",orderDdbhList);
            resultMap.put("orderList",resultOrderList);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> queryOrderProgressInformation(String ddbh) {
        LinkedHashMap<String,Object> resultMap = new LinkedHashMap<String,Object>();
        List<Map<String,Object>> departmentProgressList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> orderList = btoWebMapper.queryOrderList(null,null,ddbh,null,null,null,null);
        if(orderList != null && orderList.size() > 0){
            Map<String,Object> orderMap = orderList.get(0);
            resultMap.put("ddbh",ddbh);
            resultMap.put("cpbh",orderMap.get("cpbh"));
            resultMap.put("cpmc",orderMap.get("cpmc"));
            resultMap.put("cpth",orderMap.get("cpth"));
            resultMap.put("cpcz",orderMap.get("cpcz"));
            resultMap.put("jhrq",orderMap.get("jhrq"));
            resultMap.put("pcsl",orderMap.get("pcsl"));
            String cptp = "";
            if(orderMap.get("cptp") != null){
                cptp = String.valueOf(orderMap.get("cptp"));
                if(!cptp.endsWith("jpg") && !cptp.endsWith("png")){
                    cptp = "default.png";
                }
            }
            cptp = "http://"+ SystemConstants.serviceInternetIp+":"+SystemConstants.serviceInternetPort+"/"+SystemConstants.serviceContextPath+"/images/"+cptp;
            resultMap.put("cptp",cptp);
        }

        List<RzBmxxb> rzBmxxbList = rzBmxxbService.selectByBmfl("2");
        if(rzBmxxbList != null && rzBmxxbList.size() > 0){
            for(RzBmxxb rzBmxxb : rzBmxxbList){
                String bmbh = rzBmxxb.getBm_bh();
                String bmmc = rzBmxxb.getBm_mc();
                Map<String,Object> bmOrderMap = new HashMap<String,Object>();
                bmOrderMap.put("bmbh",bmbh);
                bmOrderMap.put("bmmc",bmmc);
                bmOrderMap.put("ddbh",ddbh);
                String bmjjxh = rzBmxxb.getBm_jjxh();
                String jhwcsjFiled = "pc_jhwcsj" + bmjjxh;
                String scwcsjFiled = "pc_scwcsj" + bmjjxh;
                String scjjslFiled = "pc_pcsl" + bmjjxh;
                String scwcslFiled = "pc_scwcsl" + bmjjxh;
                String scfpslFiled = "pc_scfpsl" + bmjjxh;
                if(StringUtils.equals(bmjjxh,"1")){
                    scjjslFiled = "pc_pcsl";
                }else if(Integer.valueOf(bmjjxh) > 1){
                    scjjslFiled = "pc_scwcsl" + (Integer.valueOf(bmjjxh) - 1);
                }
                List<Map<String,Object>> bmOrderList = btoWebMapper.queryDepartmentOrderList(ddbh,jhwcsjFiled,scwcsjFiled,scjjslFiled,scwcslFiled,scfpslFiled);
                if(bmOrderList != null && bmOrderList.size() > 0){
                    String jhwcsj = "";
                    String scwcsj = "";
                    if(bmOrderList.get(0).get("jhwcsj") != null){
                        jhwcsj = String.valueOf(bmOrderList.get(0).get("jhwcsj"));
                    }
                    if(bmOrderList.get(0).get("scwcsj") != null){
                        scwcsj = String.valueOf(bmOrderList.get(0).get("scwcsj"));
                    }
                    String scjjsl = "";
                    if(bmOrderList.get(0).get("scjjsl") instanceof  Integer){
                        scjjsl = String.valueOf(bmOrderList.get(0).get("scjjsl"));
                    }else if(bmOrderList.get(0).get("scjjsl") instanceof  Double){
                        scjjsl = String.valueOf(new Double((Double) bmOrderList.get(0).get("scjjsl")).intValue());
                    }
                    String scwcsl = "0";
                    if(bmOrderList.get(0).get("scwcsl") != null){
                        scwcsl =  String.valueOf(bmOrderList.get(0).get("scwcsl"));
                    }
                    String scfpsl = "";
                    if(bmOrderList.get(0).get("scfpsl") != null){
                        scfpsl = String.valueOf(bmOrderList.get(0).get("scfpsl"));
                    }
                    String scsysl = "0";
                    if(StringUtils.isNotBlank(scjjsl)){
                        if(StringUtils.isNotBlank(scwcsl) && !StringUtils.equals(scwcsl,"0")){
                            scsysl = String.valueOf(Integer.valueOf(scjjsl) - Integer.valueOf(scwcsl));
                        }else{
                            scsysl = scjjsl;
                        }
                    }
                    bmOrderMap.put("jhwcsj",jhwcsj);
                    bmOrderMap.put("scwcsj",scwcsj);
                    bmOrderMap.put("scjjsl",scjjsl);
                    bmOrderMap.put("scwcsl",scwcsl);
                    bmOrderMap.put("scfpsl",scfpsl);
                    bmOrderMap.put("scsysl",scsysl);
                }
                departmentProgressList.add(bmOrderMap);
            }
        }
        resultMap.put("departmentProgressList",departmentProgressList);
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> queryProcessDetailList(String ddbh, String bmbh) {
        List<Map<String,Object>> processDetailList = new ArrayList<Map<String,Object>>();
        processDetailList = btoWebMapper.queryProcessDetailList(ddbh,bmbh);
        if(processDetailList != null && processDetailList.size() > 0){
            for(Map<String,Object> processDetailMap:processDetailList){
                String scpcsl = String.valueOf(processDetailMap.get("pcsl"));
                String scwcsl = String.valueOf(processDetailMap.get("wcsl"));
                String wcjd = "0";
                if(StringUtils.isNotBlank(scpcsl) && StringUtils.isNotBlank(scwcsl)){
                    if(!StringUtils.equals(scwcsl,"0")){
                        /**  创建一个数值格式化对象 **/
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        /** 设置精确到小数点后2位 **/
                        numberFormat.setMaximumFractionDigits(2);
                        if(StringUtils.isNotBlank(scpcsl) && Integer.valueOf(scpcsl) > 0){
                            wcjd = numberFormat.format(Float.parseFloat(scwcsl)/ Float.parseFloat(scpcsl)* 100);
                        }

                    }
                }
                processDetailMap.put("wcjd",wcjd+"%");
            }
        }
        return processDetailList;
    }

    @Override
    public Map<String, Object> queryOrderRecordInfo(String ddbh, String rybh) {
        List<ScJldjb> scJldjbList = scJldjbService.queryScjlbList(null,null,null,ddbh,null);
        if(scJldjbList !=null && scJldjbList.size() > 0){
            ScJldjb scJldjb = scJldjbList.get(0);
            ddbh = scJldjb.getDd_dddh();
        }
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> orderList = btoWebMapper.queryOrderRecordInfo(ddbh,rybh);
        if(orderList != null && orderList.size() > 0){
            result.putAll(orderList.get(0));
            String cptp = "";
            if(orderList.get(0).get("cptp") != null){
                cptp = String.valueOf(orderList.get(0).get("cptp"));
                if(!cptp.endsWith("jpg") && !cptp.endsWith("png")){
                    cptp = "default.png";
                }
            }
            cptp = "http://"+ SystemConstants.serviceInternetIp+":"+SystemConstants.serviceInternetPort+"/"+SystemConstants.serviceContextPath+"/images/"+cptp;
            result.put("cptp",cptp);
        }
        List<Map<String,Object>> orderProcessList = btoWebMapper.queryOrderProcessList(ddbh,rybh);
        String gxbh = "";
        if(orderProcessList != null && orderProcessList.size() > 0){
            result.put("orderProcessList",orderProcessList);
            gxbh = String.valueOf(orderProcessList.get(0).get("gxbh"));
        }
        if(StringUtils.isNotBlank(gxbh)){
            List<Map<String,Object>> jobProcessList = btoWebMapper.queryJobProcessList(ddbh,rybh,gxbh,null,null);
            if(jobProcessList != null && jobProcessList.size() > 0){
                result.put("jobProcessList",jobProcessList);
            }
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> queryJobProcessInfo(String ddbh, String rybh, String gxbh) {
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        resultList = btoWebMapper.queryJobProcessList(ddbh,rybh,gxbh,null,null);
        return resultList;
    }

    @Override
    public List<Map<String, Object>> queryCustomerList(String khbh) {
        List<Map<String,Object>> customerList = new ArrayList<Map<String,Object>>();
        customerList = btoWebMapper.queryCustomerList("1",khbh);
        if(customerList != null && customerList.size() > 0){
            for(Map<String,Object> customer:customerList){
                if(!customer.containsKey("khmc")){
                    customer.put("khmc","");
                }
            }
        }

        return customerList;
    }

    @Override
    public List<Map<String, Object>> queryPersonnelJobProcessList(String rybh) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        try {
            result = btoWebMapper.queryPersonnelJobProcessList(rybh);
        }catch (Exception e){
            logger.error("queryPersonnelJobProcessList error {}",e);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryRegisterJobProcessList(String ddbh, String bmbh, String rybh) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        try {
            result = btoWebMapper.queryRegisterJobProcessList(ddbh,bmbh,rybh);
        }catch (Exception e){
            logger.error("queryRegisterJobProcessList error {}",e);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryTrackNumberList(String ddbh, String cpbh) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        try{
            result = btoWebMapper.queryTrackNumberList(ddbh,cpbh);
        }catch (Exception e){
            logger.error("queryTrackingNumberList error {}",e);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryHandoverNumberList(String ddbh, String cpbh) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        try{
            result = btoWebMapper.queryHandoverNumberList(ddbh,cpbh);
        }catch (Exception e){
            logger.error("queryTrackingNumberList error {}",e);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryScrapReasonList(String ddbh,String rybh,String gxbh,String gzdh,String jjdh, String fclb) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        try{
            result = btoWebMapper.queryScrapReasons(ddbh,rybh,gxbh,fclb);
            if(result == null || result.size() ==0){
                if(StringUtils.equals(fclb,"2")){
                    result = btoWebMapper.queryScrapReasonList(gxbh,"0");
                }
                if(StringUtils.equals(fclb,"1")){
                    result = btoWebMapper.queryScrapReasonList(gxbh,"1");
                }

            }


        }catch (Exception e){
            logger.error("queryTrackingNumberList error {}",e);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryTrackOrderAmount(String ddbh, String cpbh,String gxbh, String gzdh) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        try{
            result = btoWebMapper.queryTrackOrderAmount(ddbh,cpbh,gxbh,gzdh);
        }catch (Exception e){
            logger.error("queryTrackingNumberList error {}",e);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryHandoverOrderAmount(String ddbh,String cpbh, String gxbh,String gzbh, String jjdh) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        try{
                List<Map<String,Object>> jjgxList = btoWebMapper.queryHandoverOrderProcessNumber(ddbh,cpbh,gxbh);
                if(jjgxList != null && jjgxList.size() > 0){
                    for (Map<String,Object> jjgxMap : jjgxList){
                        String jjgxbh = String.valueOf(jjgxMap.get("jjgx"));
                        if(StringUtils.isNotBlank(jjgxbh)){
                            if(StringUtils.isBlank(jjdh)){
                                if(StringUtils.equals(jjgxbh,"0000")){
                                    String wcsl = String.valueOf(jjgxMap.get("pcsl"));
                                    Map<String,Object> amountMap = new HashMap<String,Object>();
                                    amountMap.put("wcsl",wcsl);
                                    result.add(amountMap);
                                }else {
                                    result = btoWebMapper.queryTrackOrderAmount(ddbh,cpbh,jjgxbh,gzbh);
                                }
                            }else{
                                if(!StringUtils.equals(jjgxbh,"0000")){
                                    result = btoWebMapper.queryTrackOrderAmount(ddbh,cpbh,jjgxbh,jjdh);
                                }
                            }
                        }
                    }
                }
        }catch (Exception e){
            logger.error("queryHandoverOrderAmount error {}",e);
        }
        if(result == null || result.size() == 0){
            Map<String,Object> amountMap = new HashMap<String,Object>();
            amountMap.put("wcsl","0");
            result.add(amountMap);
        }
        return result;
    }

    @Override
    public Map<String, Object> queryProductionRegistrationOrderInfo(String ddbh, String rybh) {
        Map<String,Object> result = new ConcurrentHashMap<String,Object>();
        result.put("gzdhflag","0");
        try {
            List<ScJldjb> scJldjbList = scJldjbService.queryScjlbList(null,null,null,ddbh,null);
            if(scJldjbList !=null && scJldjbList.size() > 0){
                ScJldjb scJldjb = scJldjbList.get(0);
                ddbh = scJldjb.getDd_dddh();
                result.put("gzdhflag","1");
            }
            List<Map<String,Object>> orderList = btoWebMapper.queryProductionRegistrationOrderInfo(ddbh,rybh);
            if(orderList != null && orderList.size() > 0){
                for(Map<String,Object> orderMap:orderList){
                    result.putAll(orderMap);
                }
            }
        }catch (Exception e){
            logger.error("queryProductionRegistrationOrderInfo errorr {}",e);
        }
        return result;
    }

    @Override
    public RestResult updateProductionRegistrationOrderInfo(String djbh,String ddbh,String cpbh,String rybh,String bmbh, String gxbh, String gzdh, String jjdh, String wcsl, String fpsl, String cpsl) {
        Map<String, String> resultData = new HashMap<>();
        resultData.put("updateScjldjb","updateScjldjb");
        try {
            List<ScJldjb> scJldjbList = scJldjbService.getByDjbh(djbh);
            List<ScCpgxde> scCpgxdeList = scCpgxdeService.selectByDdbhAndGxbh(ddbh, cpbh, gxbh);
            List<ScGxzdb> scGxzdbList = scGxzdbService.selectByGxbh(gxbh);
            String gxlb = "";
            if (scGxzdbList != null && scGxzdbList.size() > 0) {
                ScGxzdb scGxzdb = scGxzdbList.get(0);
                gxlb = scGxzdb.getGx_lb();
            }
            String pcsl = "";
            if (StringUtils.isBlank(wcsl)) {
                wcsl = "0";
            }
            if (StringUtils.isBlank(fpsl)) {
                fpsl = "0";
            }
            if (StringUtils.isBlank(cpsl)) {
                cpsl = "0";
            }
            String zpsl = String.valueOf(Integer.valueOf(wcsl) - Integer.valueOf(fpsl) - Integer.valueOf(cpsl));

            if (StringUtils.equals(wcsl, "0")) {
                logger.error("ddbh {} gxbh {} error {}", ddbh, gxbh, "请输入正品、次品、废品数量！");

                resultData.put("msg", "请输入正品、次品、废品数量！");
                return ResultUtil.error(ResultCodeEnum.DATA_EXCEPTION, resultData);
            }
            if (scCpgxdeList != null && scCpgxdeList.size() > 0) {
                ScCpgxde scCpgxde = scCpgxdeList.get(0);
                String gxdejjsl = scCpgxde.getGx_jjsl();
                if (StringUtils.isBlank(gxdejjsl)) {
                    gxdejjsl = "0";
                }
                String gxdewcsl = scCpgxde.getGx_wcsl();
                String jjgxbh = scCpgxde.getYl1();
                List<ScJldjb> scJldjbs = scJldjbService.queryOtherGzdhScjldjbList(ddbh, null, jjgxbh, gzdh, null);
                if (scJldjbs != null && scJldjbs.size() > 0) {
                    int allGxwcsl = 0;
                    for (ScJldjb scJldjb : scJldjbs) {
                        if (StringUtils.isNotBlank(scJldjb.getDj_wcsl())) {
                            String jldjwcsl = scJldjb.getDj_wcsl();
                            allGxwcsl = allGxwcsl + Integer.valueOf(jldjwcsl);
                        }
                    }
                    allGxwcsl = allGxwcsl + Integer.valueOf(wcsl);
                    if (allGxwcsl > Integer.valueOf(gxdejjsl)) {
                        resultData.put("msg", "当前工序完成数为（" + allGxwcsl + "），已超过交接数（" + gxdejjsl + "）");
                        return ResultUtil.error(ResultCodeEnum.DATA_EXCEPTION, resultData);
                    }
                }
                if (!StringUtils.equals(gxlb, "2")) {
                    String jjgxwcsl = "0";
                    List<ScJldjb> jjgxJldjbList = scJldjbService.queryScjlbList(ddbh, null, jjgxbh, gzdh, null);
                    if (jjgxJldjbList != null && jjgxJldjbList.size() > 0) {
                        ScJldjb jjgxJldjb = jjgxJldjbList.get(0);
                        String jjgxzpsl = "0";
                        if (StringUtils.isNotBlank(jjgxJldjb.getDj_zpsl())) {
                            jjgxzpsl = jjgxJldjb.getDj_zpsl();
                        }
                        String jjgxcpsl = "0";
                        if (StringUtils.isNotBlank(jjgxJldjb.getDj_cpsl())) {
                            jjgxcpsl = jjgxJldjb.getDj_cpsl();
                        }
                        jjgxwcsl = String.valueOf(Integer.valueOf(jjgxzpsl) + Integer.valueOf(jjgxcpsl));
                        if (Integer.valueOf(wcsl) > Integer.valueOf(jjgxwcsl)) {
                            resultData.put("msg", "完成数（" + wcsl + "）已超过跟踪单的上一道工序的交接数（" + jjgxwcsl + "）");
                            return ResultUtil.error(ResultCodeEnum.DATA_EXCEPTION, resultData);
                        }
                    }
                }
                if (scJldjbList != null && scJldjbList.size() > 0) {
                    for (ScJldjb scJldjb : scJldjbList) {
                        String wclsl = "";
                        if (scCpgxdeList != null && scCpgxdeList.size() > 0) {
                            scJldjb.setGx_bmxh(scCpgxdeList.get(0).getGx_bmxh());
                            pcsl = scCpgxdeList.get(0).getGx_pcsl();
                        }

                        if (StringUtils.isNotBlank(pcsl)) {
                            wclsl = String.valueOf(Integer.valueOf(pcsl) - Integer.valueOf(wcsl));
                        }
                        UpdateWrapper<ScJldjb> scjldjbUpdateWrapper = new UpdateWrapper<>();
                        if (StringUtils.isNotBlank(zpsl)) {
                            scjldjbUpdateWrapper.set("DJ_ZPSL", zpsl);
                        }
                        if (StringUtils.isNotBlank(fpsl)) {
                            scjldjbUpdateWrapper.set("DJ_FPSL", fpsl);
                        }
                        if (StringUtils.isNotBlank(cpsl)) {
                            scjldjbUpdateWrapper.set("DJ_CPSL", cpsl);
                        }
                        if (StringUtils.isNotBlank(wcsl)) {
                            scjldjbUpdateWrapper.set("DJ_WCSL", wcsl);
                        }
                        if(StringUtils.isNotBlank(wclsl) ){
                            scjldjbUpdateWrapper.set("DJ_WCLSL",wclsl);
                        }
                        scjldjbUpdateWrapper.set("DJ_GZDH",gzdh);
                        if(StringUtils.isNotBlank(jjdh)){
                            scjldjbUpdateWrapper.set("DJ_CH",jjdh);
                        }
                        scjldjbUpdateWrapper.set("DJ_TJBZ", "0");
                        scjldjbUpdateWrapper.eq("DJ_BH", djbh);
                        scJldjbService.update(scjldjbUpdateWrapper);
                    }
                }
                List<ScScjlb> scScjlbFclbList = scScjlbService.selectByDjbhAndYybh(djbh, "0", null);
                if (scScjlbFclbList != null && scScjlbFclbList.size() > 0) {
                    UpdateWrapper<ScScjlb> scScjlbUpdateWrapper = new UpdateWrapper<>();
                    scScjlbUpdateWrapper.set("MX_SL", zpsl);
                    scScjlbUpdateWrapper.eq("DJ_BH", djbh);
                    scScjlbUpdateWrapper.eq("MX_FCLB", "0");
                    scScjlbService.update(scScjlbUpdateWrapper);
                } else {
                    ScScjlb scScjlb = new ScScjlb();
                    Calendar calendar = Calendar.getInstance();
                    String jlbh = keySdf.format(calendar.getTime());
                    scScjlb.setDj_jlbh(jlbh);
                    scScjlb.setDj_scrq(calendar.getTime());
                    scScjlb.setDj_bh(djbh);
                    scScjlb.setDd_dddh(ddbh);
                    scScjlb.setCp_cpbh(cpbh);
                    scScjlb.setBm_bh(bmbh);
                    scScjlb.setGx_bh(gxbh);
                    scScjlb.setRy_bh(rybh);
                    scScjlb.setMx_fclb("0");
                    scScjlb.setMx_sl(zpsl);
                    scScjlb.setMx_tjbz("1");
                    scScjlbService.insert(scScjlb);
                }
                List<ScScjlb> scScjlbList = scScjlbService.selectByDjbhAndGxbh(djbh, gxbh);
                if (scScjlbList != null && scScjlbList.size() > 0) {
                    for (ScScjlb scScjlb : scScjlbList) {
                        String tjbz = scScjlb.getMx_tjbz();
                        if (StringUtils.isNotBlank(tjbz) && !StringUtils.equals(tjbz, "1")) {
                            UpdateWrapper<ScScjlb> scscjlbpdateWrapper = new UpdateWrapper<>();
                            scscjlbpdateWrapper.set("MX_TJBZ", "1");
                            scscjlbpdateWrapper.eq("DJ_JLBH", scScjlb.getDj_jlbh());
                            scScjlbService.update(scscjlbpdateWrapper);
                        }
                    }

                }
                List<ScJldjb> gxJldjbList = scJldjbService.queryScjlbList(ddbh, null, gxbh, null, null);
                if (gxJldjbList != null && gxJldjbList.size() > 0) {
                    String gxwcsl = "0";
                    String gxfpsl = "0";
                    String gxcpsl = "0";
                    String gxjjsl = "0";
                    for (ScJldjb gxscJldjb : gxJldjbList) {
                        if (StringUtils.isNotBlank(gxscJldjb.getDj_wcsl())) {
                            gxwcsl = String.valueOf(Integer.valueOf(gxwcsl) + Integer.valueOf(gxscJldjb.getDj_wcsl()));
                        }
                        String jldjbcpsl = "0";
                        if (StringUtils.isNotBlank(gxscJldjb.getDj_cpsl())) {
                            jldjbcpsl = gxscJldjb.getDj_cpsl();
                            gxcpsl = String.valueOf(Integer.valueOf(gxcpsl) + Integer.valueOf(gxscJldjb.getDj_cpsl()));
                        }
                        if (StringUtils.isNotBlank(gxscJldjb.getDj_fpsl())) {
                            gxfpsl = String.valueOf(Integer.valueOf(gxfpsl) + Integer.valueOf(gxscJldjb.getDj_fpsl()));
                        }
                        String jldjbzpsl = "0";
                        if (StringUtils.isNotBlank(gxscJldjb.getDj_zpsl())) {
                            jldjbzpsl = gxscJldjb.getDj_zpsl();
                        }
                        gxjjsl = String.valueOf(Integer.valueOf(jldjbzpsl) + Integer.valueOf(jldjbcpsl) + Integer.valueOf(gxjjsl));
                    }
                    logger.info("scScgxde update ddbh {} gxbh {} scCpgxde {}", ddbh, gxbh, scCpgxde);
                    UpdateWrapper<ScCpgxde> sccpgxdeUpdateWrapper = new UpdateWrapper<>();
                    sccpgxdeUpdateWrapper.set("GX_WCSL", gxwcsl);
                    sccpgxdeUpdateWrapper.set("GX_FPSL", gxfpsl);
                    sccpgxdeUpdateWrapper.set("GX_CPSL", gxcpsl);
                    sccpgxdeUpdateWrapper.eq("DD_DDDH", ddbh);
                    sccpgxdeUpdateWrapper.eq("GX_BH", gxbh);
                    scCpgxdeService.update(sccpgxdeUpdateWrapper);

                    sccpgxdeUpdateWrapper = new UpdateWrapper<>();
                    sccpgxdeUpdateWrapper.set("GX_JJSL", gxjjsl);
                    sccpgxdeUpdateWrapper.eq("DD_DDDH", ddbh);
                    sccpgxdeUpdateWrapper.eq("YL1", gxbh);
                    scCpgxdeService.update(sccpgxdeUpdateWrapper);

                }
                resultData.put("msg", "登记成功");
                resultData.put("djbh", djbh);
                return ResultUtil.success(resultData);
            } else {
                logger.error("djbh {} ddbh {} gxbh {} ", djbh, ddbh, gxbh);
                removeByDjbhAndGxbh(djbh, ddbh, gxbh);
                resultData.put("msg", "工序定额信息为空！");
                return ResultUtil.error(ResultCodeEnum.DATA_EXCEPTION, resultData);
            }
        }catch (Exception e){
            logger.error("updateProductionRegistrationOrderInfo ddbh {} error {}",ddbh,e);
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }
    public void removeByDjbhAndGxbh(String djbh, String ddbh, String gxbh){
        QueryWrapper<ScJldjb> jldjbQueryWrapper = new QueryWrapper<ScJldjb>();
        jldjbQueryWrapper.eq("DJ_BH",djbh);
        jldjbQueryWrapper.eq("DJ_TJBZ","9");
        scJldjbService.remove(jldjbQueryWrapper);
        logger.info("delete scjldjb djbh {}",djbh);
        QueryWrapper<ScScjlb> scjlbWrapper = new QueryWrapper<ScScjlb>();
        scjlbWrapper.eq("DJ_BH",djbh);
        scjlbWrapper.eq("DD_DDDH",ddbh);
        scjlbWrapper.eq("GX_BH",gxbh);
        scjlbWrapper.eq("MX_TJBZ","9");
        scScjlbService.remove(scjlbWrapper);
    }

    @Override
    public RestResult scrapRegister(Map<String, Object> params) {
        if(!params.containsKey("djbh")){
            return ResultUtil.error(ResultCodeEnum.PARAM_ERROR);
        }
        if(!params.containsKey("scraps")){
            return ResultUtil.error(ResultCodeEnum.PARAM_ERROR);
        }
        if(!params.containsKey("gxbh")){
            return ResultUtil.error(ResultCodeEnum.PARAM_ERROR);
        }
        if(!params.containsKey("fclb")){
            return ResultUtil.error(ResultCodeEnum.PARAM_ERROR);
        }
        String djbh = String.valueOf(params.get("djbh"));
        String gxbh = String.valueOf(params.get("gxbh"));
        String fclb = String.valueOf(params.get("fclb"));
        List<ScJldjb> scJldjbList = scJldjbService.getByDjbh(djbh);
        if(scJldjbList != null  && scJldjbList.size() > 0){
            ScJldjb scJldjb = scJldjbList.get(0);
            String ddbh = scJldjb.getDd_dddh();
            String cpbh = scJldjb.getCp_cpbh();
            String bmbh = scJldjb.getBm_bh();
            String rybh = scJldjb.getRy_bh();
            List<Map<String,Object>> scraps = (List<Map<String, Object>>) params.get("scraps");
            try{
                if(scraps != null && scraps.size() > 0){
                    for(Map<String,Object> scrap:scraps){
                        String yy = String.valueOf(scrap.get("yy"));
                        String sl = String.valueOf(scrap.get("sl"));
                        List<ScScjlb> scScjlbList = scScjlbService.selectByDjbhAndYybh(djbh,fclb,yy);
                        if(scScjlbList !=null && scScjlbList.size() > 0){
                            for(ScScjlb scScjlb:scScjlbList){
                                UpdateWrapper<ScScjlb> scscjlbpdateWrapper = new UpdateWrapper<>();
                                scscjlbpdateWrapper.set("MX_SL",sl);
                                scscjlbpdateWrapper.eq("DJ_JLBH",scScjlb.getDj_jlbh());
                                scScjlbService.update(scscjlbpdateWrapper);

                            }
                        }else{
                            ScScjlb scScjlb = new ScScjlb();
                            Calendar calendar = Calendar.getInstance();
                            String jlbh = keySdf.format(calendar.getTime());
                            scScjlb.setDj_jlbh(jlbh);
                            scScjlb.setDj_scrq(calendar.getTime());
                            scScjlb.setDj_bh(djbh);
                            scScjlb.setDd_dddh(ddbh);
                            scScjlb.setCp_cpbh(cpbh);
                            scScjlb.setBm_bh(bmbh);
                            scScjlb.setGx_bh(gxbh);
                            scScjlb.setRy_bh(rybh);
                            scScjlb.setMx_fclb(fclb);
                            scScjlb.setMx_yy(yy);
                            scScjlb.setMx_sl(sl);
                            scScjlb.setMx_tjbz("9");
                            scScjlbService.insert(scScjlb);
                        }


                    }
                    return ResultUtil.success();
                }
            }catch (Exception e){
                logger.error("scrapRegister error {}",e);
                return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
            }
        }

        return ResultUtil.error(ResultCodeEnum.DATA_EXCEPTION);
    }

    @Override
    public RestResult cancelProductionRegistration(String djbh, String ddbh, String gxbh) {
        try{
            logger.info("delete scscjlb djbh {}",djbh);
            removeByDjbhAndGxbh(djbh,ddbh,gxbh);
            return ResultUtil.success("取消登记成功");
        }catch (Exception e){
            logger.error("cancelProductionRegistration  djbh {} error {}",djbh,e);
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @Override
    public RestResult verifyGzdh(String ddbh, String gzdh, String gxbh) {
        try{
            logger.info("verifyGzdh ddbh {} gzdh {} gxbh {}",ddbh,gzdh,gxbh);
            List<Map<String,String>> result = new ArrayList<Map<String,String>>();
            Map<String,String> map = new HashMap<>();
            List<ScJldjb> scJldjbList = scJldjbService.queryScjlbList(ddbh,null,null,gzdh,null);
            if(scJldjbList != null && scJldjbList.size() > 0){
                ScJldjb scJldjb = scJldjbList.get(0);
                map.put("verifyGzdhMsg","成功");
                map.put("wcsl",scJldjb.getDj_wcsl());
                map.put("fpsl",scJldjb.getDj_fpsl());
                map.put("cpsl",scJldjb.getDj_cpsl());

            }else{
                List<ScCpgxde> scCpgxdeList = scCpgxdeService.selectByDdbhAndGxbh(ddbh,null,gxbh);
                if(scCpgxdeList != null && scCpgxdeList.size() > 0){
                    ScCpgxde scCpgxde = scCpgxdeList.get(0);
                    String jjgxbh = scCpgxde.getYl1();
                    if(StringUtils.isNotBlank(jjgxbh)){
                        List<ScJldjb> scJldjbs = scJldjbService.queryScjlbList(ddbh,null,jjgxbh,gzdh,null);
                        if(scJldjbs != null && scJldjbs.size() > 0){
                            ScJldjb scJldjb = scJldjbList.get(0);
                            map.put("verifyGzdhMsg","成功");
                            map.put("wcsl",String.valueOf(Integer.valueOf(scJldjb.getDj_zpsl()) + Integer.valueOf(scJldjb.getDj_cpsl())));
                            map.put("fpsl",scJldjb.getDj_fpsl());
                            map.put("cpsl",scJldjb.getDj_cpsl());

                        }else{
                            List<ScGxzdb> scGxzdbList = scGxzdbService.selectByGxbh(gxbh);
                            if(scGxzdbList != null && scGxzdbList.size() > 0){
                                ScGxzdb scGxzdb = scGxzdbList.get(0);
                                String gxlb = scGxzdb.getGx_lb();
                                if(StringUtils.equals(gxlb,"2")){
                                    map.put("verifyGzdhMsg","成功");
                                    map.put("wcsl","0");
                                    map.put("fpsl","0");
                                    map.put("cpsl","0");
                                }else{
                                    map.put("verifyGzdhMsg","未找到跟踪单号对应的记录，请重新填写或识别跟踪单号");
                                }
                            }else{
                                map.put("verifyGzdhMsg","未找到跟踪单号对应的记录，请重新填写或识别跟踪单号");
                            }
                        }
                    }else{
                        map.put("verifyGzdhMsg","未找到跟踪单号对应的记录，请重新填写或识别跟踪单号");
                    }
                }else{
                    map.put("verifyGzdhMsg","未找到跟踪单号对应的记录，请重新填写或识别跟踪单号");
                }
            }
            result.add(map);
            return ResultUtil.success(result);
        }catch (Exception e){
            logger.error("verifyGzdh  ddbh {} error {}",ddbh,e);
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @Override
    public RestResult getGxzdInfo(String gxbh) {
        try{
            logger.info("getGxzdInfo  gxbh {}",gxbh);
            List<ScGxzdb> scGxzdbList = scGxzdbService.selectByGxbh(gxbh);
            return ResultUtil.success(scGxzdbList);
        }catch (Exception e){
            logger.error("getGxzdInfo  gxbh {} error {}",gxbh,e);
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @Override
    public List<Map<String, Object>> queryJobProcessList(String ddbh, String rybh, String gxbh, String gzdh, String jjdh) {
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        try {
            List<Map<String,Object>> jobProcessList = btoWebMapper.queryJobProcessList(ddbh, null, gxbh,gzdh,null);
            if(jobProcessList == null || jobProcessList.size() ==0){
                List<Map<String,Object>> orderList = btoWebMapper.queryProductionRegistrationOrderInfo(ddbh,rybh);
                if(orderList != null && orderList.size() > 0){
                    for(Map<String,Object> orderMap:orderList){
                        Map<String,Object> result = new ConcurrentHashMap<String,Object>();
                        ScJldjb scJldjb = new ScJldjb();
                        Calendar calendar = Calendar.getInstance();
                        String djbh = keySdf.format(calendar.getTime());
                        scJldjb.setDj_bh(djbh);
                        scJldjb.setDd_dddh(ddbh);
                        scJldjb.setRy_bh(rybh);
                        scJldjb.setDj_djr(rybh);
                        scJldjb.setDj_scrq(calendar.getTime());
                        String cpbh = String.valueOf(orderMap.get("cpbh"));
                        scJldjb.setCp_cpbh(cpbh);
                        String bmbh = String.valueOf(orderMap.get("bmbh"));
                        scJldjb.setBm_bh(bmbh);
                        scJldjb.setDj_tjbz("0");
                        scJldjb.setGx_bh(gxbh);
                        scJldjb.setDj_gzdh(gzdh);
                        if(StringUtils.isNotBlank(jjdh)){
                            scJldjb.setDj_ch(jjdh);
                        }
                        scJldjb.setDj_zpsl("0");
                        scJldjb.setDj_cpsl("0");
                        scJldjb.setDj_fpsl("0");
                        scJldjbService.insert(scJldjb);
                        result.put("djbh",djbh);
                        result.put("gzdh",gzdh);
                        result.put("gxbh",gxbh);
                        result.put("jjdh",jjdh);
                        result.put("rybh",rybh);
                        result.put("zpsl","0");
                        result.put("fpsl","0");
                        result.put("cpsl","0");
                        result.put("djrq",sdf.format(new Date()));
                        List<ScCpgxde> scCpgxdes = scCpgxdeService.selectByDdbhAndGxbh(ddbh,null,gxbh);
                        if(scCpgxdes != null && scCpgxdes.size() > 0){
                            ScCpgxde scCpgxde = scCpgxdes.get(0);
                            String jjgxbh = scCpgxde.getYl1();
                            if(StringUtils.isNotBlank(jjgxbh)){
                                List<ScJldjb> scJldjbList = scJldjbService.queryScjlbList(ddbh,null,jjgxbh,gzdh,null);
                                if(scJldjbList != null && scJldjbList.size() > 0){
                                    ScJldjb scJldjb1 = scJldjbList.get(0);
                                    String jjgxzpsl = "";
                                    if(StringUtils.isNotBlank(scJldjb1.getDj_zpsl())){
                                        jjgxzpsl = scJldjb1.getDj_zpsl();
                                    }else{
                                        jjgxzpsl = "0";
                                    }
                                    String jjgxcpsl = "";
                                    if(StringUtils.isNotBlank(scJldjb1.getDj_cpsl())){
                                        jjgxcpsl = scJldjb1.getDj_cpsl();
                                    }else{
                                        jjgxcpsl = "0";
                                    }
                                    String jjgxwcsl = String.valueOf(Integer.valueOf(jjgxzpsl) + Integer.valueOf(jjgxcpsl));
                                    result.put("wcsl",jjgxwcsl);
                                }
                            }
                            if(scCpgxde.getGx_jjsl() != null){
                                result.put("jjsl",scCpgxde.getGx_jjsl());
                            }else{
                                result.put("jjsl","0");
                            }
                        }else{
                            result.put("wcsl","0");
                            result.put("jjsl","0");
                        }
                        resultList.add(result);
                    }
                }
            }else{
                Map<String,Object> jobProcess = jobProcessList.get(0);
                List<ScCpgxde> scCpgxdes = scCpgxdeService.selectByDdbhAndGxbh(ddbh,null,gxbh);
                if(scCpgxdes != null && scCpgxdes.size() > 0){
                    ScCpgxde scCpgxde = scCpgxdes.get(0);
                    if(StringUtils.isNotBlank(scCpgxde.getGx_wcsl())){
                        jobProcess.put("wcsl",scCpgxde.getGx_wcsl());
                    }else{
                        jobProcess.put("wcsl","0");
                    }
                    if(StringUtils.isNotBlank(scCpgxde.getGx_jjsl())){
                        jobProcess.put("jjsl",scCpgxde.getGx_jjsl());
                    }else{
                        jobProcess.put("jjsl","0");
                    }

                    if(StringUtils.isNotBlank(scCpgxde.getGx_fpsl())){
                        jobProcess.put("fpsl",scCpgxde.getGx_fpsl());
                    }else{
                        jobProcess.put("fpsl","0");
                    }
                    if(StringUtils.isNotBlank(scCpgxde.getGx_cpsl())){
                        jobProcess.put("cpsl",scCpgxde.getGx_cpsl());
                    }else{
                        jobProcess.put("cpsl","0");
                    }
                    jobProcess.put("zpsl","0");
                }else{
                    jobProcess.put("wcsl","0");
                    jobProcess.put("jjsl","0");
                    jobProcess.put("zpsl","0");
                    jobProcess.put("cpsl","0");
                    jobProcess.put("fpsl","0");
                }
                resultList.add(jobProcess);
            }
        }catch (Exception e){
            logger.error("queryJobProcessList error {}",e);
        }
        return resultList;
    }

    /*
    判读时间差距，两个时间相差多少天，时，分，秒
     */
    public static Long getDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long days = null;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));//现在系统当前时间
            Date pastTime = dateFormat.parse(date);//过去时间
            long diff = currentTime.getTime() - pastTime.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
}
