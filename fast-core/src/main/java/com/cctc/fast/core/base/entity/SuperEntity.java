package com.cctc.fast.core.base.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;

public class SuperEntity<T extends Model<T>> extends Model<T>{
    
	private static final long serialVersionUID = 1L;

	/**
     * 创建时间
     */
    @TableField(value="create_time",fill=FieldFill.INSERT)
    private Long createTime;
    
    /**
     * 创建人
     */
    @TableField(value="create_user_id",fill=FieldFill.INSERT)
    private String createUserId;
   
   
    /**
    * 修改时间
    */
    @TableField(value="update_time",fill=FieldFill.UPDATE)
    private Long updateTime;
    
    /**
    * 修改人
    */
    @TableField(value="update_user_id",fill=FieldFill.UPDATE)
    private String updateUserId;
  
    
    /**
     * 软删除标识
     */
    @TableField(value = "delete_flag",fill=FieldFill.INSERT)
	@TableLogic
	private Integer deleteFlag;

	public Long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}


	public String getCreateUserId() {
		return createUserId;
	}


	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}


	public Long getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}


	public String getUpdateUserId() {
		return updateUserId;
	}


	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}


	public Integer getDeleteFlag() {
		return deleteFlag;
	}


	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}


	@Override
	protected Serializable pkVal() {
		return null;
	}
}
