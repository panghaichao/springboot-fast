package com.cctc.fast.common.utils.string;

public class StrUtil {
	/**
	 * 数组中元素未找到的下标，值为-1
	 */
	public static final int INDEX_NOT_FOUND = -1;
	/**
	 * 空格
	 */
	public static final char C_SPACE = ' ';
	/**
	 * table
	 */
	public static final char C_TAB = '\t';
	/**
	 * 点
	 */
	public static final char C_DOT = '.';
	public static final char C_SLASH = '/';
	public static final char C_BACKSLASH = '\\';
	public static final char C_CR = '\r';
	public static final char C_LF = '\n';
	public static final char C_UNDERLINE = '_';
	public static final char C_COMMA = ',';
	public static final char C_DELIM_START = '{';
	public static final char C_DELIM_END = '}';
	public static final char C_BRACKET_START = '[';
	public static final char C_BRACKET_END = ']';
	public static final char C_COLON = ':';
	public static final String SPACE = " ";
	public static final String TAB = "\t";
	public static final String DOT = ".";
	public static final String DOUBLE_DOT = "..";
	public static final String SLASH = "/";
	public static final String BACKSLASH = "\\";
	public static final String EMPTY = "";
	public static final String CR = "\r";
	public static final String LF = "\n";
	public static final String CRLF = "\r\n";
	public static final String UNDERLINE = "_";
	public static final String DASHED = "-";
	public static final String COMMA = ",";
	public static final String DELIM_START = "{";
	public static final String DELIM_END = "}";
	public static final String BRACKET_START = "[";
	public static final String BRACKET_END = "]";
	public static final String COLON = ":";
	public static final String HTML_NBSP = "&nbsp;";
	public static final String HTML_AMP = "&amp;";
	public static final String HTML_QUOTE = "&quot;";
	public static final String HTML_APOS = "&apos;";
	public static final String HTML_LT = "&lt;";
	public static final String HTML_GT = "&gt;";
	public static final String EMPTY_JSON = "{}";

	/**
	 * 字符串是否为空白 
	 * 空白的定义如下： 
	 * 1、为null 
	 * 2、为不可见字符（如空格）
	 * 3、""
	 * @param str 字符串
	 * @return true/false
	 */
	public static boolean isBlank(CharSequence str) {
		int length;
		if ((str == null) || ((length = str.length()) == 0))
			return true;
		for (int i = 0; i < length; i++) {
			if (false == CharUtil.isBlankChar(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 字符串是否为空白 
	 * 空白的定义如下： 
	 * 1、为null 
	 * 2、为不可见字符（如空格）
	 * 3、""
	 * @param str 字符串
	 * @return true/false
	 */
	public static boolean isNotBlank(CharSequence str){
		return false == isBlank(str);
	}
	
	
	/**
	 * 是否包含空字符串
	 * @param strs 字符串列表
	 * @return true/false
	 */
	public static boolean hasBlank(CharSequence[] strs){
		if(ArrayUtil.isEmpty(strs)){
			return true;
		}
		for (CharSequence str : strs) {
			if (isBlank(str)) {
				return true;
			}
	    }
	    return false;
	}
	
	/**
	 * 给定所有字符串是否为空白
	 * @param strs 字符串
	 * @return true/false
	 */
	public static boolean isAllBlank(CharSequence[] strs){
		if (ArrayUtil.isEmpty(strs)){
			return true;
	    }
		for (CharSequence str : strs) {
			if (isNotBlank(str)) {
				return false;
			}
	    }
	    return true;
	}
	
	/**
	 * 当给定字符串为null时，转换为Empty
	 * @param str 被转换的字符串
	 * @return ""
	 */
	public static String nullToEmpty(CharSequence str){
		return nullToDefault(str, "");
	}
	
	/**
	 * 如果字符串是null，则返回指定默认字符串，否则返回字符串本身。
	 * @param str 要转换的字符串 
	 * @param defaultStr 默认字符串
	 * @return 字符串本身或指定的默认字符串 <BR/>
	 * nullToDefault(null, "default")  = "default"<BR/>
	 * nullToDefault("bat", "default") = "bat"
	 */
	public static String nullToDefault(CharSequence str, String defaultStr){
	    return str == null ? defaultStr : str.toString();
	}
	
	/**
	 * 除去字符串头尾部的空白，如果字符串是null，依然返回null。
	 * @param str 要处理的字符串
	 * @return 除去头尾空白的字符串，如果原字串为null，则返回null <BR/>
	 * trim(null)  = null <BR/>
	 * trim("  ")  = ""
	 */
	public static String trim(CharSequence str){
	    return null == str ? null : trim(str, 0);
	}

	/**
	 * 给定字符串数组全部做去首尾空格
	 * @param strs 要处理的字符串数组
	 */
	public static void trim(String[] strs){
		if (null == strs) {
			return;
		}
	    for (int i = 0; i < strs.length; i++) {
	    	String str = strs[i];
	    	if (null != str)
    		strs[i] = str.trim();
    	}
  	}
	
	/**
	 * 除去字符串头尾部的空白符，如果字符串是null，依然返回null。
	 * @param str 要处理的字符串
	 * @param mode -1表示trimStart，0表示trim全部， 1表示trimEnd
	 * @return
	 */
	public static String trim(CharSequence str, int mode){
	    if (str == null) {
	    	return null;
	    }

	    int length = str.length();
	    int start = 0;
	    int end = length;

	    if (mode <= 0) {
	    	while ((start < end) && (CharUtil.isBlankChar(str.charAt(start)))) {
	    		start++;
	    	}
	    }

	    if (mode >= 0) {
	    	while ((start < end) && (CharUtil.isBlankChar(str.charAt(end - 1)))) {
	    		end--;
	    	}
	    }

	    if ((start > 0) || (end < length)) {
	    	return str.toString().substring(start, end);
	    }
	    return str.toString();
	}
	
	public static boolean isEmpty(CharSequence str){
	    return (str == null) || (str.length() == 0);
	}
	

}
