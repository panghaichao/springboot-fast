package com.cctc.fast.portal.basic.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cctc.fast.core.base.entity.SuperEntity;

/**
 * 
 * 数据字典
 * @author Hejeo
 *
 */
@SuppressWarnings("serial")
@TableName(value = "t_dict_info")
public class DictInfo extends SuperEntity<DictInfo>{
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	/**
	 * 主键
	 */
	@TableId(value = "dict_id", type = IdType.INPUT)
	private String id; 
	
	/**
	 * 上级编号
	 */
	@TableField(value="p_dict_id")
	private String pDictId;
	
	/**
	 * 字典类型 0 类型 1 明细
	 */
	@TableField(value="dict_type")
	private String dictType;
	
	/**
	 * 字典编码
	 */
	@TableField(value="dict_code")
	private String dictCode;
	
	/**
	 * 字典名称
	 */
	@TableField(value="dict_name")
	private String name;
	
	/**
	 * 字典Key
	 */
	@TableField(value="dict_key")
	private String dictKey;
	
	/**
	 * 字典Val
	 */
	@TableField(value="dict_val")
	private String dictVal;
	
	/**
	 * 字典状态 0禁用1启用
	 */
	@TableField(value="dict_state")
	private String dictState;
	
	/**
	 * 排序
	 */
	@TableField(value="dict_sort")
	private Integer dictSort;

	@TableField(exist=false)
	private List<DictInfo> children=new ArrayList<DictInfo>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpDictId() {
		return pDictId;
	}

	public void setpDictId(String pDictId) {
		this.pDictId = pDictId;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public String getDictVal() {
		return dictVal;
	}

	public void setDictVal(String dictVal) {
		this.dictVal = dictVal;
	}

	public String getDictState() {
		return dictState;
	}

	public void setDictState(String dictState) {
		this.dictState = dictState;
	}

	public Integer getDictSort() {
		return dictSort;
	}

	public void setDictSort(Integer dictSort) {
		this.dictSort = dictSort;
	}

	public List<DictInfo> getChildren() {
		return children;
	}

	public void setChildren(List<DictInfo> children) {
		this.children = children;
	}
}
