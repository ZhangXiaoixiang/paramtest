package com.demo.paramtest.util;
/**
 * 
 * @author
 *
 * @param <T>
 */
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

	 public JsonResult() {
		 	code = SUCCESS;
	        message = "";
	    }

	    public JsonResult(T data){
	    	code = SUCCESS;
	        this.data = data;
	    }

	    public JsonResult(Throwable e){
	    	code = ERROR;
	        message = e.getMessage();
	    }

	    public JsonResult(String code, Throwable e){
	        this.code = code;
	        this.message = e.getMessage();
	    }

	    public JsonResult(String code, T data){
	        this.code = code;
	        this.data = data;
	    }

	    

	    public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public T getData() {
	        return data;
	    }

	    public void setData(T data) {
	        this.data = data;
	    }

}
