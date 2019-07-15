package com.cbmie.system.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class GlobalExceptionHandlerResolver extends SimpleMappingExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		if(ex!=null){
			ex.printStackTrace();
		}
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			if(ex instanceof SystemException){
				Integer statusCode = getStatusCodeBySysExpt((SystemException)ex);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				ModelAndView mv = getModelAndView(viewName, ex, request);
				exposeModeAndView(mv,(SystemException)ex);
				return mv;
			}else{
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				ModelAndView mv = getModelAndView(viewName, ex, request);
				return mv;
			}
		}
		else {
			return null;
		}
	}
	
	protected Integer getStatusCodeBySysExpt(SystemException ex){
		String code = ex.getExptCode();
		Integer statusCode = ExceptionCode.EXCEPTION_STATUS_CODE_MAP.get(code);
		return statusCode;
	}
	protected void exposeModeAndView(ModelAndView mv,SystemException ex){
		String mesg = ex.getMessage();
		if(StringUtils.isEmpty(mesg)){
			String code = ex.getExptCode();
			if(ExceptionCode.AUTHORITY_EXCEPTION_CODE.equals(code)){
				mesg = "很抱歉，您的权限不够";
			}else if(ExceptionCode.DATA_EXCEPTION_CODE.equals(code)){
				mesg = "很抱歉，数据出现问题";
			}else if(ExceptionCode.LOGIC_EXCEPTION_CODE.equals(code)){
				mesg = "很抱歉，后台处理出现问题";
			}else if(ExceptionCode.TIMEOUT_EXCEPTION_CODE.equals(code)){
				mesg = "很抱歉，时间超时";
			}else{
				mesg = "很抱歉，系统出现不明错误";
			}
		}
		mv.addObject("mesg", mesg);
	}
}
