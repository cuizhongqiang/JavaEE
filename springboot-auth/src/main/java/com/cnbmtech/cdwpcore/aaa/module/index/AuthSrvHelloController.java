/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.module.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"/"}, method=RequestMethod.GET)
public class AuthSrvHelloController {

	@RequestMapping(value={"/"}, method=RequestMethod.GET)
    String home() {
        return "redirect:/index.html";
    }

}