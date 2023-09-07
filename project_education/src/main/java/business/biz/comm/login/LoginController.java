package business.biz.comm.login;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import business.biz.Constants;
import business.biz.dto.LoginDto;
import business.biz.model.Member;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.FileUtil;
import common.util.properties.ApplicationProperty;


/**
 * @author jogyeongmin
 * 로그인 관련 컨트롤
 */
@EnableWebMvc
@Controller
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class LoginController extends BaseController {


    @Autowired
    private LoginService  loginService;

	@Autowired
	private HttpSession session;
	
	
	
	/**
	 * 2023-05-26 조경민 
	 * 로그인 유무에 따라 로그인, 메인페이지 반환  
	 */
	@GetMapping("/login")
	public String login() {
		// 로그인 정보 조회
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		// 세션에 로그인 정보 있을 경우 메인으로 이동
		if(loginMember != null) {
			return "redirect:/main.do";
		}
		
		// 로그인페이지 출력
		return "login/login";
	}
	
	/**
	 * 2023-05-26 조경민
	 * 로그인 Api 메서드(Ajax 사용)
	 * 로그인 성공시 "/main", 실패시 401 코드와 오류메세지 반환
	 * @param loginDto: user_id, user_pw
	 */
	@ResponseBody
	@PostMapping(value= "/api/v1/login", produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> doLogin(@RequestBody LoginDto loginDto) {
		Member loginMember;
		
		// 회원 찾아오기
		try {
			loginMember = loginService.doLogin(loginDto);
			
		}catch(IllegalArgumentException e){
			
			// 에러 발생시 에러메세지 반환
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
					
		}
		
		// 로그인 회원 정보 저장
		session.setAttribute("loginMember", loginMember);
		
		// 이동해야 할 url 반환
		return ResponseEntity.ok("/main");
		
	}
	
	
	
	/**
	 * 2023-05-26 조경민
	 * 로그아웃 메서드
	 */
	@GetMapping("/logout")
    public String logout() {
        if (session != null) {
        	// 세션 무효화
            session.removeAttribute("loginMember"); 
        }
        
        // 로그아웃 후 리다이렉트할 경로
        return "redirect:/"; 
    }
	
	
	
	
}
