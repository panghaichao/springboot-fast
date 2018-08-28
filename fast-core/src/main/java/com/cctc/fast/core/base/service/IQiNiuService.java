package com.cctc.fast.core.base.service;

import java.io.File;
import java.io.InputStream;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

/**
 * 
 * 七牛云服务
 * @author Hejeo
 *
 */
public interface IQiNiuService {
	/**
	 * 上传文件
	 * @param file
	 */
	Response uploadFile(File file) throws QiniuException;
	
	/**
	 * 上传文件 文件流上传
	 * @param inputStream
	 */
	Response uploadFile(InputStream inputStream,String name) throws QiniuException;
	
	/**
	 * 删除文件
	 * @param key
	 */
	Response delete(String key) throws QiniuException;
}
