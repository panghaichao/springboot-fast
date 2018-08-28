package com.cctc.fast.portal.business.member.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 会员卡信息
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_member_card")
public class MemberCard extends SuperEntity<MemberCard>{
	
	@Override
	protected Serializable pkVal() {
		return this.memberCardCode;
	}
	
	/**
	 * 主键
	 */
	@TableId(value = "member_card_id", type = IdType.INPUT)
	private String memberCardId; 
	
	/**
	 * 会员卡编号
	 */
	@TableField(value="member_card_code")
	private String memberCardCode;
	
	/**
	 * 会员卡级别
	 */
	@TableField(value="member_level_id")
	private String memberLevelId;
	
	/**
	 * 开卡日期
	 */
	@TableField(value="card_date")
	private Long cardDate;
	
	/**
	 * 推荐人编号
	 */
	@TableField(value="reference_id")
	private String referenceId;
	
	/**
	 * 卡余额
	 */
	@TableField(value="member_card_balance")
	private BigDecimal memberCardBalance;
	
	/**
	 * 总消费金额
	 */
	@TableField(value="total_consumption")
	private BigDecimal totalConsumption;
	
	
	/**
	 * 最后消费时间
	 */
	@TableField(value="last_consume_date")
	private Long lastConsumeDate;
	
	
	/**
	 * 总积分
	 */
	@TableField(value="total_point")
	private Integer totalPoint;
	
	/**
	 * 可用积分
	 */
	@TableField(value="usable_point")
	private Integer usablePoint;
	
	/**
	 * 会员卡状态
	 */
	@TableField(value="member_card_state")
	private String memberCardState;

	public String getMemberCardId() {
		return memberCardId;
	}

	public void setMemberCardId(String memberCardId) {
		this.memberCardId = memberCardId;
	}

	public String getMemberCardCode() {
		return memberCardCode;
	}

	public void setMemberCardCode(String memberCardCode) {
		this.memberCardCode = memberCardCode;
	}

	public String getMemberLevelId() {
		return memberLevelId;
	}

	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public Long getCardDate() {
		return cardDate;
	}

	public void setCardDate(Long cardDate) {
		this.cardDate = cardDate;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public Integer getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}

	public Integer getUsablePoint() {
		return usablePoint;
	}

	public void setUsablePoint(Integer usablePoint) {
		this.usablePoint = usablePoint;
	}

	public String getMemberCardState() {
		return memberCardState;
	}

	public void setMemberCardState(String memberCardState) {
		this.memberCardState = memberCardState;
	}

	public BigDecimal getMemberCardBalance() {
		return memberCardBalance;
	}

	public void setMemberCardBalance(BigDecimal memberCardBalance) {
		this.memberCardBalance = memberCardBalance;
	}

	public BigDecimal getTotalConsumption() {
		return totalConsumption;
	}

	public void setTotalConsumption(BigDecimal totalConsumption) {
		this.totalConsumption = totalConsumption;
	}

	public Long getLastConsumeDate() {
		return lastConsumeDate;
	}

	public void setLastConsumeDate(Long lastConsumeDate) {
		this.lastConsumeDate = lastConsumeDate;
	}
}
