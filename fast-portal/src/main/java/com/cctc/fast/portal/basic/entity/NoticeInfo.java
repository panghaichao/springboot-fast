package com.cctc.fast.portal.basic.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.common.enums.NoticeTypeEnum;
import com.cctc.fast.common.utils.id.KeyUtil;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 通知信息表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_notice_info")
public class NoticeInfo extends SuperEntity<NoticeInfo>{
	
	public NoticeInfo(){
		
	}
	
	/**
	 * 构造方法
	 * @param noticeType 通知类型
	 * @param primaryKey 关联编号
	 * @param noticeTitle 通知标题
	 * @param noticeContents 通知内容
	 */
	public NoticeInfo(NoticeTypeEnum noticeType,String primaryKey,String noticeTitle,String noticeContents){
		this.noticeId = KeyUtil.genUniqueKey();
		this.noticeType = noticeType.getCode();
		this.primaryKey = primaryKey;
		this.noticeTitle = noticeTitle;
		this.noticeContents = noticeContents;
	}
	
	@Override
	protected Serializable pkVal() {
		return this.noticeId;
	}
	
	/**
	 * 主键
	 */
	@TableId(value = "notice_id", type = IdType.INPUT)
	private String noticeId;
	
	
	/**
	 * 通知类型 0 类型 1 明细
	 */
	@TableField(value="notice_type")
	private Integer noticeType;
	
	
	/**
	 * 关联编号
	 */
	@TableField(value="primary_key")
	private String primaryKey;
	
	
	/**
	 * 通知标题
	 */
	@TableField(value="notice_title")
	private String noticeTitle;
	
	
	/**
	 * 通知内容
	 */
	@TableField(value="notice_contents")
	private String noticeContents;
	
	
	@TableField(exist=false)
	private Integer noticeStatus;
	
	@TableField(exist=false)
	private Long readTime;
	
	@TableField(exist=false)
	private String noticeDetailId;

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}


	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}


	public String getNoticeTitle() {
		return noticeTitle;
	}


	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}


	public String getNoticeContents() {
		return noticeContents;
	}


	public void setNoticeContents(String noticeContents) {
		this.noticeContents = noticeContents;
	}

	public Integer getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(Integer noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public Long getReadTime() {
		return readTime;
	}

	public void setReadTime(Long readTime) {
		this.readTime = readTime;
	}

	public String getNoticeDetailId() {
		return noticeDetailId;
	}

	public void setNoticeDetailId(String noticeDetailId) {
		this.noticeDetailId = noticeDetailId;
	}
	
	
	
}
