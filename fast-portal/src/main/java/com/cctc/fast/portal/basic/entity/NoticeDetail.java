package com.cctc.fast.portal.basic.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.common.enums.NoticeStatusEnum;
import com.cctc.fast.common.utils.id.KeyUtil;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 通知信息明细表
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_notice_detail")
public class NoticeDetail extends SuperEntity<NoticeDetail>{
	
	public NoticeDetail(){
		
	}
	
	/**
	 * 构造方法
	 * @param noticeId 通知信息表编号
	 * @param userId 用户编号
	 */
	public NoticeDetail(String noticeId,String userId){
		this.noticeDetailId = KeyUtil.genUniqueKey();
		this.noticeId = noticeId;
		this.userId = userId;
		this.noticeStatus = NoticeStatusEnum.UNREAD.getCode();
	}
	
	
	@Override
	protected Serializable pkVal() {
		return this.noticeDetailId;
	}
	
	/**
	 * 主键
	 */
	@TableId(value = "notice_detail_id", type = IdType.INPUT)
	private String noticeDetailId;
	
	
	
	/**
	 * 通知信息表编号
	 */
	@TableField(value="notice_id")
	private String noticeId;
	
	
	/**
	 * 用户编号
	 */
	@TableField(value="user_id")
	private String userId;
	
	
	/**
	 * 消息状态 
	 */
	@TableField(value="notice_status")
	private Integer noticeStatus;
	
	
	/**
	 * 读取时间
	 */
	@TableField(value="read_time")
	private Long readTime;


	public String getNoticeDetailId() {
		return noticeDetailId;
	}


	public void setNoticeDetailId(String noticeDetailId) {
		this.noticeDetailId = noticeDetailId;
	}


	public String getNoticeId() {
		return noticeId;
	}


	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
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
	
}
