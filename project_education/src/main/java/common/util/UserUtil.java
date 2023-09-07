package common.util;

import org.springframework.security.core.context.SecurityContextHolder;

import business.biz.Constants;
import common.user.UserVO;

public class UserUtil {
	/**
	* 세션 사용자 객체 가져오는 메서드
	* @return 사용자 정보 객체
	*/
	public static UserVO getUserVO() {
		try{
			return (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}

	}

	public static void setRecentLogin(String value) {
		try{
			getUserVO().setRecentLogin(value);
		}catch(Exception e) {
		}
	}

	public static String getRecentLogin() {
		try{
			return getUserVO().getRecentLogin();
		}catch(Exception e) {
			return null;
		}
	}

	public static void setPwdChgAlert(String value) {
		try{
			getUserVO().setPwdChgAlert(value);
		}catch(Exception e) {
		}
	}

	public static String getPwdChgAlert() {
		try{
			return getUserVO().getPwdChgAlert();
		}catch(Exception e) {
			return null;
		}
	}

	/**
	* 세션 사용자 권한 가져오는 메서드
	* @return 사용자 권한
	*/
	public static String getRole() {
		if(UserUtil.getUserVO() != null) {
			return UserUtil.getUserVO().getAuthorities().iterator().next().getAuthority();
		}else{
			return null;
		}
	}

	/**
	* 세션 사용자 관리자 권한인지 체크하는 메서드
	* @return 관리자 권한 여부
	*/
	public static boolean isAdmin() {
		String role = UserUtil.getRole();

		return role.equals(Constants.ROLE_ADMIN);
	}

	/**
	* 세션 사용자 접속 권한 없는 지 여부 확인
	* @return 접속 권한 여부
	*/
	public static boolean isFail() {
		String role = UserUtil.getRole();

		return role.equals(Constants.ROLE_FAILURE);
	}

	/**
	* 세션 사용자 아이디 가져오는 메서드
	* @return 사용자 아이디
	*/
	public static String getUsername() {
		if(UserUtil.getUserVO() != null) {
			return UserUtil.getUserVO().getUsername();
		}else{
			return null;
		}

	}

	/**
	* 세션 사용자 아이디 체크
	* @param check - 체크할 사용자 아이디
	* @return 동일 여부
	*/
	public static boolean checkUsername(String check) {
		String username = UserUtil.getUsername();

		return username.equals(check);
	}

	/**
	* 세션 사용자 기관 코드
	* @return Integer
	*/
	public static Integer getOrgCd(){
		if(UserUtil.getUserVO() != null) {
			return UserUtil.getUserVO().getOrg_cd();
		}else{
			return null;
		}
	}
}
