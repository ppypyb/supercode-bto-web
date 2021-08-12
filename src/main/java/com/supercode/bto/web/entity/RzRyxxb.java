package com.supercode.bto.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO 人员信息表
 * @date 2021/7/8 20:34
 */
@TableName("RZ_RYXXB")
public class RzRyxxb extends Model<RzRyxxb> {
    private static final long serialVersionUID=1L;
    /** 人员编号**/
    private String ry_bh;
    /** 部门编号**/
    private String bm_bh;
    /** 岗位工序**/
    private String gx_bh;
    /** 姓名**/
    private String ry_xm;
    /** 性别**/
    private String ry_xb;
    /** 职务**/
    private String ry_zw;
    /** 登录密码**/
    private String ry_mm;
    /** 联系电话**/
    private String ry_lxdh;
    /** 身份证号**/
    private String ry_sfid;
    /** 民族**/
    private String ry_mz;
    /** 籍贯**/
    private String ry_jg;

    /** 家庭住址**/
    private String ry_jtdz;
    /** 紧急联系人**/
    private String ry_lxr;
    /** 联系人电话**/
    private String ry_lxrdh;
    /** 关系**/
    private String ry_lxrgx;
    /** 入职日期**/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date ry_rzrq;
    /** 入职方式**/
    private String ry_rzfs;
    /** 引荐人**/
    private String ry_yjr;
    /** 薪资标准**/
    private int ry_gzdy;
    /** 离职日期**/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date ry_lzrq;

    /** 在职状态**/
    private String ry_zt;
    /** 最高学历**/
    private String ry_zgxl;
    /** 教育背景**/
    private String ry_jybj;
    /** 技能特长**/
    private String ry_jntc;
    /** 教育背景**/
    private String ry_gzjl;

    private int ry_zsdj;

    private int ry_pxdj;

    private int ry_jfdj;

    private String ry_sfxx;

    private String bz;

    private String ry_pdjl;

    private String ry_qx;

    private String yl1;

    private String yl2;
    /** 离职原因**/
    private String ry_lzyy;


    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date ry_zzrq;

    private String ry_zp;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date ry_csrq;

    @TableField(exist = false)
    private String ry_scgx;

    private String ry_khbh;

    public String getRy_bh() {
        return ry_bh;
    }

    public void setRy_bh(String ry_bh) {
        this.ry_bh = ry_bh;
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

    public String getRy_xm() {
        return ry_xm;
    }

    public void setRy_xm(String ry_xm) {
        this.ry_xm = ry_xm;
    }

    public String getRy_xb() {
        return ry_xb;
    }

    public void setRy_xb(String ry_xb) {
        this.ry_xb = ry_xb;
    }

    public String getRy_zw() {
        return ry_zw;
    }

    public void setRy_zw(String ry_zw) {
        this.ry_zw = ry_zw;
    }

    public String getRy_mm() {
        return ry_mm;
    }

    public void setRy_mm(String ry_mm) {
        this.ry_mm = ry_mm;
    }

    public String getRy_lxdh() {
        return ry_lxdh;
    }

    public void setRy_lxdh(String ry_lxdh) {
        this.ry_lxdh = ry_lxdh;
    }

    public String getRy_sfid() {
        return ry_sfid;
    }

    public void setRy_sfid(String ry_sfid) {
        this.ry_sfid = ry_sfid;
    }

    public String getRy_mz() {
        return ry_mz;
    }

    public void setRy_mz(String ry_mz) {
        this.ry_mz = ry_mz;
    }

    public String getRy_jg() {
        return ry_jg;
    }

    public void setRy_jg(String ry_jg) {
        this.ry_jg = ry_jg;
    }

    public String getRy_jtdz() {
        return ry_jtdz;
    }

    public void setRy_jtdz(String ry_jtdz) {
        this.ry_jtdz = ry_jtdz;
    }

    public String getRy_lxr() {
        return ry_lxr;
    }

    public void setRy_lxr(String ry_lxr) {
        this.ry_lxr = ry_lxr;
    }

    public String getRy_lxrdh() {
        return ry_lxrdh;
    }

    public void setRy_lxrdh(String ry_lxrdh) {
        this.ry_lxrdh = ry_lxrdh;
    }

    public String getRy_lxrgx() {
        return ry_lxrgx;
    }

    public void setRy_lxrgx(String ry_lxrgx) {
        this.ry_lxrgx = ry_lxrgx;
    }

    public Date getRy_rzrq() {
        return ry_rzrq;
    }

    public void setRy_rzrq(Date ry_rzrq) {
        this.ry_rzrq = ry_rzrq;
    }

    public String getRy_rzfs() {
        return ry_rzfs;
    }

    public void setRy_rzfs(String ry_rzfs) {
        this.ry_rzfs = ry_rzfs;
    }

    public String getRy_yjr() {
        return ry_yjr;
    }

    public void setRy_yjr(String ry_yjr) {
        this.ry_yjr = ry_yjr;
    }

    public int getRy_gzdy() {
        return ry_gzdy;
    }

    public void setRy_gzdy(int ry_gzdy) {
        this.ry_gzdy = ry_gzdy;
    }

    public Date getRy_lzrq() {
        return ry_lzrq;
    }

    public void setRy_lzrq(Date ry_lzrq) {
        this.ry_lzrq = ry_lzrq;
    }

    public String getRy_zt() {
        return ry_zt;
    }

    public void setRy_zt(String ry_zt) {
        this.ry_zt = ry_zt;
    }

    public String getRy_zgxl() {
        return ry_zgxl;
    }

    public void setRy_zgxl(String ry_zgxl) {
        this.ry_zgxl = ry_zgxl;
    }

    public String getRy_jybj() {
        return ry_jybj;
    }

    public void setRy_jybj(String ry_jybj) {
        this.ry_jybj = ry_jybj;
    }

    public String getRy_jntc() {
        return ry_jntc;
    }

    public void setRy_jntc(String ry_jntc) {
        this.ry_jntc = ry_jntc;
    }

    public String getRy_gzjl() {
        return ry_gzjl;
    }

    public void setRy_gzjl(String ry_gzjl) {
        this.ry_gzjl = ry_gzjl;
    }

    public int getRy_zsdj() {
        return ry_zsdj;
    }

    public void setRy_zsdj(int ry_zsdj) {
        this.ry_zsdj = ry_zsdj;
    }

    public int getRy_pxdj() {
        return ry_pxdj;
    }

    public void setRy_pxdj(int ry_pxdj) {
        this.ry_pxdj = ry_pxdj;
    }

    public int getRy_jfdj() {
        return ry_jfdj;
    }

    public void setRy_jfdj(int ry_jfdj) {
        this.ry_jfdj = ry_jfdj;
    }

    public String getRy_sfxx() {
        return ry_sfxx;
    }

    public void setRy_sfxx(String ry_sfxx) {
        this.ry_sfxx = ry_sfxx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getRy_pdjl() {
        return ry_pdjl;
    }

    public void setRy_pdjl(String ry_pdjl) {
        this.ry_pdjl = ry_pdjl;
    }

    public String getRy_qx() {
        return ry_qx;
    }

    public void setRy_qx(String ry_qx) {
        this.ry_qx = ry_qx;
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

    public String getRy_lzyy() {
        return ry_lzyy;
    }

    public void setRy_lzyy(String ry_lzyy) {
        this.ry_lzyy = ry_lzyy;
    }

    public Date getRy_zzrq() {
        return ry_zzrq;
    }

    public void setRy_zzrq(Date ry_zzrq) {
        this.ry_zzrq = ry_zzrq;
    }

    public String getRy_zp() {
        return ry_zp;
    }

    public void setRy_zp(String ry_zp) {
        this.ry_zp = ry_zp;
    }

    public Date getRy_csrq() {
        return ry_csrq;
    }

    public void setRy_csrq(Date ry_csrq) {
        this.ry_csrq = ry_csrq;
    }

    public String getRy_scgx() {
        return ry_scgx;
    }

    public void setRy_scgx(String ry_scgx) {
        this.ry_scgx = ry_scgx;
    }

    public String getRy_khbh() {
        return ry_khbh;
    }

    public void setRy_khbh(String ry_khbh) {
        this.ry_khbh = ry_khbh;
    }
}
