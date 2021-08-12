package com.supercode.bto.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/19 21:12
 */
@TableName("ZG_FCDZB")
public class ZgFcdzb extends Model<ZgFcdzb> {
    private static final long serialVersionUID=1L;
    /** 原因编号 **/
    private String yy_bh;
    /** 工序编号 **/
    private String gx_bh;
    /** 原因 **/
    private String yy;
    /** 岗位工序名称 **/
    private String gx_mc;
    /** 提交标志 **/
    private String tjbz;
    /** 部门编号 **/
    private String bm_bh;
    /** 检验部门 **/
    private String zrbm;

    public String getYy_bh() {
        return yy_bh;
    }

    public void setYy_bh(String yy_bh) {
        this.yy_bh = yy_bh;
    }

    public String getGx_bh() {
        return gx_bh;
    }

    public void setGx_bh(String gx_bh) {
        this.gx_bh = gx_bh;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    public String getGx_mc() {
        return gx_mc;
    }

    public void setGx_mc(String gx_mc) {
        this.gx_mc = gx_mc;
    }

    public String getTjbz() {
        return tjbz;
    }

    public void setTjbz(String tjbz) {
        this.tjbz = tjbz;
    }

    public String getBm_bh() {
        return bm_bh;
    }

    public void setBm_bh(String bm_bh) {
        this.bm_bh = bm_bh;
    }

    public String getZrbm() {
        return zrbm;
    }

    public void setZrbm(String zrbm) {
        this.zrbm = zrbm;
    }
}
