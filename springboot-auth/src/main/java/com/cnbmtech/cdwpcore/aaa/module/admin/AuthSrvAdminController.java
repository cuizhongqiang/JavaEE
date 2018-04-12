/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.module.admin;

import org.apache.shiro.authz.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/admin"}, method=RequestMethod.GET)
public class AuthSrvAdminController {

	@RequestMapping(value={"/"}, method=RequestMethod.GET)
    String home() {
        return "redirect:/admin/success.html";
    }

	@RequestMapping(value={"/hello"}, method=RequestMethod.GET)
    public @ResponseBody String hello() {
        return "hello";
    }
	
	@RequiresRoles(value={"admin","user"})
	//@RequiresPermissions("admin:one")
	@RequestMapping(value={"/permission1"}, method=RequestMethod.GET)
	@ResponseBody
	String permission1() {
		return "permission1";
	}
	
	@RequiresRoles("admin")
	//@RequiresPermissions("admin:two")
	@RequestMapping(value={"/permission2"}, method=RequestMethod.GET)
	@ResponseBody
	String permission2() {
		return "permission2";
	}

}