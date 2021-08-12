package com.supercode.bto.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supercode.bto.web.entity.RzBmxxb;

import java.util.List;

/**
 * @description: TODO
 * @author pengyongbo
 * @date 2021/7/8 20:54
 * @version 1.0
 */
public interface IRzBmxxbService extends IService<RzBmxxb> {

    public List<RzBmxxb> selectByBmbh(String bmbh);

    public List<RzBmxxb> selectByBmmc(String bmmc);

    public List<RzBmxxb> selectByBmfl(String bmfl);



}
