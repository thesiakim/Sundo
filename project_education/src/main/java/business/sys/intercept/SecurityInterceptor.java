package business.sys.intercept;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import common.base.BaseInterceptor;
import common.user.UserInfo;
import common.util.CommUtils;
import common.util.WebUtil;

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
public class SecurityInterceptor extends BaseInterceptor {

    protected final Log logger = LogFactory.getLog(getClass());

    protected static final String MAIN_URL				= "/main.do";
    protected static final String CHECK_PIX				= ".do";
    protected static final String LOGIN_URL				= "/login.do";
    protected static final String ACCESS_DENIED_URL		= "/error/accessDenied.jsp";
    protected static final String ERROR_FLAG_EF 		= "EF";		// URL 강제접속 방지.
    protected static final String MOBILE_2ND_AUTH_URL	= "/mobileAuth.do";
	protected static final String[] BYPASS_URLS 		= new String[] {
    	"exam/exam.do"
	};

    @Autowired
    UserInfo userInfo;

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
		// ** [sbb2.0-fix findbugs, cslee] Untrusted Servlet Parameter - 18.08.07
		// [조치] - 관리를 위해 공통 로직으로 변경 (WebUtil.getParameter(..)로 변경)
//		String mobileYn = CommUtils.getString(request.getParameter("mobileYn"), "");
		String mobileYn = CommUtils.getString(WebUtil.getParameter(request,"mobileYn"), "");

		//모바일 요청 제외
		if(!mobileYn.equals("Y")){
			// 세션 체크 ByPass
			if(matchUrl != null && StringUtils.indexOfAny(matchUrl, BYPASS_URLS) != -1) {
				return true;
			}

			//세션 체크
			if((String)userInfo.getUser_id() == null){
				//Ajax요청 여부
			    // ** [sbb2.0-fix findbugs, cslee] Http Header Untrusted - 18.08.01
		        // [조치] - Ajax요청을 보낼때 자동으로 추가되는 header로 ajax요청인지 판단하는데 사용되는 Header(x-requested-with) 임
			    //        - 세션이 없을 때의 대응으로 조작 된다 해도 문제가 되지 않음
				if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED);	//401
				}else{
				    // ** [sbb2.0-fix findbugs, cslee] Unvalidated Redirect - 18.08.01
		            // [조치] 리디렉션이 소스내에 정해진 페이지(로그인 화면)으로 이동하는 것으로 문제 없음
					response.sendRedirect(request.getContextPath() + LOGIN_URL);
					return false;
				}
			}
			
			//=================================================
			//[18.07.12 cslee 추가] 권한 적용.
			//-------------------------------------------
			// 관리자가 일반사용자페이지(/userPage.do), 일반사용자가 관리자페이지(/admin.do)로 접근하지 못하게 막는다.
			String isManager = (String)request.getSession().getAttribute("isManager");
			if( ("Y".equals(isManager) && matchUrl.contains("/userPage.do"))
			        || (!"Y".equals(isManager) && matchUrl.contains("/admin.do")) ) {
			    
			    // ACCESS_DENIED_URL 페이지로 강제 이동
			    // ** [sbb2.0-fix findbugs, cslee] Unvalidated Redirect - 18.08.01
			    // [조치] 리디렉션이 소스내에 정해진 페이지(접근거부 오류메시지 화면)으로 이동하는 것으로 문제 없음
			    response.sendRedirect(request.getContextPath() + ACCESS_DENIED_URL);
			    return false;
			}
			//=================================================

			// 관리자 이고 모바일 2차 인증이 필요한 경우 인증 페이지로 리다이렉트
			if ((String)userInfo.getUser_id() != null) {
				if (!MOBILE_2ND_AUTH_URL.equals(matchUrl) && !LOGIN_URL.equals(matchUrl) && "Y".equals(isManager)) {
					String needMobileAuth = (String)request.getSession().getAttribute("need_mobile_auth");
					if ("Y".equals(needMobileAuth)) {
						response.sendRedirect(request.getContextPath() + MOBILE_2ND_AUTH_URL);
						return false;
					}
				}
			}
		}

		Map map = new HashMap();
		map.put("url", 		matchUrl);
		map.put("userId", 	CommUtils.nvlTrim(userInfo.getUser_id()) );

		//Map authMap = authorityService.getAuthorityUser(map);

		Map authMap = null;

		// non authority check
		if (authMap == null) {

		// authority check
		} else {
			if (CommUtils.empty(userInfo.getUser_id()) ) {
	        	Map returnMap = new HashMap();
	        	returnMap.put("returnFlag"  , ERROR_FLAG_EF);

	        	request.getSession().setAttribute("CERT_RETURN", returnMap);

	        	// ** [sbb2.0-fix findbugs, cslee] Unvalidated Redirect - 18.08.01
	            // [조치] 리디렉션이 소스내에 정해진 페이지(로그인 화면)으로 이동하는 것으로 문제 없음
				response.sendRedirect(request.getContextPath() + LOGIN_URL);
				return false;
			}
			if (CommUtils.nvlTrim((String)authMap.get("auth")).equals("X") ) {
			    // ** [sbb2.0-fix findbugs, cslee] Unvalidated Redirect - 18.08.01
	            // [조치] 리디렉션이 소스내에 정해진 페이지(접근거부 오류메시지 화면)으로 이동하는 것으로 문제 없음
				response.sendRedirect(request.getContextPath() + ACCESS_DENIED_URL);
				return false;
			}
		}

		return true;
	}
}
