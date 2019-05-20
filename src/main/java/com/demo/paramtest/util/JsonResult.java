package com.demo.paramtest.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author
 *
 * @param <T>
 */
@Data//@Data注解在类上，会为类的所有属性自动生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。
@AllArgsConstructor//@NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {

	private String code;
    private String message;
    private T data;
    
    public static final String SUCCESS = "S";
    public static final String ERROR = "F";
	
	@Override
	public String toString() {
		return "JsonResult [code=" + code + ", message=" + message + ", data=" + data + "]";
	}



}
