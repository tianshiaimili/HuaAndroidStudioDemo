package com.hua.utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 *  解析Json服务类
 *  反射机制
 *
 */
public class DataParser<T> implements DataParserInterface{
	
	private Field[] fields;
	private Class cls;
	private Class child;
	private List<T> list;
	private List<String> group;
	private String childName;
	
	private String[] childNames;
	private boolean[] isObject;
	private Class[] childClasss;
	private List<T> childs;
	
	public DataParser(Class cls) {
		this.cls = cls;
		this.fields = cls.getDeclaredFields();
		list = new ArrayList<T>();
	}
	
	public DataParser(Class cls,Class child,String name,List<?> childs) {
		this.cls = cls;
		this.child =child;
		this.childName = name;
		this.fields = cls.getDeclaredFields();
		list = new ArrayList<T>();
		
	}
	
	/**
	 * 假如json格式中存在其他对象列表时候。可以使用该方法
	 * 具体用法可以查看亲子成长记中的获取记录详情接口
	 * @param cls
	 * @param childClasss
	 * @param name
	 * @param isObject
	 */
	public DataParser(Class cls, Class[] childClasss, String[] name,
			boolean[] isObject) {
		this.cls = cls;
		this.childClasss = childClasss;
		this.childNames = name;
		this.fields = cls.getDeclaredFields();
		this.isObject = isObject;
		list = new ArrayList<T>();

	}
	
	
	
	/**
	 * @param str :json字符串
	 * @param nodeName ： 需要解析的根元素节点
	 * @return 数据集合
	 */
	@Override
	public List<T> getData(String str,String nodeName) {
		try {
			JSONObject js = null;
			js = nodeName==null?new JSONObject(str):new JSONObject(str).getJSONObject(nodeName);
			   for (int i = 0; i < js.names().length(); i++) {
				   T t = (T) cls.newInstance();
				   int tag = 1; //判断json字符串中是否有fields[j].getName()字段，如果没有会抛异常
				   for (int j = 0; j < fields.length; j++) {
					   String value = "";
					   try {                   //js.names().getString(i) 这样没有排序
						   value = js.getJSONObject(i+"").getString(fields[j].getName());
						} catch (JSONException e) {
							value = null;
							tag = 0 ;
						}
					  if(value!=null){
						  fields[j].setAccessible(true);
						  fields[j].set(t, value);
					  }
					 
				}
				   if(tag==1)list.add(t);   //没有抛异常才加入List
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	

	
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public T getData(String str){
		 T t = null;
		try{
			t = (T) cls.newInstance();
			JSONObject js = new JSONObject(str).getJSONObject("data");
			 for (int i = 0; i < fields.length; i++) {
				   String value = "";
				   try {
					   value = js.getString(fields[i].getName());
					} catch (JSONException e) {
						value = null;
					}
				  if(value!=null){
					  fields[i].setAccessible(true);
					  fields[i].set(t, value);
				  }
				 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;
	}
	

	/**
	 * 获取帖子详情页回复页数
	 * @param str
	 * @return 一共有多少页
	 */
	public static String getValue(String str,String key){
		String value = ""; 
		try {
			value = new JSONObject(str).getString(key);
			
		} catch (Exception e) {
			e.printStackTrace();
			value = "0";
		}
		
		return value;
	}


    public static String getValueStr(String str,String key){
        String value = "";
        try {
            value = new JSONObject(str).getString(key);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
	

	




	public  List<T> getDatas(String json){
		try {
			JSONArray array = new JSONObject(json).getJSONArray("data");

				for(int i = 0;i<array.length();i++){
					Gson gson = new Gson();
					list.add((T) gson.fromJson(array.getJSONObject(i).toString(),cls));
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	/**
	 * 根据节点转化对象
	 * @param json
	 * @param nodeName
	 * @return
	 */
	public  T getDatas(String json,String nodeName){
		try {
			JSONObject object = new JSONObject(json).getJSONObject(nodeName);
		
		 
				Gson gson = new Gson();
				return (T) gson.fromJson(object.toString(),cls);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e){
            e.printStackTrace();
        }
		return null;
		
	}
	/**
	 * 根据节点转化对象
	 * @param json
	 * @param nodeName
	 * @return
	 */
	public   List<T> getListDatas(String json,String nodeName){
		try {
			JSONArray array = new JSONObject(json).getJSONArray(nodeName);
			
				for(int i = 0;i<array.length();i++){
					Gson gson = new Gson();
					list.add((T) gson.fromJson(array.getJSONObject(i).toString(),cls));
				}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}


	
	/**
	 * 获取data字段中的某一个nodevalue
	 *{"errmsg":{"errno":0,
	 *			"msg":"\u4e0a\u4f20\u56fe\u50cf\u6210\u529f"},
	 *			"data":{
	 *					"big":"http:\/\/um.mama.cn\/.\/data\/avatar\/007\/75\/63\/84_avatar_big.jpg",
	 *					"middle":"http:\/\/um.mama.cn\/.\/data\/avatar\/007\/75\/63\/84_avatar_middle.jpg",
	 *					"small":"http:\/\/um.mama.cn\/.\/data\/avatar\/007\/75\/63\/84_avatar_small.jpg"},
	 *			"status":1}
	 * @return
	 */
	public static String getOneValueInData(String json,String node){
		String value = null;
		try {
			value = new JSONObject(json).getJSONObject("data").getString(node);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
	public static String getOneValueError(String json,String node){
		String value = "";
		try {
			value = new JSONObject(json).getJSONObject("errmsg").getString(node);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	public static String getOneValueError(String json){

        return getOneValueError(json,"msg");
	}

	public static String getalueErrorNo(String json){

        return getOneValueError(json,"errno");
	}

	
	/**
	 * 获取data外的node
	 * @return
	 */
	public static String getJsonNode(String json,String node){
		String value = null;
		try {
			value = new JSONObject(json).getString(node);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 获取JSONArray里面的第一个jsonObject
	 * @return
	 */
	public static String getJsonArrNode(String json,String node){
		String value = null;
		try {
			value = new JSONObject(json).getJSONArray("data").getJSONObject(0).getString(node);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	

	
	public static String getStatus(String json){
		String status = null;
		try {
			status = new JSONObject(json).getString("status");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	public static String getIsFa(String json){
		String is_favorit = null;
		try {
			is_favorit = new JSONObject(json).getString("is_favorit");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is_favorit;
	}

    //test git
	public static String getNeed(String json,String needName){
		String is_favorit = null;
		try {
			is_favorit = new JSONObject(json).getString(needName);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
            is_favorit = "0";
			e.printStackTrace();
		}
		return is_favorit;
	}
	public static String getTitle(String json){
		String is_favorit = null;
		try {
			is_favorit = new JSONObject(json).getString("subject");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is_favorit;
	}
	
	
	public static String getViews(String json){
		String is_favorit = null;
		try {
			is_favorit = new JSONObject(json).getString("views");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is_favorit;
	}
	
	public static String getReplies(String json){
		String is_favorit = null;
		try {
			is_favorit = new JSONObject(json).getString("replies");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is_favorit;
	}
	public T getDataBean(String str, String nodeName) {
		T t = null;
		try {
			t = (T) cls.newInstance();
			JSONObject js = null;
			js = nodeName == null ? new JSONObject(str) : new JSONObject(str)
					.getJSONObject(nodeName);
		
			for (int j = 0; j < fields.length; j++) {
				String value = "";
				try { // js.names().getString(i) 这样没有排序
					value = js.getString(fields[j].getName());
				} catch (JSONException e) {
					value = null;
				}
				if (value != null) {
					fields[j].setAccessible(true);
					fields[j].set(t, value);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	

	public ArrayList<String> getJsonStringList(String json, String str) {
		ArrayList<String> list = new ArrayList<String>();
		try{
			JSONObject jsonObject = new JSONObject(json);
			JSONArray statJSONArray = jsonObject.getJSONArray(str);
			for(int i = 0; i < statJSONArray.length(); i++) {
				list.add((String) statJSONArray.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


}

/* 简单用法
DataParser<CirclePostListBean> pd = new DataParser<CirclePostListBean>(CirclePostListBean.class);
List<CirclePostListBean> lis = pd.getDatas(json);
isNewDigest = DataParser.getJsonNode(json, "isNewDigest");
		DataParser<PostsNewBean> new_postsCream = null;
		if(StringUtil.isNull(isNewDigest) && checkType == 1 && isNewPosts){
		new_postsCream  = new DataParser<PostsNewBean>(PostsNewBean.class);
		postsNewBean = new_postsCream.getDatas(json, "data");
		setItemNewPostsCreamShow(json, postsNewBean);
		}*/

