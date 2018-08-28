package com.cctc.fast.core.base.service.impl;

import java.io.File;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cctc.fast.core.base.service.IQiNiuService;
import com.cctc.fast.core.properties.QiNiuProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

@Service
public class QiNiuServiceImpl implements IQiNiuService{
	
	@Autowired
    private UploadManager uploadManager;
	
	@Autowired
    private BucketManager bucketManager;
	
	@Autowired
    private Auth auth;
	
	@Autowired
    private QiNiuProperties qiNiuProperties;
	
	private StringMap putPolicy;

	@Override
	public Response uploadFile(File file) throws QiniuException {
		Response response  = this.uploadManager.put(file, null, getUploadToken());
		int retry = 0;
		while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, null, getUploadToken());
            retry++;
        }
		return response;
	}

	
	@Override
	public Response uploadFile(InputStream inputStream,String name) throws QiniuException {
		Response response = this.uploadManager.put(inputStream, name, getUploadToken(), null, null);
		int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(inputStream, name, getUploadToken(), null, null);
            retry++;
        }
        return response;
	}

	@Override
	public Response delete(String key) throws QiniuException {
		Response response = bucketManager.delete(qiNiuProperties.getBucket(), key);
        int retry = 0;
        while (response.needRetry() && retry++ < 3) {
            response = bucketManager.delete(qiNiuProperties.getBucket(), key);
        }
        return response;
	}
	
	/**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(qiNiuProperties.getBucket(), null, 3600, putPolicy);
    }

}
