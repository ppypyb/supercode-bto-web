package com.supercode.bto.web.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/8/1 12:09
 */
@Component
public class SystemConstants {

    /** 百度 APP ID**/
    public static final String APP_ID = "24632186";

    /**
     * 百度 api_key
     */
    public static final String APP_KEY = "N7dYxSFqr0hy818S7fpBLdS7";

    /**
     * 百度 SECRET_KEY
     */
    public static final String SECRET_KEY  = "W7Nd40gwjFcHcq21RmjZ67oqQ4FnZnpB";

    /** 百度获取token URL**/
    public static final String authHostUrl = "https://aip.baidubce.com/oauth/2.0/token?";

    /** 数字识别 URL**/
    public static final String numbersUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/numbers";

    /** 通用文字识别URL**/
    public static final String generalBasicUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";



    /** 服务器地址**/
    public static String serviceInternetIp;
    /** 服务器端口号**/
    public static String serviceInternetPort;
    /** server.servlet.context-path**/
    public static String serviceContextPath;


    @Value("${service.Internet.ip}")
    public  void setServiceInternetIp(String serviceInternetIp) {
        SystemConstants.serviceInternetIp = serviceInternetIp;
    }
    @Value("${service.Internet.port}")
    public  void setServiceInternetPort(String serviceInternetPort) {
        SystemConstants.serviceInternetPort = serviceInternetPort;
    }

    @Value("${server.servlet.context-path}")
    public  void setServiceContextPath(String serviceContextPath) {
        SystemConstants.serviceContextPath = serviceContextPath;
    }



}
