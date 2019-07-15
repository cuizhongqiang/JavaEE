package com.cbmie.webservice;

public class HelloWorldImpl implements IHelloWorld {  
    @Override  
    public String sayHello(String username) {  
           
        return "Hello " + username;  
    }  
  
    @Override   
    public void setUser(String username) {  
           
    }  
}
