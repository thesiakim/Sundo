package business.biz.templete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import common.base.BaseController;

@EnableWebMvc
@Controller
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class SampleController extends BaseController {

	
    @Autowired
    private SampleService  sampleService;
	
	
	
	@RequestMapping("/sample.do")
    public String Login(HttpServletRequest request, ModelMap model) throws Exception {

	    // model.addAttribute("kakaoApiKey", ApplicationProperty.get("kakaoApiKey"));
	    return "sampleTemplete/Sample1";
    }
	
	
	@RequestMapping("/movePageA.do")
    public String MovePageA(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 권한 페이지 체크
		boolean isAuthorityUser = super.isAuthorityUser(request);
		
		if(isAuthorityUser) {
			// 정상 페이지 호출
			return "sampleTemplete/PageA";
		}else {
			// 비권한에 따른 에러 페이지 호출
			return request.getContextPath() + "error/401";
		}
    }

	@RequestMapping("/movePageB.do")
    public String movePageB(HttpServletRequest request, ModelMap model) throws Exception {
		
	    return "sampleTemplete/PageB";
    }

	@RequestMapping("/movePageC.do")
    public String movePageC(HttpServletRequest request, ModelMap model) throws Exception {

	    return "sampleTemplete/PageC";
    }

	@RequestMapping("/movePageD.do")
    public String movePageD(HttpServletRequest request, ModelMap model) throws Exception {

	    return "sampleTemplete/PageD";
    }
	
	
	
	//	계정 > 기관별 메뉴 및 권한 START

	@RequestMapping("/menuAndAuth.do")
    public String menuAndAuth(HttpServletRequest request, ModelMap model) throws Exception {

	    return "comm/menuAuth/menuAndAuth";
    }
	

//	@RequestMapping(value="sample/getMenuList.do", method=RequestMethod.POST)
//    @ResponseBody
//    public Object getMenuList(HttpServletRequest request) throws Exception{
//        
//        List tempList = sampleService.GetMenuList();
//
//		
//        JSONObject returnJson = new JSONObject();
//        returnJson.put("db_menu_list", tempList);
//
//        return returnJson;
//	}
	
	
	
//	// 기관 리스트
//	@RequestMapping(value="sample/getAllAgentList.do", method=RequestMethod.POST)
//    @ResponseBody
//    public Object GetAllAgentList(HttpServletRequest request) throws Exception{
//        
//		HashMap userInfo = super.getUserInfo(request);
//		System.out.println(userInfo);
//		System.out.println(userInfo);
//		System.out.println(userInfo);
//		System.out.println(userInfo);
//		System.out.println(userInfo);
//
//        JSONObject returnJson = new JSONObject();
//		if(userInfo != null) {
//			boolean isAdmin = super.isAuthorityUser(request);
//			
//	        List agentAllList = sampleService.GetAllAgentList();
//	        List agentActiveList = sampleService.GetAgentActiveList(userInfo);
//			
//	        returnJson.put("db_agent_list", agentAllList);
//	        returnJson.put("db_agent_active_list", agentActiveList);
//	        
//	        JSONObject userJson = new JSONObject();
//	        userJson.put("is_admin", isAdmin);
//	        userJson.put("user_agent_code", userInfo.get("user_agent_code"));
//	        
//	        returnJson.put("userInfo", userJson);
//	        returnJson.put("status", "SUCCESS");
//
//		}
//
//        return returnJson;
//	}
	
	
	// 메뉴리스트
	@RequestMapping(value="sample/getRD_MenuList.do", method=RequestMethod.POST)
    @ResponseBody
    public Object GetRD_MenuList(HttpServletRequest request) throws Exception{
        
        List authMenuList = sampleService.GetRD_AuthMenuList();
		
        JSONObject returnJson = new JSONObject();
        returnJson.put("status", "SUCCESS");
        returnJson.put("db_rd_menu_list", authMenuList);

        return returnJson;
	}
	
	// 기관 리스트 저장
	@RequestMapping(value="sample/insertAgentActiveList.do", method=RequestMethod.POST)
    @ResponseBody
    public Object InsertAgentActiveList(HttpServletRequest request) throws Exception{
		
        HashMap parmM = super.getHashMapFromRequestBody(request);
        

        List duplListChk = sampleService.GetDuplAgentActiveList(parmM);

        System.out.println(duplListChk.size());

        JSONObject returnJson = new JSONObject();
        if(duplListChk.size() > 0) {
        	// 있을 경우
            returnJson.put("status", "fail");
        	
        }else {
        	// 없을 경우 삽입
            sampleService.InsertAgentActiveList(parmM);
            returnJson.put("status", "SUCCESS");
        }

        return returnJson;
	}


	// 기관 리스트 삭제
	@RequestMapping(value="sample/deleteAgentActiveList.do", method=RequestMethod.POST)
    @ResponseBody
    public Object DeleteAgentActiveList(HttpServletRequest request) throws Exception{
		
        HashMap parmM = super.getHashMapFromRequestBody(request);
        System.out.println(parmM);
        
        int suc = sampleService.DeleteAgentActiveList(parmM);

        JSONObject returnJson = new JSONObject();
        returnJson.put("status", "SUCCESS");
        returnJson.put("deleteState", suc);

        return returnJson;
	}

	
	
	//	계정 > 기관별 메뉴 및 권한 END


	
	
	
	

	@RequestMapping(value="/UpdateAutorityUp.do", method=RequestMethod.POST)
    @ResponseBody
    public Object UpdateAutorityUp(HttpServletRequest request) throws Exception{

        HashMap tempM = super.getHashMapFromRequestBody(request);
        
        
        System.out.print(tempM);
        
        int updateCount = sampleService.UpdateAutorityUp(tempM);
        
		
        JSONObject returnJson = new JSONObject();
        returnJson.put("db_select_count", updateCount);

        return returnJson;
	}
	
	@RequestMapping(value="/UpdateAutorityDown.do", method=RequestMethod.POST)
    @ResponseBody
    public Object UpdateAutorityDown(HttpServletRequest request) throws Exception{

        
        HashMap tempM = super.getHashMapFromRequestBody(request);
        

        int updateCount = sampleService.UpdateAutorityDown(tempM);
        

//		boolean isLoginOut = super.loginOut(request);
//        boolean isRegistered = super.setUserInfoToSession(request, userInfoMap);
		
		
        JSONObject returnJson = new JSONObject();
        returnJson.put("db_select_count", updateCount);

        return returnJson;
	}
	

	@RequestMapping(value="/getPriorityUserData.do", method=RequestMethod.POST)
    @ResponseBody
    public Object GetPriorityUserData(HttpServletRequest request) throws Exception{

        
        List userList = sampleService.GetPriorityUserData();
        
		
        JSONObject returnJson = new JSONObject();
        returnJson.put("db_select_count", userList.size());
        returnJson.put("data", userList);

        return userList;
	}
	
	
	
	
	
}