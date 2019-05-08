package com.fxbank.cap.ceba.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;


/** 
* @ClassName: ErrorList 
* @Description: 错误码列表
* @作者 杜振铎
* @date 2019年5月8日 下午3:00:32 
*  
*/
public class ErrorList {
	
	@JSONField(name = "DATA")
	private List<ErrorInfo>  data = new ArrayList<ErrorInfo>();

	public List<ErrorInfo> getData() {
		return data;
	}

	public void setData(List<ErrorInfo> data) {
		this.data = data;
	}

	
	
}
