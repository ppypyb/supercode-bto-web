package com.supercode.bto.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO 部门信息表
 * @date 2021/7/17 10:55
 */
@TableName("RZ_BMXXB")
public class RzBmxxb extends Model<RzBmxxb> {
    private static final long serialVersionUID=1L;

    /** 部门编号**/
    private String bm_bh;
    /** 部门名称**/
    private String bm_mc;
    /** 部门简称**/
    private String bm_jc;
    /** 部门分类**/
    private String bm_fl;
    /** 交接序号**/
    private String bm_jjxh;
    /** 系统编号**/
    private String bm_xtbh;
    /** 预留**/
    private String yl1;
    /** 预留**/
    private String yl2;

    public String getBm_bh() {
        return bm_bh;
    }

    public void setBm_bh(String bm_bh) {
        this.bm_bh = bm_bh;
    }

    public String getBm_mc() {
        return bm_mc;
    }

    public void setBm_mc(String bm_mc) {
        this.bm_mc = bm_mc;
    }

    public String getBm_jc() {
        return bm_jc;
    }

    public void setBm_jc(String bm_jc) {
        this.bm_jc = bm_jc;
    }

    public String getBm_fl() {
        return bm_fl;
    }

    public void setBm_fl(String bm_fl) {
        this.bm_fl = bm_fl;
    }

    public String getBm_jjxh() {
        return bm_jjxh;
    }

    public void setBm_jjxh(String bm_jjxh) {
        this.bm_jjxh = bm_jjxh;
    }

    public String getBm_xtbh() {
        return bm_xtbh;
    }

    public void setBm_xtbh(String bm_xtbh) {
        this.bm_xtbh = bm_xtbh;
    }

    public String getYl1() {
        return yl1;
    }

    public void setYl1(String yl1) {
        this.yl1 = yl1;
    }

    public String getYl2() {
        return yl2;
    }

    public void setYl2(String yl2) {
        this.yl2 = yl2;
    }
}
