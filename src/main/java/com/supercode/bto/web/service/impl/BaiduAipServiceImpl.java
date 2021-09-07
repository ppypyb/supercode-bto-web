package com.supercode.bto.web.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.util.Base64Util;
import com.supercode.bto.web.common.SystemConstants;
import com.supercode.bto.web.service.IBaiduAipService;


import com.supercode.bto.web.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/8/1 12:08
 */
@Service
public class BaiduAipServiceImpl implements IBaiduAipService   {

    private Logger logger = LoggerFactory.getLogger(BaiduAipServiceImpl.class);

    private static AipOcr aipOcrClient = null;

    @PostConstruct
    public void init(){
        aipOcrClient = new AipOcr(SystemConstants.APP_ID, SystemConstants.APP_KEY, SystemConstants.SECRET_KEY);
        //设置链接超时时间毫秒
        aipOcrClient.setConnectionTimeoutInMillis(10000);
        aipOcrClient.setSocketTimeoutInMillis(20000);
    }

    @Override
    public String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = SystemConstants.APP_KEY;
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = SystemConstants.SECRET_KEY;
        return getAuth(clientId, clientSecret);
    }

    @Override
    public String nubmers(MultipartFile file) {

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
        String jsonData = "";
        // 参数为本地图片二进制数组
        try {

            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth();
            String generalBasicResult = HttpUtil.post(SystemConstants.generalBasicUrl, accessToken, param);
//            JSONObject numberJsobj = aipOcrClient.basicGeneral(fileBytes, options);
            JSONObject numberJsobj = JSONObject.parseObject(generalBasicResult);
            logger.info("百度 numberJsobj {}",numberJsobj);
            if(numberJsobj.keySet().contains("words_result")){
                JSONArray jsonArray = numberJsobj.getJSONArray("words_result");
                List<Object> numberList = jsonArray.toJavaList(Object.class);
                if(numberList != null && numberList.size() > 0){
                    for(Object json:numberList){
                        if(json instanceof Map){
                            if(((Map<String, String>) json).containsKey("words")){
                                Map<String,String> jsonMap = (Map<String, String>) json;
                                String words = jsonMap.get("words");
                                if(!isContainChinese(words)){
                                    if(!StringUtils.contains(words,"NO")){
                                        jsonData = jsonData + words;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isBlank(jsonData)){
            jsonData = "0";
        }
        return jsonData;
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String getAccessTokenUrl = SystemConstants.authHostUrl
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    /**
     * 字符串是否包含中文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符 false 不包含中文字符
     * @throws
     */
    public static boolean isContainChinese(String str) {
        if(StringUtils.isNotBlank(str)){
            Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }
}
