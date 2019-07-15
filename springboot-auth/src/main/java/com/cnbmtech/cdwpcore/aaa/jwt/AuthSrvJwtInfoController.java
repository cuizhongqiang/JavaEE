/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.jwt;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RestController;  
  
@RestController  
@RequestMapping("/jwt")  
public class AuthSrvJwtInfoController {  
  
    @Autowired  
    private JwtInfo jwtInfo;  
  
    @RequestMapping(value = "/info", method = RequestMethod.GET)  
    public JwtInfo getJwtInfo() {  
        return jwtInfo;  
    }  
}  