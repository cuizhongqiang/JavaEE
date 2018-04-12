package com.cnbmtech.cdwpcore.aaa.msg;
public class ResponseBean {
    
    // http 状态码

    private int status;
    
    // 返回信息
    private String msg;

    // 返回的数据
    private Object data;

    public ResponseBean(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}