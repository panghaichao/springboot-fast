package com.cctc.fast.portal.business.member.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 会员等级表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_member_level")
public class MemberLevel extends SuperEntity<MemberLevel>{
	
	@Override
	protected Serializable pkVal() {
		return this.memberLevelId;
	}
	
	/**
	 * 主键
	 */
	@TableId(value = "member_level_id", type = IdType.INPUT)
	private String memberLevelId; 
	
	/**
	 * 会员等级名称
	 */
	@TableField(value="member_level_name")
	@NotNull(message="会员等级名称不能为空~~~")
	@Length(min = 4 , max = 10, message = "会员等级名称长度必须在{min} - {max} 之间")
	private String memberLevelName;
	
	
	/**
	 * 消费折扣
	 */
	@TableField(value="member_level_discount")
	private BigDecimal memberLevelDiscount;
	
	/**
	 * 升级所需积分
	 */
	@TableField(value="member_level_point")
	@Min(value=0,message="升级积分必须大于0")
	private Integer memberLevelPoint;
	
	
	/**
	 * 排序
	 */
	@TableField(value="member_level_sort")
	private Integer memberLevelSort;
	
	
	/**
	 * 备注
	 */
	@TableField(value="remark")
	private String remark;


	public String getMemberLevelId() {
		return memberLevelId;
	}


	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}


	public String getMemberLevelName() {
		return memberLevelName;
	}


	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}


	public BigDecimal getMemberLevelDiscount() {
		return memberLevelDiscount;
	}


	public void setMemberLevelDiscount(BigDecimal memberLevelDiscount) {
		this.memberLevelDiscount = memberLevelDiscount;
	}


	public Integer getMemberLevelPoint() {
		return memberLevelPoint;
	}


	public void setMemberLevelPoint(Integer memberLevelPoint) {
		this.memberLevelPoint = memberLevelPoint;
	}


	public Integer getMemberLevelSort() {
		return memberLevelSort;
	}


	public void setMemberLevelSort(Integer memberLevelSort) {
		this.memberLevelSort = memberLevelSort;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
