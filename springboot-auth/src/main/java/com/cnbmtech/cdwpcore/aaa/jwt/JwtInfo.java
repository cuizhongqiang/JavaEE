/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.jwt;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;  
/* 
 * 自定义配置文件的解析类 
 */  
@ConfigurationProperties(prefix = "jwt.info")
@PropertySource("classpath:config/jwt.properties")
public class JwtInfo {  
    private String clientId;    
    private String base64Secret;    
    private String name;    
    private int expiresSecond;  
    public String getClientId() {  
        return clientId;  
    }  
    public void setClientId(String clientId) {  
        this.clientId = clientId;  
    }  
    public String getBase64Secret() {  
        return base64Secret;  
    }  
    public void setBase64Secret(String base64Secret) {  
        this.base64Secret = base64Secret;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public int getExpiresSecond() {  
        return expiresSecond;  
    }  
    public void setExpiresSecond(int expiresSecond) {  
        this.expiresSecond = expiresSecond;  
    }  
      
} 