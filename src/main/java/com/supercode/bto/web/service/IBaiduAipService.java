package com.supercode.bto.web.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description: TODO
 * @author pengyongbo
 * @date 2021/8/1 12:02
 * @version 1.0
 */
public interface IBaiduAipService {

    /** 获取token**/
    public  String getAuth();

    /** 识别图片中数字**/

    public String nubmers(MultipartFile file);


}
