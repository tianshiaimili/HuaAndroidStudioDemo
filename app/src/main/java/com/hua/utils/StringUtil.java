package com.hua.utils;

import android.content.Context;
import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串操作类
 * 
 * @author hejinxi
 * 
 */
public class StringUtil {

	

	/**
	 * 如果如果内容为空的话，返回""
	 * 
	 * @param content
	 * @return
	 */
	public static String isEmpty(String content) {
		if (TextUtils.isEmpty(content)) {
			return "";
		} else {
			return content;
		}
	}

	
	/**
	 * 如果如果内容为空的话，返回true
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isNull(String content) {
		if (TextUtils.isEmpty(content)) {
			return true;
		} else {
			if ("null".equals(content.trim().toLowerCase())) {
				return true;
			} else {
				return false;
			}

		}

	}
	/**
	 * 如果如果内容为空或为零的话，返回true
	 * 
	 * @param content
	 * @return
	 */
	public static boolean valueOfZero(String content) {
		 if(!isNull(content)){
			 if("0".equals(content)){
				 return true;
			 }else{
				 return false;
			 }
		 }else{
			 return true;
		 }
		
	}

	/**
	 * 将字符串转换为整形
	 * @param value
	 * @return
	 */
	public static int integerValueOf(String value){
		 if(!isNull(value)){
			 return Integer.valueOf(value);
		 }else{
			 return 0;
		 }
	}
	/**
	 * 验证连接是否有http：//开头的
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrlLegal(String url) {
		if (!isNull(url)) {
			if (url.indexOf("http") == -1) {
				url = "http://" + url;
				return url;
			} else {
				return url;
			}
		} else {
			return null;
		}
	}

	public static String getUrlTaoBao(String url) {
		if (!isNull(url)) {
			if (url.indexOf("taobao://") != -1) {
//				url = UrlPath.TAOBAOURL;
			}
		}
		return url;
	}
	public static boolean isUrlTaoBao(String url) {
		boolean flag = false;
		if (!isNull(url)) {
			if (url.indexOf("taobao://") != -1) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 检查URL是 否合法
	 * 
	 * @param value
	 * @return
	 */
	public static boolean checkURL(String value) {
		return value
				.matches("(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?");
	}

	/**
	 * 截取微信分享内容
	 * @param share_content
	 * @param msg_temp
	 * @return
	 */
	public static String getWeixinContent(String share_content,String msg_temp){
		if (isNull(share_content)) {
			String content  = msg_temp
					.replaceAll("\\[img\\]([^\\[\\]]+?)\\[\\/img\\]", "")
					.replace("\\n", "")
					.replaceAll("<img[^>]+?>", "")
					.replace("<br/>", "")
					.replace("&nbsp;", "")
					.replace("</img>", "");
			if (content.length()>300) {
				share_content = content.substring(0,290);
			}else{
				share_content = content;
			}
		}
		return share_content;
	}
	/**
	 * 转换成MD5
	 * 
	 * @param source
	 * @return
	 */
	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int chineseLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 把一个字符串中的大写字母转换成小写，小写字母转换成大写 第二种方法
	 * 
	 * @param s
	 *            传入的字符串
	 * @return 返回转换后的字符串
	 */
	public static String StringChange(String s) {
		if (s.equals("") || s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		int len = s.length();
		char c;

		for (int i = 0; i < len; i++) {
			c = s.charAt(i);
			if (c >= 'a' && c <= 'z') {
				c = (char) (c - 32);
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/*
	 * 全角转成半角 中文标点转成英文标点
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/*
	 * 半角转化为全角
	 */
	public static String ToSBC(String input) {
		// 半角转全角：
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {

			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127 && c[i] > 32)
				c[i] = (char) (c[i] + 65248);

		}
		return new String(c);
	}

	/**
	 * 得到中文首字母
	 * 
	 * @param str
	 * @return
	 */
//	public static String getPinYinHeadChar(String str) {
//
////		String convert = "";
////		for (int j = 0; j < str.length(); j++) {
////			char word = str.charAt(j);
////
////			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
////			if (pinyinArray != null) {
////				convert += pinyinArray[0].charAt(0);
////			} else {
////				convert += word;
////			}
////		}
//		return PinyinHelper.toHanyuPinyinStringArray(str);
//	}

	
	/**
	 * 禁止上传图片的fid
	 * @param fid
	 * @return
	 */
//	public static boolean ForBidUploadFid(String fid){
////		if(!StringUtil.isNull(fid)){
////			List<String> upload = ActionContace.uploadFids;
////			for (String fids : upload) {
////				if(fid.equals(fids)){
////					return true;
////				}
////			}
////		}
////		return false;
//
//	}
	
	/**
	 * 过滤发布图片内容
	 * @param content
	 * @return
	 */
	public static String  filterImage(boolean fid,String content){
		if(fid){
			if(!StringUtil.isNull(content)){
				content= content.replaceAll("\\[attachimg\\]([^\\[\\]]+?)\\[\\/attachimg\\]", "");
			}
		}
		
		return content;
	}
	

	
	public static boolean isHashNull(String hash){
		if(isNull(hash)){
			return true;
		}else{
			return false;
		}
	}
	





	/**
	 * 
	 * @param temp
	 * @return
	 */
	public  static  Map<String, Object>  hashUids(Map<String, Object> temp){
		
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, Object> entry : temp.entrySet()) {
			String key = entry.getKey().toString();
			String value = null;
			if(entry.getValue()!=null){
				value = entry.getValue().toString();
			}
			if("uid".equals(key)){
				if(isNull(value)){
					value = "0";
				}
			}
			if("hash".equals(key)){
				if(isHashNull(value)){
					continue;
				}
			}
			map.put(key, value);
			
		}
		
		return map;
		
	}
	
	/**
	 * 判断List是否为null
	 * @param list
	 * @return 不为空和条数大于零返回true
	 */
	public static boolean isListNoNull(List list){
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取帖子详情页回复页数
	 * @param pagecount
	 * @return 一共有多少页
	 */
	public static int getPageCount(int pagetotal, int pagecount) {
		int total = 1;
//		pagetotal = pagetotal + 1;
		try {
			if (pagetotal == 0) {
				return 1;
			}
			if (pagetotal <= pagecount) {
				total = 1;
			} else {
				if (pagetotal % pagecount != 0) {
					total = pagetotal / pagecount + 1;
				} else {
					total = pagetotal / pagecount;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			total = 1;
		}
		return total;
	}
	
	/*
	 * 要严格的验证手机号码，必须先要清楚现在已经开放了哪些数字开头的号码段，目前国内号码段分配如下：
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186电信：133、153、180、189、（1349卫通）
	 */
	public static boolean isMobileNO(Context context,String mobiles) {

		if (!StringUtil.isNull(mobiles)) {
			Pattern p = Pattern
					.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			if (m.matches()) {
				return true;
			} else {
				ToastUtil.showToast(context, "您的手机号格式有误，请重新输入");
				return false;
			}

		} else {
			ToastUtil.showToast(context, "请输入手机号码");
			return false;
		}

	}
    /*
	 * 要严格的验证手机号码，必须先要清楚现在已经开放了哪些数字开头的号码段，目前国内号码段分配如下：
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186电信：133、153、180、189、（1349卫通）
	 */
    public static boolean isMobileNOToast(Context context,String mobiles) {

        if (!StringUtil.isNull(mobiles)) {
            Pattern p = Pattern
                    .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            if (m.matches()) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }

    public static String deleteSpace(String str) {
        String newStr = "";
        newStr  = str.replaceAll(" ", "");
        return newStr;

    }
    
    /**
     * 过滤emoji表情
     * @param content
     * @return
     */
	public static String filterEmoji(String content) {
		
		try
		{
			if(content == null || content.length() == 0)
				return content;
			
			Pattern emoji = Pattern
					.compile(
							"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
							Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			
			Matcher emojiMatcher = emoji.matcher(content);
	
			while (emojiMatcher.find()) {
				content = content.replace(emojiMatcher.group(), "");
			}
		}catch(Exception ex)
		{
		}
		return content;
	}




    /**
     *  普通站点 客户端与服务器通信Token（用于Get）
     * @param url
     * @param params
     * @return
     * @deprecateds
     */
    public static String makeurlstr(String url, Map<String, ?> params) {

        return makedburlstr(url, params);
    }

    /**
     * 拼装URl和参数 常使用于get方式请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String makedburlstr(String url, Map<String, ?> params) {
        Object[] keys = params.keySet().toArray();
        Arrays.sort(keys);
        StringBuffer sb = new StringBuffer();
        StringBuffer urlbuff = new StringBuffer(url + "?");
        for (Object s : keys) {

            if (null == params.get(s)) {

            } else {
                sb.append(s);
                sb.append(params.get(s));
                urlbuff.append(s).append("=").append(URLEncoder.encode(params.get(s).toString())).append("&");
            }
        }
        return urlbuff.toString();

    }

    /**
     * 新增判断帖子中的图片是否gif图
     * @param imageUrl
     * @return
     */
    public static boolean isGif(String imageUrl){
         boolean flag = false;
         if(isNull(imageUrl)){
             return flag;
         }
         if(imageUrl.toLowerCase().contains(".gif")){
             flag = true;
         }
         return flag;
    }


	/**
	 *
	 *
	 * */
	public static String listToString(List<String> stringList,String split){
		if (stringList==null || stringList.size() <= 0 || split == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag=false;
		for (String string : stringList) {
			if (flag) {
				result.append(split);
			}else {
				flag=true;
			}
			result.append(string);
		}
		return result.toString();
	}

	/**
	 * 发帖内容转化为帖子内容一样的格式。包括图片
	 * @param msg
	 * @param photoBeanList
	 * @return
	 */
//	public static String getDetailContent(String msg,List<PhotoBean> photoBeanList){
//		String texts[] = msg.split("\\[attachimg\\]([^\\[\\]]+?)\\[\\/attachimg\\]"); // 文字按[img]http://...[/img]进行分组
//		ArrayList<String> list_text = new ArrayList<String>();
//		// 把文字加入list中
//		for (int j = 0; j < texts.length; j++) {
//			list_text.add(texts[j]);
//		}
//		// 把图片链接加入list中
//		Matcher mc = Pattern.compile("\\[attachimg\\]([^\\[\\]]+?)\\[\\/attachimg\\]").matcher(msg);
//		final ArrayList<String> list_img = new ArrayList<String>();
//		while (mc.find()) {
//			list_img.add(mc.group()); // 图片地址
//		}
//		for(int i = 0;i<list_img.size();i++){
//			String tem = list_img.get(i);
//			tem = tem.replace("[attachimg]", "[img]").replace("[/attachimg]", "[/img]");
//			int index = tem.indexOf("[img]");
//			int end = tem.indexOf("[/img]");
//			if(index!=-1&&end!=-1){
//				tem = tem.substring(index+5, tem.length()-6);
//				for(PhotoBean s:photoBeanList){
//					if(tem.equals(s.getKey())){
//						tem = "[img]http://image-qzone.mamaquan.mama.cn/upload/"+s.getName()+"[/img]";
//
//						break;
//					}
//				}
//			}
//			list_img.set(i, tem);
//
//		}
//		// 把文字、图片链接一次放入list中
//		ArrayList<String> list_mgs = new ArrayList<String>();
//		int img_position = 0;
//		for (int k = 0; k < list_text.size(); k++) {
//			String text = list_text.get(k);
//			if (text.length() > 0 && !text.equals("")) {
//				list_mgs.add(text);
//			}
//			if (img_position < list_img.size()) {
//				list_mgs.add(list_img.get(img_position));
//				img_position++;
//			}
//		}
//		while (img_position < list_img.size()) {
//			list_mgs.add(list_img.get(img_position));
//			img_position++;
//		}
//		StringBuilder sb = new StringBuilder();
//		for(String s:list_mgs){
//			sb.append(s);
//		}
//		return sb.toString();
//	}

}
