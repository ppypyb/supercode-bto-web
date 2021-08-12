package com.supercode.bto.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO 记录登记表
 * @date 2021/7/19 8:37
 */
@TableName("SC_JLDJB")
public class ScJldjb extends Model<ScJldjb> {
    private static final long serialVersionUID=1L;
    /** 编号**/
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
    /** 登记序号**/
    private String gx_bmxh;
    /** 工序编号**/
    private String gx_bh;
    /** 生产工人**/
    private String ry_bh;
    /** 登记人**/
    private String dj_djr;
    /** 完成数量**/
    private String dj_wcsl;
    /** 正品数量**/
    private String dj_zpsl;
    /** 次品数量**/
    private String dj_cpsl;
    /** 废品数量**/
    private String dj_fpsl;
    /** 未处理数量**/
    private String dj_wclsl;
    /** 提交标志**/
    private String dj_tjbz;
    /** 跟踪单号**/
    private String dj_gzdh;
    /** 车号**/
    private String dj_ch;
    /** 炉号**/
    private String dj_lh;
    /** 备注**/
    private String bz;
    /** 预留**/
    private String yl1;
    /** 预留**/
    private String yl2;

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

    public String getGx_bmxh() {
        return gx_bmxh;
    }

    public void setGx_bmxh(String gx_bmxh) {
        this.gx_bmxh = gx_bmxh;
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

    public String getDj_djr() {
        return dj_djr;
    }

    public void setDj_djr(String dj_djr) {
        this.dj_djr = dj_djr;
    }

    public String getDj_wcsl() {
        return dj_wcsl;
    }

    public void setDj_wcsl(String dj_wcsl) {
        this.dj_wcsl = dj_wcsl;
    }

    public String getDj_zpsl() {
        return dj_zpsl;
    }

    public void setDj_zpsl(String dj_zpsl) {
        this.dj_zpsl = dj_zpsl;
    }

    public String getDj_fpsl() {
        return dj_fpsl;
    }

    public void setDj_fpsl(String dj_fpsl) {
        this.dj_fpsl = dj_fpsl;
    }

    public String getDj_wclsl() {
        return dj_wclsl;
    }

    public void setDj_wclsl(String dj_wclsl) {
        this.dj_wclsl = dj_wclsl;
    }

    public String getDj_tjbz() {
        return dj_tjbz;
    }

    public void setDj_tjbz(String dj_tjbz) {
        this.dj_tjbz = dj_tjbz;
    }

    public String getDj_gzdh() {
        return dj_gzdh;
    }

    public void setDj_gzdh(String dj_gzdh) {
        this.dj_gzdh = dj_gzdh;
    }

    public String getDj_ch() {
        return dj_ch;
    }

    public void setDj_ch(String dj_ch) {
        this.dj_ch = dj_ch;
    }

    public String getDj_lh() {
        return dj_lh;
    }

    public void setDj_lh(String dj_lh) {
        this.dj_lh = dj_lh;
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

    public String getDj_cpsl() {
        return dj_cpsl;
    }

    public void setDj_cpsl(String dj_cpsl) {
        this.dj_cpsl = dj_cpsl;
    }
}
