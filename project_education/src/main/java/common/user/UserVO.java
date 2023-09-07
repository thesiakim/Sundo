package common.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
* Class Name : UserVO
* Description : 사용자 정보 객체
* Modification Information
* 
* 수정일		수정자		수정내용
* ------- -------- ---------------------------
* 2014. 9. 17.	jackshy		최초 생성
*
* @author 스마트 재난/안전 플랫폼 – 사용자 정보 객체(jackshy)
* @since 2014. 9. 17.
* @version 1.0
* 
* Copyright (C) 2014 by MOSPA All right reserved.
*/
public class UserVO implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	/** 사용자 아이디 */
	private String username;
	
	/** 패스워드 */
	private String password;
	
	/** 패스워드 변경 알림 */
	private String pwdChgAlert;
	
	/** 최근접속 시간 */
	private String recentLogin;
	
	/** 권한 관련 객체 리스트 */
	private final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	/** 최근접속 시간 */	
	private String manager;	
	
	/** 기관 코드  **/
	private Integer orgCd;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	//@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	
	
	public String getManager() {
		return manager;
	}



	public void setManager(String manager) {
		this.manager = manager;
	}



	public String getRecentLogin() {
		return recentLogin;
	}



	public void setRecentLogin(String recentLogin) {
		this.recentLogin = recentLogin;
	}



	public String getPwdChgAlert() {
		return pwdChgAlert;
	}



	public void setPwdChgAlert(String pwdChgAlert) {
		this.pwdChgAlert = pwdChgAlert;
	}


	public Integer getOrg_cd() {
		return orgCd;
	}

	public void setOrg_cd(Integer orgCd) {
		this.orgCd = orgCd;
	}



	/**
	* 권한 등록 메서드
	* @param authority - 권한 객체
	* @return 없음
	*/
	public void setAuthorities(GrantedAuthority authority){
		authorities.clear();
		authorities.add(authority);
	}
	
	/**
	* 패스워드 등록 메서드 
	* @param value - 패스워드
	* @return 없음
	*/
	public void setPassword(String value) {
		password = value;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	//@Override
	public String getPassword() {
		return password;
	}
	
	/**
	* 사용자 아이디 등록 메서드
	* @param value - 사용자 아이디
	* @return 없음
	*/
	public void setUsername(String value) {
		username = value;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	//@Override
	public String getUsername() {
		return username;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	//@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	//@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	//@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	//@Override
	public boolean isEnabled() {
		return true;
	}

}
