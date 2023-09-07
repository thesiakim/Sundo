package common.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.util.CommUtils;

/**
 * @Class Name : BaseInterceptor.java
 * @Description :
 * @Modification Information
 * @author cslee
 * @since 
 * @version
 * @see
 */

@SuppressWarnings("all")
public abstract class BaseInterceptor extends HandlerInterceptorAdapter {

    protected String getRequestURL(HttpServletRequest request) {
		String requestURL = request.getRequestURI();
		requestURL 		= requestURL.substring(request.getContextPath().length());
		String pathInfo = request.getPathInfo();

		if ("/".equals(requestURL) && pathInfo != null) {
			requestURL = pathInfo;
		}
		return requestURL;
	}

	protected String getCurrPage(HttpServletRequest request) {
		String currPage 	= request.getRequestURI();
		currPage			= currPage.substring(currPage.lastIndexOf("/")+1);
		return currPage;
	}
}
