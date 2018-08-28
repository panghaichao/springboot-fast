package com.cctc.fast.core.base.controller;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.cctc.fast.core.base.service.IQiNiuService;
import com.cctc.fast.core.utils.Result;
import com.cctc.fast.core.utils.ResultUtil;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;

@RestController
public class UploadController {
	@Autowired
    private IQiNiuService qiNiuService;

	
	@RequestMapping(value ="/uploadAction",method={RequestMethod.POST,RequestMethod.GET})
	public Result<Object> uploadPic(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException{
		String oldName = file.getOriginalFilename();
		String fileName = changeName(oldName);
		Response res = qiNiuService.uploadFile(file.getInputStream(),fileName);
		DefaultPutRet putRet = JSON.parseObject(res.bodyString(),DefaultPutRet.class);
		return ResultUtil.success(putRet);
	}
	
	public static String changeName(String oldName){
		Random r = new Random();
		Date d = new Date();
		String newName = oldName.substring(oldName.indexOf('.'));
		newName = r.nextInt(99999999) + d.getTime() + newName;
		return newName;
	}
}
