package common.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.annotation.Aspect;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import commf.message.Message;
import common.util.CommUtils;
import common.util.SecurityUtil;
import common.util.WebUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Program Name : BaseController
 * Description :
 * Programmer Name : ntarget
 * Creation Date : 2017-01-09
 * Used Table :
 */

@Aspect
@SuppressWarnings("all")
public abstract class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public BaseController() {
	}

	public HttpServletRequest request = null;

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	
	

	
	/**
	 * @throws IOException ***********/

	
	public HashMap getHashMapFromRequestBody(HttpServletRequest request) throws IOException {
		
		
		// Stream
		InputStream reqInStream = request.getInputStream();
		
		// To String
		String body = getRequestBodyStreamToStringData(reqInStream);
		
		// To Json
		JSONObject jsonObj = getJsonDataFromString(body);
		
		// To HashMap
		HashMap tempM = getHashMapFromJSON(jsonObj);
		return tempM;
	}
	
	// Common Func3
	private String getRequestBodyStreamToStringData(InputStream reqStreamData) throws IOException {
		
		String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
	 
        try {
            InputStream inputStream = reqStreamData;
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                	stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
	 
        body = stringBuilder.toString();
		
        return body;
		
	}
	
	// Common Func2
	private JSONObject getJsonDataFromString(String _body) throws IOException{
		
		String body = _body;
		JSONObject tmepObj = JSONObject.fromObject(body);

		// JSON END
        return tmepObj;
	}

	// Common Func
	private HashMap getHashMapFromJSON(JSONObject _jsonObj) {
		
		JSONObject jsonObj = _jsonObj;
		
        // JSON => HASHMAP
		
		HashMap tempMss = new HashMap();
        Set key = jsonObj.keySet();
        Iterator<String> iter = key.iterator();
        
        while(iter.hasNext())
        {
        	System.out.println("--------------- >>>");
        	
            String keyname = iter.next();
            String value = (String) jsonObj.get(keyname);
            if(value.indexOf("[") == 0 && value.indexOf("]") == (value.length() - 1)) {
            	// 파라미터 배열 추가
            	String[] spArr = value.split("},");
            	
            	ArrayList tmList = new ArrayList();
            	for(int i = 0; i < spArr.length; i++) {
            		String tmVal = spArr[i];
            		String[] spVals = tmVal.split(",");
            		HashMap eachMap = new HashMap();
            		for(int k = 0; k < spVals.length; k++) {
            			String[] rTmSp = spVals[k].split(":");
            			
            			if(rTmSp.length == 2) {
            				String rKeyName = rTmSp[0].replace("{", "");
            				rKeyName = rKeyName.replace("[","");
            				rKeyName = rKeyName.replace("]","");
            				rKeyName = rKeyName.replace("}","");
            				rKeyName = rKeyName.replace("\"","");
            				
            				String rValue = rTmSp[1];
            				rValue = rValue.replace("{", "");
            				rValue = rValue.replace("\"","");
            				rValue = rValue.replace("[","");
            				rValue = rValue.replace("]","");
            				rValue = rValue.replace("}","");

            				rValue = SecurityUtil.removeXss(rValue);
            				
            				eachMap.put(rKeyName, rValue);
            			}
            		}
            		tmList.add(eachMap);
//            		tempStr += tmVal + " ";
            		
            	}
                tempMss.put(keyname, tmList);
            }else {
            	// 배열이 아닌 스트링
            	if(value.indexOf("EXCEPTION") != -1) {
            		value = value.replace("EXCEPTION", "");
            	}
                tempMss.put(keyname, value);
            }
//            System.out.println("key : "+keyname+" type : "+ value);
        }
        
        return tempMss;
	}
	
	
	
	/*************/
	
	
	public boolean setUserInfoToSession(HttpServletRequest request, HashMap userInfoMap) {

        userInfoMap.put("isLogined", true);
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("user_info", userInfoMap);
        return true;
	}
	
	public HashMap getUserInfo(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		return (HashMap) httpSession.getAttribute("user_info");
	}
	
	public boolean isLoginCheckerFromSession(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		HashMap tempM = (HashMap) httpSession.getAttribute("user_info");
		boolean isLogined = (Boolean) tempM.get("isLogined");
		return isLogined;
	}
	
	public boolean loginOut(HttpServletRequest request) {
		HttpSession httpSession = request.getSession(true);
		httpSession.removeAttribute("user_info");
		return true;
	}
	
	public boolean isAuthorityUser(HttpServletRequest request) {
		
		HttpSession httpSession = request.getSession();
		HashMap tempM = (HashMap) httpSession.getAttribute("user_info");
		String userType = (String) tempM.get("user_type");
		
		boolean isAuthorityUser = false;
		if(userType != null) {
			if(userType.equals("ADMIN")) {
				isAuthorityUser = true;
			}
		}
		
		return isAuthorityUser;
	}
	
	
	
	
	

	protected int BASE_PAGE = 1;
	protected int CURR_PAGE = 1;
	protected int PAGE_SIZE = 15;
	protected int PAGE_BBS_SIZE = 10;
	protected int FILE_SIZE = 20;


	protected String GRID_DATA = null;
	protected String USER_DATA = null;

	protected String ajaxView = "jsonView";	// default "jsonView", another "xmlView"

    public String getErrMessage(Exception ex) {
    	String msg = "";

    	if (ex.toString().indexOf("EgovBizException") >= 0) {
    		msg = ex.getMessage();
    	} else {
    		msg = "Error !! : "+ex.toString();
    	}

    	return msg;
    }

	protected HashMap getParameterMap(HttpServletRequest req, boolean sessionFlag) {
		setRequest(req);

		HashMap map = new HashMap();
		map = getParameterMap(req);

		return map;
	}


	protected HashMap getParameterMap(HttpServletRequest req) {
		setRequest(req);

		HashMap map = new HashMap();

		Enumeration enm = req.getParameterNames();

		String name = null;
		String value = null;
		String[] arr = null;

		while (enm.hasMoreElements()) {
			name = (String) enm.nextElement();
			
			// Untrusted Servlet Parameter - 18.08.02
			// [조치] - 공통 처리를 위해 WebUtil.getParameterValues(..)로 데체
//			arr = req.getParameterValues(name);
			arr = WebUtil.getParameterValues(req, name);

			if (name.startsWith("arr")) {
				map.put(name, arr);
			} else {
				if (arr != null && arr.length > 0) {
					value = arr[0];
				} else {
				    // Untrusted Servlet Parameter - 18.08.02
		            // [조치] - 공통 처리를 위해 WebUtil.getParameter(..)로 데체
//				    value = req.getParameter(name);
					value = WebUtil.getParameter(req,name);
				}

				map.put(name, value);
			}
		}

		// Request Parameters For GRID
		this.GRID_DATA = (String) map.get("jqGridParams");
		JSONArray jsonArray = null;
		List listGrid = new ArrayList();

		if (this.GRID_DATA != null) {
			JSONObject jsonObject = JSONObject.fromObject(this.GRID_DATA);
			String mode = "";
			for (Iterator<String> names = jsonObject.keySet().iterator(); names.hasNext();) {
				mode = names.next();

				if (mode != null) {
					if (jsonObject.containsKey(mode)) {
						jsonArray = jsonObject.getJSONArray(mode);

						Map jmap = null;
						JSONObject object = null;
						for (int i = 0; i < jsonArray.size(); i++) {
							object = (JSONObject) jsonArray.getJSONObject(i);

							jmap = (HashMap) JSONObject.toBean(object, java.util.HashMap.class);

							listGrid.add(jmap);
						}

						map.put(mode, listGrid);
					}
				}
			}
			map.remove("jqGridParams");
		}

		// Request Parameters For GRID -> jqUserParams
		this.USER_DATA = (String) map.get("jqUserParams");
		if (this.USER_DATA != null) {
			JSONObject jsonObject = JSONObject.fromObject(this.USER_DATA);
			Map jmap = (HashMap) JSONObject.toBean(jsonObject, java.util.HashMap.class);

			if (!jmap.isEmpty()) {
				Iterator k = jmap.keySet().iterator();
				String key = "";
				while (k.hasNext()) {
					key = (String) k.next();
					map.put(key, jmap.get(key));
				}
			}
			map.remove("jqUserParams");
		}

		// Return View (ajax)
		if (!CommUtils.nvlTrim((String)map.get("returnType")).equals("")
				&& !CommUtils.nvlTrim((String)map.get("oper")).equals("")) {
			ajaxView = CommUtils.nvlTrim((String)map.get("returnType"));
		} else {
			ajaxView = "jsonView";
		}

		return map;
	}

	// Get Method Name
	protected String getMethodName(Throwable trb) {
		StackTraceElement[] stacks = trb.getStackTrace();
		StackTraceElement currentStack = stacks[0];
		return currentStack.getMethodName();
	}

	public void resultFlag(String msg) {
		request.getSession().setAttribute("PROCFLAG", msg);
	}
	
	/* 균주 등록 컬럼 */
	protected String[] columnArr = {
			"time"
			,"lat"
			,"lon"
			,"user_org"
			,"user_name"
			,"title"
			,"content"
			,"sido"
			,"gugun"
			,"dong"
			,"bunji"
			,"road_name"
			,"building"
			,"category1"
			,"category2"
			,"status"
			,"org_file_name"
			,"file_ext"
			,"file_path"
			
	};

}
