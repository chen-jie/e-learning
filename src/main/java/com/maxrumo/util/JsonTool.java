package com.maxrumo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTool {
	
	private static ObjectMapper mapper = new ObjectMapper();
	static{
		// 过滤对象的null属性.
		mapper.setSerializationInclusion(Include.NON_NULL);
	}
	//阻止工具类被实例化
	private JsonTool(){}
	/** 
	 * @description 对象转为json
	 * @author cj
	 * @date 2016年12月23日 上午10:55:37
	 * @param obj
	 * @return
	 */
	public static String objToJson(Object obj){
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	/** 
	 * @description json转为对象
	 * @author cj
	 * @date 2016年12月23日 上午10:52:35
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToObj(String json,Class<T> clazz){
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/** 
	 * @description json转为对象list
	 * @author cj
	 * @date 2016年12月23日 上午10:59:16
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToObjList(String json,Class<T> clazz){
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);  
		try {
			List<T> list =  mapper.readValue(json, javaType);
			return list;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/** 
	 * @description 转为map对象
	 * @author cj
	 * @date 2016年12月23日 上午11:02:52
	 * @param json
	 * @param key
	 * @param value
	 * @return
	 */
	public static <K,V> Map<K,V> jsonToMap(String json,Class<K> key,Class<V> value){
		JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class,key, value);
		try {
			return mapper.readValue(json, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
