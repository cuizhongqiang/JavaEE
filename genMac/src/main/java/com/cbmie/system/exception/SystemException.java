package com.cbmie.system.exception;

public class SystemException extends Exception {

	private static final long serialVersionUID = -7458458169920631513L;
	
	/**异常编码*/
	private String exptCode = ExceptionCode.DEFAULT_EXCEPTION_CODE;
	
	public SystemException(){
		super();
	}
	
	public SystemException(String exptCode){
		super();
		this.exptCode = exptCode;
	}
	
	public SystemException(String exptCode,String message){
		super(message);
		this.exptCode = exptCode;
	}
	
	public SystemException(String exptCode,Throwable cause) {  
        super(cause);  
        this.exptCode = exptCode;
    }  
  
    public SystemException(String exptCode,String message, Throwable cause) {  
        super(message, cause); 
        this.exptCode = exptCode;
    }

	public String getExptCode() {
		return exptCode;
	}

	public void setExptCode(String exptCode) {
		this.exptCode = exptCode;
	}
}
