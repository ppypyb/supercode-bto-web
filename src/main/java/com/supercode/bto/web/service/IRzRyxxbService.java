package com.supercode.bto.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supercode.bto.web.entity.RzRyxxb;

import java.util.List;

/**
 * @description: TODO
 * @author pengyongbo
 * @date 2021/7/8 20:54
 * @version 1.0
 */
public interface IRzRyxxbService extends IService<RzRyxxb> {

    public List<RzRyxxb> selectByRybh(String rybh);

    public List<RzRyxxb> selectByRyxm(String ryxm);



}
