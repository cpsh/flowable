package com.sun.health.flowable.mybatis_plus.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author SJ
 * @since 2018-05-09
 */
@TableName("p_person")
public class PPerson extends Model<PPerson> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "p_id", type = IdType.AUTO)
    private Long pId;
    @TableField("p_name")
    private String pName;
    @TableField("p_age")
    private Integer pAge;
    @TableField("p_gender")
    private String pGender;
    @TableField("p_address_country")
    private String pAddressCountry;
    @TableField("p_address_province")
    private String pAddressProvince;
    @TableField("p_address_city")
    private String pAddressCity;
    @TableField("p_address_county")
    private String pAddressCounty;
    @TableField("p_birthday")
    private Date pBirthday;


    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getpAge() {
        return pAge;
    }

    public void setpAge(Integer pAge) {
        this.pAge = pAge;
    }

    public String getpGender() {
        return pGender;
    }

    public void setpGender(String pGender) {
        this.pGender = pGender;
    }

    public String getpAddressCountry() {
        return pAddressCountry;
    }

    public void setpAddressCountry(String pAddressCountry) {
        this.pAddressCountry = pAddressCountry;
    }

    public String getpAddressProvince() {
        return pAddressProvince;
    }

    public void setpAddressProvince(String pAddressProvince) {
        this.pAddressProvince = pAddressProvince;
    }

    public String getpAddressCity() {
        return pAddressCity;
    }

    public void setpAddressCity(String pAddressCity) {
        this.pAddressCity = pAddressCity;
    }

    public String getpAddressCounty() {
        return pAddressCounty;
    }

    public void setpAddressCounty(String pAddressCounty) {
        this.pAddressCounty = pAddressCounty;
    }

    public Date getpBirthday() {
        return pBirthday;
    }

    public void setpBirthday(Date pBirthday) {
        this.pBirthday = pBirthday;
    }

    @Override
    protected Serializable pkVal() {
        return this.pId;
    }

    @Override
    public String toString() {
        return "PPerson{" +
        ", pId=" + pId +
        ", pName=" + pName +
        ", pAge=" + pAge +
        ", pGender=" + pGender +
        ", pAddressCountry=" + pAddressCountry +
        ", pAddressProvince=" + pAddressProvince +
        ", pAddressCity=" + pAddressCity +
        ", pAddressCounty=" + pAddressCounty +
        ", pBirthday=" + pBirthday +
        "}";
    }
}
