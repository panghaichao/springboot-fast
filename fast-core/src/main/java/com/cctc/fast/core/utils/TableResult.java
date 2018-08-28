package com.cctc.fast.core.utils;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 
 * layui 表格返回结果数据对象
 * @author Hejeo
 *
 */
public class TableResult<T> {
	private Integer code = 0;
    private Integer count;
    private List<T> data;
    private String msg = "";
    
    public TableResult(Page<T> page){
    	this.data = page.getRecords();
    	this.count = (int) page.getTotal();
    	this.msg = "查询成功";
    }
    
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
