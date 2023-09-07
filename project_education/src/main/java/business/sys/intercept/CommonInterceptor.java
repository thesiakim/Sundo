package business.sys.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.base.BaseInterceptor;
import common.util.properties.ApplicationProperty;

/**
 * @Class Name : SecurityInterceptor.java
 * @Description :
 * @Modification Information
 * @author ntarget
 * @since 2017. 01. 03
 * @version
 * @see
 */

@SuppressWarnings("all")
public class CommonInterceptor extends BaseInterceptor {

    protected final Log logger = LogFactory.getLog(getClass());


	/**
	 * Filter processing page when accessing
	 *
	 * @param HttpServletRequest request, HttpServletResponse response, Object handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
	    String matchUrl	= getRequestURL(request);
	    
//	    logger.debug(" +++++++++++++++++++++++++++++++++++++++++ " + matchUrl);
	    
	    // WAS의 serverType을 환경변수에서 가져옴.
	    String serverType = ApplicationProperty.get("was.server.type");

	    //
	    request.setAttribute("_SERVER_TYPE", serverType);

		return true;
	}
}
