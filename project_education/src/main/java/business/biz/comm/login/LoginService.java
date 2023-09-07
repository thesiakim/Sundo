package business.biz.comm.login;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import business.biz.dto.LoginDto;
import business.biz.model.Member;
import commf.dao.CommonDAOImpl;
import common.base.BaseService;



/**
 * @author jogyeongmin
 * 로그인 관련 Service
 */
@Service
@SuppressWarnings({"rawtypes", "unchecked"})
public class LoginService extends BaseService {
	
	/** 로그 객체 */
	public static final Logger LOGGER = Logger.getLogger(LoginService.class);
	
    @Autowired
    private CommonDAOImpl dao;
    
    
    
	/**
	 * @param loginDto(user_id, user_pw)
	 * @return 
	 * DB 정상 조회 시 : Member
	 * 오류 : Exception
	 */
	public Member doLogin(LoginDto loginDto) {
		
		ArrayList<Member> matchingId = new ArrayList<>();
		
		try {
			matchingId = (ArrayList<Member>) dao.list("Login.findMatchingId",loginDto);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		switch(matchingId.size()) {
			case 1 : return matchingId.get(0);
			case 0 : throw new IllegalArgumentException("일치하는 회원이 없습니다.");
			default : throw new IllegalArgumentException("회원 오류. 관리자에게 문의하세요");
		}
		
	}
    
    
}