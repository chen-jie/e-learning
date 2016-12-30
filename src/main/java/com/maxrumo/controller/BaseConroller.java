package com.maxrumo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.maxrumo.util.JsonTool;
import com.maxrumo.vo.ResultVo;

public class BaseConroller {

	public static final int CODE_SUCCESS = 0;
	public static final int CODE_ERROR = 1;
	public static final Map<Integer, String> CODE_MSG = new HashMap<Integer, String>();
	static {
		CODE_MSG.put(CODE_SUCCESS, "success");
		CODE_MSG.put(CODE_ERROR, "unknown error");
	}

	protected String success() {
		return success(null);
	}

	protected String success(String msg) {
		return success(msg, null);
	}

	protected String success(Object data) {
		return success(null, data);
	}

	protected String success(String msg, Object data) {
		ResultVo vo = buildResultVo(CODE_SUCCESS, msg, data);
		return JsonTool.objToJson(vo);
	}

	protected String fail() {
		return fail(null);
	}

	protected String fail(String msg) {
		return fail(msg, null);
	}

	protected String fail(String msg, Object data) {
		ResultVo vo = buildResultVo(CODE_ERROR, msg, data);
		return JsonTool.objToJson(vo);
	}

	protected ResultVo buildResultVo(int code, String msg, Object data) {
		ResultVo vo = new ResultVo();
		vo.setStatus(code);
		vo.setData(data);
		if (StringUtils.isNotEmpty(msg)) {
			vo.setMsg(msg);
		} else {
			vo.setMsg(CODE_MSG.get(code));
		}
		return vo;
	}
}
