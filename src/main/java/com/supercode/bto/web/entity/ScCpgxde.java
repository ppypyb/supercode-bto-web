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
@TableName("SC_CPGXDE")
public class ScCpgxde extends Model<ScCpgxde> {
    private static final long serialVersionUID=1L;
    /** 订单单号 **/
    private String dd_dddh;
    /** 产品编号 **/
    private String cp_cpbh;
    /** 岗位工序编号 **/
    private String gx_bh;
    /** 部门编号 **/
    private String bm_bh;
    /** 登记序号 **/
    private String gx_bmxh;
    /** 交接标志 **/
    private String gx_jjbz;
    /** 入库标志 **/
    private String gx_rkbz;
    /** 折工标志 **/
    private String gx_zgbz;
    /** 班组数 **/
    private String gx_gds;
    /** 工人数 **/
    private String gx_grs;
    /** 折工定额 **/
    private String gx_de;
    /** 单价 **/
    private String gx_zgs;
    /** 预留 **/
    private String yl1;
    /** 预留 **/
    private String yl2;
    /** 排产数量 **/
    private String gx_pcsl = "0";
    /** 完成数量 **/
    private String gx_wcsl = "0";
    /** 废品数量 **/
    private String gx_fpsl = "0";
    /** 次品数量 **/
    private String gx_cpsl = "0";
    /** 提交数量 **/
    private String gx_tjsl = "0";
    /** 剩余工时 **/
    private String gx_sygs = "0";
    /** 完成时间 **/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gx_wcsj;
    /** 生产交期 **/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gx_scjq;
    /** 默认工序 **/
    private String gx_mrbz;
    /** 未处理数量 **/
    private String gx_wclsl = "0";
    /** 交接数量 **/
    private String gx_jjsl = "0";

    private String hk_ssje;

    private String hbdw;

    private String hk_fph;

    private String gx_lb;

    private String gx_gws;

    private String gx_gdjjs;

    private String gx_zgdj;

    private String gx_bsc;

    private String gx_dejs;

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

    public String getGx_bmxh() {
        return gx_bmxh;
    }

    public void setGx_bmxh(String gx_bmxh) {
        this.gx_bmxh = gx_bmxh;
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

    public String getGx_zgbz() {
        return gx_zgbz;
    }

    public void setGx_zgbz(String gx_zgbz) {
        this.gx_zgbz = gx_zgbz;
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

    public String getGx_pcsl() {
        return gx_pcsl;
    }

    public void setGx_pcsl(String gx_pcsl) {
        this.gx_pcsl = gx_pcsl;
    }

    public String getGx_wcsl() {
        return gx_wcsl;
    }

    public void setGx_wcsl(String gx_wcsl) {
        this.gx_wcsl = gx_wcsl;
    }

    public String getGx_fpsl() {
        return gx_fpsl;
    }

    public void setGx_fpsl(String gx_fpsl) {
        this.gx_fpsl = gx_fpsl;
    }

    public String getGx_cpsl() {
        return gx_cpsl;
    }

    public void setGx_cpsl(String gx_cpsl) {
        this.gx_cpsl = gx_cpsl;
    }

    public String getGx_tjsl() {
        return gx_tjsl;
    }

    public void setGx_tjsl(String gx_tjsl) {
        this.gx_tjsl = gx_tjsl;
    }

    public String getGx_sygs() {
        return gx_sygs;
    }

    public void setGx_sygs(String gx_sygs) {
        this.gx_sygs = gx_sygs;
    }

    public Date getGx_wcsj() {
        return gx_wcsj;
    }

    public void setGx_wcsj(Date gx_wcsj) {
        this.gx_wcsj = gx_wcsj;
    }

    public Date getGx_scjq() {
        return gx_scjq;
    }

    public void setGx_scjq(Date gx_scjq) {
        this.gx_scjq = gx_scjq;
    }

    public String getGx_mrbz() {
        return gx_mrbz;
    }

    public void setGx_mrbz(String gx_mrbz) {
        this.gx_mrbz = gx_mrbz;
    }

    public String getGx_wclsl() {
        return gx_wclsl;
    }

    public void setGx_wclsl(String gx_wclsl) {
        this.gx_wclsl = gx_wclsl;
    }

    public String getGx_jjsl() {
        return gx_jjsl;
    }

    public void setGx_jjsl(String gx_jjsl) {
        this.gx_jjsl = gx_jjsl;
    }

    public String getHk_ssje() {
        return hk_ssje;
    }

    public void setHk_ssje(String hk_ssje) {
        this.hk_ssje = hk_ssje;
    }

    public String getHbdw() {
        return hbdw;
    }

    public void setHbdw(String hbdw) {
        this.hbdw = hbdw;
    }

    public String getHk_fph() {
        return hk_fph;
    }

    public void setHk_fph(String hk_fph) {
        this.hk_fph = hk_fph;
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

    public String getGx_gdjjs() {
        return gx_gdjjs;
    }

    public void setGx_gdjjs(String gx_gdjjs) {
        this.gx_gdjjs = gx_gdjjs;
    }

    public String getGx_zgdj() {
        return gx_zgdj;
    }

    public void setGx_zgdj(String gx_zgdj) {
        this.gx_zgdj = gx_zgdj;
    }

    public String getGx_bsc() {
        return gx_bsc;
    }

    public void setGx_bsc(String gx_bsc) {
        this.gx_bsc = gx_bsc;
    }

    public String getGx_dejs() {
        return gx_dejs;
    }

    public void setGx_dejs(String gx_dejs) {
        this.gx_dejs = gx_dejs;
    }
}
