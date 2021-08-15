package com.supercode.bto.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO 生产记录表
 * @date 2021/7/18 11:09
 */
@TableName("SC_SCJLB")
public class ScScjlb extends Model<ScScjlb> {
    private static final long serialVersionUID=1L;

    /** 记录编号**/
    private String dj_jlbh;
    /** 登记编号**/
    private String dj_bh;
    /** 订单单号**/
    private String dd_dddh;
    /** 产品编号**/
    private String cp_cpbh;
    /** 生产日期**/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date dj_scrq;
    /** 部门编号**/
    private String bm_bh;
    /** 工序编号**/
    private String gx_bh;
    /** 生产工人**/
    private String ry_bh;
    /** 废次类别**/
    private String mx_fclb;
    /** 数量**/
    private String mx_sl = "0";
    /** 原因**/
    private String mx_yy;
    /** 提交标志**/
    private String mx_tjbz;
    /** 备注**/
    private String bz;
    /** 预留**/
    private String yl1;
    /** 预留**/
    private String yl2;

    private String cp_dz;

    public String getDj_jlbh() {
        return dj_jlbh;
    }

    public void setDj_jlbh(String dj_jlbh) {
        this.dj_jlbh = dj_jlbh;
    }

    public String getDj_bh() {
        return dj_bh;
    }

    public void setDj_bh(String dj_bh) {
        this.dj_bh = dj_bh;
    }

    public String getDd_dddh() {
        return dd_dddh;
    }

    public void setDd_dddh(String dd_dddh) {
        this.dd_dddh = dd_dddh;
    }

    public String getCp_cpbh() {
        return cp_cpbh;
    }

    public void setCp_cpbh(String cp_cpbh) {
        this.cp_cpbh = cp_cpbh;
    }

    public Date getDj_scrq() {
        return dj_scrq;
    }

    public void setDj_scrq(Date dj_scrq) {
        this.dj_scrq = dj_scrq;
    }

    public String getBm_bh() {
        return bm_bh;
    }

    public void setBm_bh(String bm_bh) {
        this.bm_bh = bm_bh;
    }

    public String getGx_bh() {
        return gx_bh;
    }

    public void setGx_bh(String gx_bh) {
        this.gx_bh = gx_bh;
    }

    public String getRy_bh() {
        return ry_bh;
    }

    public void setRy_bh(String ry_bh) {
        this.ry_bh = ry_bh;
    }

    public String getMx_fclb() {
        return mx_fclb;
    }

    public void setMx_fclb(String mx_fclb) {
        this.mx_fclb = mx_fclb;
    }

    public String getMx_sl() {
        return mx_sl;
    }

    public void setMx_sl(String mx_sl) {
        this.mx_sl = mx_sl;
    }

    public String getMx_yy() {
        return mx_yy;
    }

    public void setMx_yy(String mx_yy) {
        this.mx_yy = mx_yy;
    }

    public String getMx_tjbz() {
        return mx_tjbz;
    }

    public void setMx_tjbz(String mx_tjbz) {
        this.mx_tjbz = mx_tjbz;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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

    public String getCp_dz() {
        return cp_dz;
    }

    public void setCp_dz(String cp_dz) {
        this.cp_dz = cp_dz;
    }
}
