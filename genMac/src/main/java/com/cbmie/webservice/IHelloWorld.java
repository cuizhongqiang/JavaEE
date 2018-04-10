package com.cbmie.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IHelloWorld {
	@WebMethod
    public String sayHello(@WebParam(name = "username") String username);
    public void setUser(String username);
}
