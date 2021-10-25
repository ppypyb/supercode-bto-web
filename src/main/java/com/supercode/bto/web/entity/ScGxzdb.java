package com.supercode.bto.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO 产品工序定额表
 * @date 2021/7/19 21:35
 */
@TableName("SC_GXZDB")
public class ScGxzdb extends Model<ScGxzdb> {
    private static final long serialVersionUID=1L;
    /** 岗位工序编号 **/
    private String gx_bh;
    /** 部门编号 **/
    private String bm_bh;
    /** 工序状态 **/
    private String gx_zt;
    /** 交接标志 **/
    private String gx_jjbz;
    /** 入库标志 **/
    private String gx_rkbz;
    /** 班组数 **/
    private String gx_gds;
    /** 工人数 **/
    private String gx_grs;
    /** 折工定额 **/
    private String gx_de;
    /** 折工定额 **/
    private String gx_zgbz;
    /** 单价 **/
    private String gx_zgs;
    /** 预留 **/
    private String yl1;
    /** 预留 **/
    private String yl2;
    /** 部门序号 **/
    private String gx_bmxh ;
    /** 工序类别 **/
    private String gx_lb ;
    /** 岗位数 **/
    private String gx_gws = "0";

    public String getGx_bh() {
        return gx_bh;
    }

    public void setGx_bh(String gx_bh) {
        this.gx_bh = gx_bh;
    }

    public String getBm_bh() {
        return bm_bh;
    }

    public void setBm_bh(String bm_bh) {
        this.bm_bh = bm_bh;
    }

    public String getGx_zt() {
        return gx_zt;
    }

    public void setGx_zt(String gx_zt) {
        this.gx_zt = gx_zt;
    }

    public String getGx_jjbz() {
        return gx_jjbz;
    }

    public void setGx_jjbz(String gx_jjbz) {
        this.gx_jjbz = gx_jjbz;
    }

    public String getGx_rkbz() {
        return gx_rkbz;
    }

    public void setGx_rkbz(String gx_rkbz) {
        this.gx_rkbz = gx_rkbz;
    }

    public String getGx_gds() {
        return gx_gds;
    }

    public void setGx_gds(String gx_gds) {
        this.gx_gds = gx_gds;
    }

    public String getGx_grs() {
        return gx_grs;
    }

    public void setGx_grs(String gx_grs) {
        this.gx_grs = gx_grs;
    }

    public String getGx_de() {
        return gx_de;
    }

    public void setGx_de(String gx_de) {
        this.gx_de = gx_de;
    }

    public String getGx_zgbz() {
        return gx_zgbz;
    }

    public void setGx_zgbz(String gx_zgbz) {
        this.gx_zgbz = gx_zgbz;
    }

    public String getGx_zgs() {
        return gx_zgs;
    }

    public void setGx_zgs(String gx_zgs) {
        this.gx_zgs = gx_zgs;
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

    public String getGx_bmxh() {
        return gx_bmxh;
    }

    public void setGx_bmxh(String gx_bmxh) {
        this.gx_bmxh = gx_bmxh;
    }

    public String getGx_lb() {
        return gx_lb;
    }

    public void setGx_lb(String gx_lb) {
        this.gx_lb = gx_lb;
    }

    public String getGx_gws() {
        return gx_gws;
    }

    public void setGx_gws(String gx_gws) {
        this.gx_gws = gx_gws;
    }
}
