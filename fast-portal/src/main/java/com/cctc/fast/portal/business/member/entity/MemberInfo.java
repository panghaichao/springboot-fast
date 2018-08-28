package com.cctc.fast.portal.business.member.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 会员信息表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_member_info")
public class MemberInfo extends SuperEntity<MemberInfo>{
	
	@Override
	protected Serializable pkVal() {
		return this.memberId;
	}
	
	/**
	 * 主键
	 */
	@TableId(value = "member_id", type = IdType.INPUT)
	private String memberId; 
	
	/**
	 * 会员卡编号
	 */
	@TableField(value="member_card_code")
	private String memberCardCode;
	
	/**
	 * 会员姓名
	 */
	@TableField(value="member_name")
	private String memberName;
	
	/**
	 * 会员性别
	 */
	@TableField(value="member_sex")
	private String memberSex;
	
	/**
	 * 手机号码
	 */
	@TableField(value="member_phone")
	private String memberPhone;
	
	/**
	 * 固定电话
	 */
	@TableField(value="member_tel")
	private String memberTel;
	
	/**
	 * 身份证号码
	 */
	@TableField(value="identity_card")
	private String identityCard;
	
	/**
	 * 出生日期
	 */
	@TableField(value="birth_date")
	private Long birthDate;
	
	/**
	 * 会员年龄
	 */
	@TableField(value="member_age")
	private Integer memberAge;

	
	/**
	 * 电子邮箱
	 */
	@TableField(value="email_address")
	private String emailAddress;
	
	/**
	 * 工作单位
	 */
	@TableField(value="company_name")
	private String companyName;
	
	
	/**
	 * 省市区
	 */
	@TableField(value="area")
	private String area;

	
	/**
	 * 详细地址
	 */
	@TableField(value="address")
	private String address;
	
	
	/**
	 * 备注
	 */
	@TableField(value="remark")
	private String remark;


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getMemberCardCode() {
		return memberCardCode;
	}


	public void setMemberCardCode(String memberCardCode) {
		this.memberCardCode = memberCardCode;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getMemberSex() {
		return memberSex;
	}


	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex;
	}


	public String getMemberPhone() {
		return memberPhone;
	}


	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}


	public String getMemberTel() {
		return memberTel;
	}


	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}


	public String getIdentityCard() {
		return identityCard;
	}


	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}


	public Long getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Long birthDate) {
		this.birthDate = birthDate;
	}


	public Integer getMemberAge() {
		return memberAge;
	}


	public void setMemberAge(Integer memberAge) {
		this.memberAge = memberAge;
	}


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
