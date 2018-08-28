package com.cctc.fast.common.utils.string;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * MD5加密
 * @author Hejeo
 *
 */
public class Md5Util {
	
	/**
     * 散列算法
     */
    public final static String HASH_ALGORITHM_NAME = "MD5";

    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = 2;
    
	/**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource  密码盐
     * @return
     */
	public static String md5(String credentials, String saltSource) {
        return new SimpleHash(HASH_ALGORITHM_NAME, credentials, saltSource, HASH_ITERATIONS).toHex();
    }
	
	/**
     * 获取随机盐值
     *
     * @param length 字节长度，一个字节2位16进制数表示
     * @return
     */
    public static String getRandomSalt(int length) {
        return new SecureRandomNumberGenerator().nextBytes(length).toHex();
    }
    
    
    public static void main(String[] args) {
    	String a = getRandomSalt(3);
    	System.out.println(a);
		System.out.println(md5("123456", a));
	}
}
