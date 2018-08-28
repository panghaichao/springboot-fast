package com.cctc.fast.portal.business.member.vo;
import java.math.BigDecimal;
import com.cctc.fast.core.base.entity.SuperEntity;
import com.cctc.fast.portal.business.member.entity.MemberInfo;

@SuppressWarnings("serial")
public class MemberInfoVo extends SuperEntity<MemberInfo>{
	/**
	 * 主键
	 */
	private String memberId; 
	
	/**
	 * 会员卡编号
	 */
	private String memberCardCode;
	
	/**
	 * 会员姓名
	 */
	private String memberName;
	
	/**
	 * 会员性别
	 */
	private String memberSex;
	
	/**
	 * 手机号码
	 */
	private String memberPhone;
	
	/**
	 * 固定电话
	 */
	private String memberTel;
	
	/**
	 * 身份证号码
	 */
	private String identityCard;
	
	/**
	 * 出生日期
	 */
	private Long birthDate;
	
	/**
	 * 会员年龄
	 */
	private Integer memberAge;

	
	/**
	 * 电子邮箱
	 */
	private String emailAddress;
	
	/**
	 * 工作单位
	 */
	private String companyName;
	
	
	/**
	 * 省市区
	 */
	private String area;
	
	
	/**
	 * 详细地址
	 */
	private String address;
	
	
	/**
	 * 备注
	 */
	private String remark;
	
	
	
	
	/**
	 * 会员等级id
	 */
	private String memberLevelId;
	
	/**
	 * 会员等级名称
	 */
	private String memberLevelName;
	
	
	/**
	 * 开卡日期
	 */
	private Long cardDate;
	
	
	/**
	 * 推荐人编号
	 */
	private String referenceId;
	
	
	/**
	 * 推荐人姓名
	 */
	private String referenceName;
	
	
	/**
	 * 卡余额
	 */
	private BigDecimal memberCardBalance;
	
	/**
	 * 总消费金额
	 */
	private BigDecimal totalConsumption;
	
	
	/**
	 * 最后消费时间
	 */
	private Long lastConsumeDate;
	
	
	/**
	 * 总积分
	 */
	private Integer totalPoint;
	
	/**
	 * 可用积分
	 */
	private Integer usablePoint;
	
	/**
	 * 会员卡状态
	 */
	private String memberCardState;

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

	public String getMemberLevelId() {
		return memberLevelId;
	}

	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getMemberLevelName() {
		return memberLevelName;
	}

	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}

	public Long getCardDate() {
		return cardDate;
	}

	public void setCardDate(Long cardDate) {
		this.cardDate = cardDate;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
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
	
	
}
