package com.supercode.bto.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

    private SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd");
    /** 表主键**/
    private SimpleDateFormat keySdf = new SimpleDateFormat("yyyyMMddHHmmss");

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
        /** 执行中订单**/
        List<Map<String,Object>> inExecutionList = new ArrayList<Map<String,Object>>();
        /** 临期订单**/
        List<Map<String,Object>> adventOrderList = new ArrayList<Map<String,Object>>();
        /** 逾期订单**/
        List<Map<String,Object>> overdueOrderList = new ArrayList<Map<String,Object>>();
        /** 已完成订单**/
        List<Map<String,Object>> completedList = new ArrayList<Map<String,Object>>();

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
                    if(!cptp.endsWith("jpg") && !cptp.endsWith("png")){
                        cptp = "default.png";
                    }
                }
                resultOrderMap.put("cptp",cptp);


                if(orderMap.get("jhrq") != null){
                    orderJhrq = String.valueOf(orderMap.get("jhrq"));
                }
                if(orderMap.get("ddzt") != null){
                    orderZt = String.valueOf(orderMap.get("ddzt"));
                }
                if(StringUtils.equals(orderZt,"1")){
                    resultOrderMap.put("status","1");
                    orderStatus = "1";
                    if(StringUtils.isNotBlank(orderJhrq)){
                        try {
                            Date ddjhrqDate = sdf.parse(orderJhrq);
                            Date nowDate = new Date();
                            boolean flag = nowDate.getTime() >= ddjhrqDate.getTime();
                            if(flag){
                                /** 当前时间大于计划交货时间称为逾期**/
                                overdueOrderList.add(orderMap);
                                resultOrderMap.put("status","3");
                                orderStatus = "3";
                            }
                            /** 交货日期与当前时间相差7天之内为临期订单**/
                            Long diffDays = getDay(orderJhrq);
                            if(diffDays > 0 && diffDays < 8){
                                resultOrderMap.put("status","4");
                                adventOrderList.add(orderMap);
                                orderStatus = "4";
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                /** 已完成订单**/
                if(StringUtils.equals(orderZt,"2")){
                    completedList.add(orderMap);
                    resultOrderMap.put("status","2");
                    orderStatus = "2";
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
//            resultMap.put("inExecutionList",inExecutionList);
//            resultMap.put("adventList",adventOrderList);
//            resultMap.put("overdueList",overdueOrderList);
//            resultMap.put("completedList",completedList);
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
            resultMap.put("cptp","default.png");
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
                        wcjd = numberFormat.format(Float.parseFloat(scwcsl)/ Float.parseFloat(scpcsl)* 100);
                    }
                }
                processDetailMap.put("wcjd",wcjd+"%");
            }
        }
        return processDetailList;
    }

    @Override
    public Map<String, Object> queryOrderRecordInfo(String ddbh, String rybh) {
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> orderList = btoWebMapper.queryOrderRecordInfo(ddbh,rybh);
        if(orderList != null && orderList.size() > 0){
            result.putAll(orderList.get(0));
            result.put("cptp","default.png");
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
        try {
            List<Map<String,Object>> orderList = btoWebMapper.queryProductionRegistrationOrderInfo(ddbh,rybh);
            if(orderList != null && orderList.size() > 0){
                for(Map<String,Object> orderMap:orderList){
                    result.putAll(orderMap);
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
                    scJldjbService.insert(scJldjb);
                    result.put("djbh",djbh);
                }
            }
        }catch (Exception e){
            logger.error("queryProductionRegistrationOrderInfo errorr {}",e);
        }
        return result;
    }

    @Override
    public RestResult updateProductionRegistrationOrderInfo(String djbh,String ddbh,String cpbh,String rybh,String bmbh, String gxbh, String gzdh, String jjdh, String zpsl, String fpsl, String cpsl) {
        try{
//            List<ScJldjb> scJldjbList = scJldjbService.queryScjlbList(ddbh, rybh, gxbh, gzdh, jjdh);
            List<ScJldjb> scJldjbList = scJldjbService.getByDjbh(djbh);
            List<ScCpgxde> scCpgxdeList = scCpgxdeService.selectByDdbhAndGxbh(ddbh,gxbh);
            String pcsl = "";
            if(StringUtils.isBlank(zpsl)){
                zpsl = "0";
            }
            if(StringUtils.isBlank(fpsl)){
                fpsl = "0";
            }
            if(StringUtils.isBlank(cpsl)){
                cpsl = "0";
            }
            String wcsl = String.valueOf(Integer.valueOf(zpsl) + Integer.valueOf(fpsl) + Integer.valueOf(cpsl));
            if(scJldjbList != null && scJldjbList.size() > 0){
                for(ScJldjb scJldjb : scJldjbList){
                    scJldjb.setDd_dddh(ddbh);
                    scJldjb.setCp_cpbh(cpbh);
                    scJldjb.setBm_bh(bmbh);
                    scJldjb.setGx_bh(gxbh);
                    scJldjb.setRy_bh(rybh);
                    scJldjb.setDj_djr(rybh);
                    scJldjb.setDj_gzdh(gzdh);
                    scJldjb.setDj_ch(jjdh);
                    if(scCpgxdeList != null && scCpgxdeList.size() > 0) {
                        scJldjb.setGx_bmxh(scCpgxdeList.get(0).getGx_bmxh());
                        pcsl = scCpgxdeList.get(0).getGx_pcsl();
                    }
                    if(StringUtils.isNotBlank(zpsl) && !StringUtils.equals(zpsl,"0")){
                        scJldjb.setDj_zpsl(zpsl);
                    }
                    if(StringUtils.isNotBlank(fpsl) && !StringUtils.equals(fpsl,"0")){
                        scJldjb.setDj_fpsl(fpsl);
                    }
                    if(StringUtils.isNotBlank(cpsl) && !StringUtils.equals(cpsl,"0")){
                        scJldjb.setDj_cpsl(cpsl);
                    }

                    if(!StringUtils.equals(wcsl,"0")){
                        scJldjb.setDj_wcsl(wcsl);
                    }else{
                        scJldjb.setDj_wcsl("0");
                    }
                    if(StringUtils.isNotBlank(pcsl)){
                        String wclsl = String.valueOf(Integer.valueOf(pcsl) - Integer.valueOf(wcsl));
                        scJldjb.setDj_wclsl(wclsl);
                    }else{
                        scJldjb.setDj_wclsl("0");
                    }
                    scJldjb.setDj_tjbz("0");
                    UpdateWrapper<ScJldjb> scjldjbUpdateWrapper = new UpdateWrapper<>();
                    scjldjbUpdateWrapper.eq("DJ_BH",djbh);
                    scJldjbService.update(scJldjb,scjldjbUpdateWrapper);
                }
            }
            /**
            else{
                ScJldjb scJldjb = new ScJldjb();
                Calendar calendar = Calendar.getInstance();
                djbh = keySdf.format(calendar.getTime());
                scJldjb.setDj_bh(djbh);
                scJldjb.setDd_dddh(ddbh);
                scJldjb.setRy_bh(rybh);
                scJldjb.setDj_djr(rybh);
                scJldjb.setDj_scrq(calendar.getTime());
                scJldjb.setCp_cpbh(cpbh);
                scJldjb.setBm_bh(bmbh);
                scJldjb.setGx_bh(gxbh);
                scJldjb.setDj_ch(jjdh);
                scJldjb.setDj_gzdh(gzdh);

                if(scCpgxdeList != null && scCpgxdeList.size() > 0) {
                    scJldjb.setGx_bmxh(scCpgxdeList.get(0).getGx_bmxh());
                    pcsl = scCpgxdeList.get(0).getGx_pcsl();
                }
                if(StringUtils.isNotBlank(zpsl) && !StringUtils.equals(zpsl,"0")){
                    scJldjb.setDj_zpsl(zpsl);
                }else{
                    scJldjb.setDj_zpsl("0");
                }
                if(StringUtils.isNotBlank(fpsl) && !StringUtils.equals(fpsl,"0")){
                    scJldjb.setDj_fpsl(fpsl);
                }else{
                    scJldjb.setDj_fpsl("0");
                }
                if(StringUtils.isNotBlank(cpsl) && !StringUtils.equals(cpsl,"0")){
                    scJldjb.setDj_cpsl(cpsl);
                }else{
                    scJldjb.setDj_fpsl("0");
                }
                if(!StringUtils.equals(wcsl,"0")){
                    scJldjb.setDj_wcsl(wcsl);
                }else{
                    scJldjb.setDj_wcsl("0");
                }
                if(StringUtils.isNotBlank(pcsl)){
                    String wclsl = String.valueOf(Integer.valueOf(pcsl) - Integer.valueOf(wcsl));
                    scJldjb.setDj_wclsl(wclsl);
                }else{
                    scJldjb.setDj_wclsl("0");
                }
                scJldjb.setDj_tjbz("0");
                scJldjbService.insert(scJldjb);
            } ***/

            List<ScScjlb> scScjlbList = scScjlbService.selectByDjbhAndGxbh(djbh,gxbh);
            if(scScjlbList != null && scScjlbList.size() > 0){
                for(ScScjlb scScjlb : scScjlbList){
                    scScjlb.setMx_tjbz("1");
                    scScjlb.setDj_bh(djbh);
                    UpdateWrapper<ScScjlb> scScjlbUpdateWrapper = new UpdateWrapper<>();
                    scScjlbUpdateWrapper.eq("DJ_JLBH", scScjlb.getDj_jlbh());
                    scScjlbService.update(scScjlb,scScjlbUpdateWrapper);
                    logger.info("update scscjlb jlbh {}",scScjlb.getDj_jlbh());
                }
            }
            /** 更新工序定额表**/
            ScCpgxde scCpgxde = scCpgxdeList.get(0);

            /** 查询工序完成数量**/
            List<Map<String,Object>> wcslList = btoWebMapper.queryProcessAmount(ddbh,gxbh,"0");
            if(wcslList != null && wcslList.size() > 0){
                String gxdeWcsl = String.valueOf(wcslList.get(0).get("sl"));
                if(StringUtils.isBlank(gxdeWcsl)){
                    gxdeWcsl = "0";
                }
                gxdeWcsl = String.valueOf(Integer.valueOf(gxdeWcsl) + Integer.valueOf(wcsl));
                scCpgxde.setGx_wcsl(gxdeWcsl);
            }
            /** 查询工序废品数量**/
            List<Map<String,Object>> fpslList = btoWebMapper.queryProcessAmount(ddbh,gxbh,"2");
            if(fpslList != null && fpslList.size() > 0){
                String gxdeFpsl = String.valueOf(fpslList.get(0).get("sl"));
                if(StringUtils.isBlank(gxdeFpsl)){
                    gxdeFpsl = "0";
                }
                gxdeFpsl = String.valueOf(Integer.valueOf(gxdeFpsl) + Integer.valueOf(fpsl));
                scCpgxde.setGx_fpsl(gxdeFpsl);
            }
            /** 查询工序废品数量**/
            List<Map<String,Object>> cpslList = btoWebMapper.queryProcessAmount(ddbh,gxbh,"1");
            if(cpslList != null && cpslList.size() > 0){
                String gxdeCpsl = String.valueOf(cpslList.get(0).get("sl"));
                if(StringUtils.isBlank(gxdeCpsl)){
                    gxdeCpsl = "0";
                }
                gxdeCpsl = String.valueOf(Integer.valueOf(gxdeCpsl) + Integer.valueOf(cpsl));
                scCpgxde.setGx_cpsl(gxdeCpsl);
            }

            logger.info("scScgxde update ddbh {} gxbh {} scCpgxde {}",ddbh,gxbh,scCpgxde);
            UpdateWrapper<ScCpgxde> sccpgxdeUpdateWrapper = new UpdateWrapper<>();
            sccpgxdeUpdateWrapper.eq("DD_DDDH", scCpgxde.getDd_dddh());
            sccpgxdeUpdateWrapper.eq("GX_BH", scCpgxde.getGx_bh());
            scCpgxdeService.update(scCpgxde,sccpgxdeUpdateWrapper);

            /** 查询工序交接数量**/
            List<Map<String,Object>> jjslList = btoWebMapper.queryProcessHandoverAmount(ddbh,gxbh);
            if(jjslList != null && jjslList.size() > 0){
                String gxdeJjsl = String.valueOf(jjslList.get(0).get("sl"));
                if(StringUtils.isBlank(gxdeJjsl)){
                    gxdeJjsl = "0";
                }
                gxdeJjsl = String.valueOf(Integer.valueOf(zpsl) + Integer.valueOf(gxdeJjsl) + Integer.valueOf(cpsl));
                ScCpgxde jjScCpgxde = new ScCpgxde();
                jjScCpgxde.setGx_jjsl(gxdeJjsl);
                jjScCpgxde.setDd_dddh(ddbh);
                jjScCpgxde.setGx_bh(gxbh);
                UpdateWrapper<ScCpgxde> sccpgxdeJJslUpdateWrapper = new UpdateWrapper<>();
                sccpgxdeJJslUpdateWrapper.eq("DD_DDDH", ddbh);
                sccpgxdeJJslUpdateWrapper.eq("YL1", gxbh);
                scCpgxdeService.update(jjScCpgxde,sccpgxdeJJslUpdateWrapper);
            }


            return ResultUtil.success(djbh);
        }catch (Exception e){
            logger.error("updateProductionRegistrationOrderInfo ddbh {} error {}",ddbh,e);
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
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
            List<Map<String,String>> scraps = (List<Map<String, String>>) params.get("scraps");
            try{
                if(scraps != null && scraps.size() > 0){
                    for(Map<String,String> scrap:scraps){
                        String yy = scrap.get("yy");
                        String sl = String.valueOf(scrap.get("sl"));
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
                        scScjlb.setMx_tjbz("0");
                        scScjlbService.insert(scScjlb);
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
            QueryWrapper<ScJldjb> jldjbQueryWrapper = new QueryWrapper<ScJldjb>();
            jldjbQueryWrapper.eq("DJ_BH",djbh);
            jldjbQueryWrapper.eq("DJ_TJBZ","0");
            scJldjbService.remove(jldjbQueryWrapper);
            logger.info("delete scjldjb djbh {}",djbh);
            QueryWrapper<ScScjlb> scjlbWrapper = new QueryWrapper<ScScjlb>();
            scjlbWrapper.eq("DJ_BH",djbh);
            scjlbWrapper.eq("DD_DDDH",ddbh);
            scjlbWrapper.eq("GX_BH",gxbh);
            scjlbWrapper.eq("MX_TJBZ","0");
            scScjlbService.remove(scjlbWrapper);
            logger.info("delete scscjlb djbh {}",djbh);
            return ResultUtil.success("取消登记成功");
        }catch (Exception e){
            logger.error("cancelProductionRegistration  djbh {} error {}",djbh,e);
            return ResultUtil.error(ResultCodeEnum.INSIDE_API_INVOKE_ERROR);
        }
    }

    @Override
    public List<Map<String, Object>> queryJobProcessList(String ddbh, String rybh, String gxbh, String gzdh, String jjdh) {
        List<Map<String,Object>> jobProcessList = new ArrayList<Map<String,Object>>();
        try {
            jobProcessList = btoWebMapper.queryJobProcessList(ddbh, rybh, gxbh,gzdh,jjdh);
        }catch (Exception e){
            logger.error("queryJobProcessList error {}",e);
        }
        return jobProcessList;
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
