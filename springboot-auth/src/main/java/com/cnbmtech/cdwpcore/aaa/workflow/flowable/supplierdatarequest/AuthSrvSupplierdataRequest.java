package com.cnbmtech.cdwpcore.aaa.workflow.flowable.supplierdatarequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/SupplierdataRequest")
public class AuthSrvSupplierdataRequest{

	@RequestMapping(value={"/"}, method=RequestMethod.GET)
    public String request() {
        return "hello";
    }

}