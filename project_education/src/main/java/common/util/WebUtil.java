package common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	
	static {
		System.setProperty("jsse.enableSNIExtension", "false");
	}
	
	
	public static String getResponseText(String urlString, String encoding) {
		return WebUtil.getResponseText(urlString, encoding, null, false, 0);
	}
	
	
	public static String getResponseText(String urlString, String encoding, Map<String, String> headers) {
		return WebUtil.getResponseText(urlString, encoding, headers, false, 0);
	}
	
	public static String getResponseText(String urlString, String encoding, Map<String, String> headers, boolean isSSLRequest) {
		return WebUtil.getResponseText(urlString, encoding, headers, isSSLRequest, 0);
	}

	
	public static String postResponseText(String urlString, String encoding, Map<String, String> headers) {
		HttpURLConnection httpConnection = null;
		String[] url = urlString.split("\\?");
		String result = "";
		BufferedReader in = null;
		try{
			URL targetUrl = new URL(url[0]);
			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			
			if(headers != null && !headers.isEmpty()) {
				Iterator<String> iterHeaderKey = headers.keySet().iterator();
				
				while(iterHeaderKey.hasNext()) {
					String key = iterHeaderKey.next();
					httpConnection.addRequestProperty(key, headers.get(key));
				}
			}
			
			httpConnection.setRequestMethod("POST");
			httpConnection.setDoOutput(true);
			OutputStream opstrm = httpConnection.getOutputStream();
			opstrm.write(url[1].getBytes());
			opstrm.flush();
			opstrm.close();
			
			String buffer = null;
			in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), encoding));
			while((buffer = in.readLine()) != null) {
				result += buffer;
			}
		}catch(Exception e) {
			
		}finally {
			if(httpConnection != null) {
				try {
					httpConnection.disconnect();
				} catch(Exception e) {}
			}
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		
		return result;
	}
	
	public static String getResponseText(String urlString, String encoding, Map<String, String> headers, boolean isSSLRequest, int timeout) { 
		HttpURLConnection httpConnection = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		StringBuffer result = new StringBuffer(); 
		
		try {
			URL url = new URL(urlString);
			httpConnection = (HttpURLConnection) url.openConnection();
			
			if(timeout > 0) {
				httpConnection.setConnectTimeout(timeout);
				httpConnection.setReadTimeout(timeout);
			}
			
			
			if(headers != null && !headers.isEmpty()) {
				Iterator<String> iterHeaderKey = headers.keySet().iterator();
				
				while(iterHeaderKey.hasNext()) {
					String key = iterHeaderKey.next();
					httpConnection.addRequestProperty(key, headers.get(key));
				}
			}
			
			
			isr = new InputStreamReader(httpConnection.getInputStream(), encoding);
			br = new BufferedReader(isr);
			
			String temp = null;
			
			while ((temp = br.readLine()) != null) {
				result.append(temp);
			}
			
			return result.toString();
		} catch(SocketTimeoutException ste) {
			logger.debug("Read Time Exception : " + urlString);
			
		} catch(Exception e) {
			if(logger.isErrorEnabled()) {
				logger.error("exception", e);
			}
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch(Exception e) {}
			}
			
			if(isr != null) {
				try {
					isr.close();
				} catch(Exception e) {}
			}
			
			if(httpConnection != null) {
				try {
					httpConnection.disconnect();
				} catch(Exception e) {}
			}
			
		}
		return null;
	}
	
	public static void getProxy(String type, String reqUrl, HttpServletRequest request, HttpServletResponse response) {
		HttpURLConnection httpURLConnection = null;
		InputStream is = null; 
		OutputStream os = null;
		InputStream ris = null; 
		OutputStream ros = null; 
		
		try {
			StringBuffer sbURL = new StringBuffer();
			sbURL.append(reqUrl);
			
			URL targetUrl = new URL(sbURL.toString());
			httpURLConnection = (HttpURLConnection)targetUrl.openConnection(); 
			httpURLConnection.setDoInput(true);
			
			if("GET".equals(type.toUpperCase())) {
				httpURLConnection.setDoOutput(false); 
				httpURLConnection.setRequestMethod("GET");
			}else{
				httpURLConnection.setDoOutput(true); 
				httpURLConnection.setRequestMethod("POST");
			}
			
			int length = 5000;
			int bytesRead = 0;
			ros = response.getOutputStream(); 
			response.setContentType(httpURLConnection.getContentType());
			ris = httpURLConnection.getInputStream();
			
			byte[] resBytes = new byte[length]; 
			bytesRead = 0; 
			while ((bytesRead = ris.read(resBytes, 0, length)) > 0) { 
				ros.write(resBytes, 0, bytesRead);
			}
		}catch(Exception e) { 
			logger.debug("ERROR", e);
			response.setStatus(500); 
		} finally { 
			try { if(is != null) { is.close(); } } catch(Exception e) {} 
			try { if(os != null) { os.close(); } } catch(Exception e) {} 
			try { if(ris != null) { ris.close(); }  } catch(Exception e) {} 
			try { if(ros != null) { ros.close(); } } catch(Exception e) {} 
			if(httpURLConnection != null) { httpURLConnection.disconnect(); }
		}
	}
	
	/**
	 * 공통으로 사용하는 내용을 util로 만듬
	 * @param request
	 * @return
	 */
	public static String getBrowser(HttpServletRequest request) {
	    // Untrusted User-Agent Header - 18.08.02
        // [조치] - 다운로드되는 파일명의 인코딩 처리를 위해 User-Agent를 활용하여 브라우저 구별을 하는 것으로 보안상 문제되지 않음
        //        - 그대로 유지
        String header =request.getHeader("User-Agent");
        if (header.contains("Trident")) {
               return "MSIE";
        } else if(header.contains("Chrome")) {
               return "Chrome";
        } else if(header.contains("Opera")) {
               return "Opera";
        }
        return "Firefox";
    }
	
	/**
	 * Untrusted Servlet Parameter - 18.08.02
	 * [조치] - parameter에 대한 공통 처리를 위해 추가
	 * 
     * request 객체의 parameter 값을 얻어온다.
     * 
     * 보안 취약점을 해결하기 위해 removeXss를 수행
     * @param request
     * @param key
     * @return String
     */
    public static String getParameter(HttpServletRequest request, String key) {
        String[] arrRes = getParameterValues(request, key);
        String res = null;
        
        if(arrRes != null && arrRes.length > 0) {
            res = arrRes[0];
        }
        return res;
    }
    
    /**
     * Untrusted Servlet Parameter - 18.08.02
     * [조치] - parameter에 대한 공통 처리를 위해 추가
     *        - Xss 방지 처리 추가
     * 
     * request 객체의 parameter 값을 얻어온다.
     * @param request
     * @param key
     * @return String[]
     */
    public static String[] getParameterValues(HttpServletRequest request, String key) {
        String[] res = null;
        if(request != null ) {
            
            res = request.getParameterValues(key) ;
            
            if(res != null && res.length > 0) {
                for(int i=0; i<res.length;i++) {
                    // 보안 취약점을 해결하기 위해 removeXss를 수행
                    res[i] = SecurityUtil.removeXss( res[i] ) ;
                }
            }
        }
        return res;
    }

}