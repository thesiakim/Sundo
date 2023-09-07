package common.user;

import java.io.Serializable;

@SuppressWarnings("all")
public class UserInfo implements Serializable {
	private String super_org_seq	= null;
	private String org_seq			= null;
	private String org_name			= null;
	private String user_seq			= null;
	private String user_id			= null;
	private String user_password	= null;
	private String user_name		= null;
	private String user_nickname	= null;
	private String user_email		= null;
	private String phone_number		= null;
	private String mobile_number	= null;
	private String address			= null;
	private String address_detail	= null;
	private String position_code	= null;
	private String job_code			= null;
	private String block_type		= null;
	private String block_reason		= null;
	private String block_reason_detail	= null;
	private String block_datetime	= null;
	private String dormancy_type	= null;
	private String last_login_datetime	= null;
	private String login_count		= null;
	private String leave_type		= null;
	private String leave_reason		= null;
	private String leave_reason_detail	= null;
	private String leave_datetime	= null;
	private String signup_type		= null;
	private String signup_reason	= null;
	private String ins_datetime		= null;
	private String upt_datetime		= null;
	private String password_datetime	= null;

	// [18.07.04 cslee 추가] 최상위 기관 org_seq 항목
    private String root_org_seq   = null;

    // 모바일 2차 인증 관련 항목
    private String mobile_auth_code;
	private String mobile_auth_fail_count;
	private String mobile_auth_issue_dttm;
	private String mobile_auth_2nd_yn;

	private String duplicate_login_yn;
	
	public String getSuper_org_seq() {
		return super_org_seq;
	}
	public void setSuper_org_seq(String super_org_seq) {
		this.super_org_seq = super_org_seq;
	}
	public String getOrg_seq() {
		return org_seq;
	}
	public void setOrg_seq(String org_seq) {
		this.org_seq = org_seq;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getUser_seq() {
		return user_seq;
	}
	public void setUser_seq(String user_seq) {
		this.user_seq = user_seq;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress_detail() {
		return address_detail;
	}
	public void setAddress_detail(String address_detail) {
		this.address_detail = address_detail;
	}
	public String getPosition_code() {
		return position_code;
	}
	public void setPosition_code(String position_code) {
		this.position_code = position_code;
	}
	public String getJob_code() {
		return job_code;
	}
	public void setJob_code(String job_code) {
		this.job_code = job_code;
	}
	public String getBlock_type() {
		return block_type;
	}
	public void setBlock_type(String block_type) {
		this.block_type = block_type;
	}
	public String getBlock_reason() {
		return block_reason;
	}
	public void setBlock_reason(String block_reason) {
		this.block_reason = block_reason;
	}
	public String getBlock_reason_detail() {
		return block_reason_detail;
	}
	public void setBlock_reason_detail(String block_reason_detail) {
		this.block_reason_detail = block_reason_detail;
	}
	public String getBlock_datetime() {
		return block_datetime;
	}
	public void setBlock_datetime(String block_datetime) {
		this.block_datetime = block_datetime;
	}
	public String getDormancy_type() {
		return dormancy_type;
	}
	public void setDormancy_type(String dormancy_type) {
		this.dormancy_type = dormancy_type;
	}
	public String getLast_login_datetime() {
		return last_login_datetime;
	}
	public void setLast_login_datetime(String last_login_datetime) {
		this.last_login_datetime = last_login_datetime;
	}
	public String getLogin_count() {
		return login_count;
	}
	public void setLogin_count(String login_count) {
		this.login_count = login_count;
	}
	public String getLeave_type() {
		return leave_type;
	}
	public void setLeave_type(String leave_type) {
		this.leave_type = leave_type;
	}
	public String getLeave_reason() {
		return leave_reason;
	}
	public void setLeave_reason(String leave_reason) {
		this.leave_reason = leave_reason;
	}
	public String getLeave_reason_detail() {
		return leave_reason_detail;
	}
	public void setLeave_reason_detail(String leave_reason_detail) {
		this.leave_reason_detail = leave_reason_detail;
	}
	public String getLeave_datetime() {
		return leave_datetime;
	}
	public void setLeave_datetime(String leave_datetime) {
		this.leave_datetime = leave_datetime;
	}
	public String getSignup_type() {
		return signup_type;
	}
	public void setSignup_type(String signup_type) {
		this.signup_type = signup_type;
	}
	public String getSignup_reason() {
		return signup_reason;
	}
	public void setSignup_reason(String signup_reason) {
		this.signup_reason = signup_reason;
	}
	public String getIns_datetime() {
		return ins_datetime;
	}
	public void setIns_datetime(String ins_datetime) {
		this.ins_datetime = ins_datetime;
	}
	public String getUpt_datetime() {
		return upt_datetime;
	}
	public void setUpt_datetime(String upt_datetime) {
		this.upt_datetime = upt_datetime;
	}
	public String getPassword_datetime() {
		return password_datetime;
	}
	public void setPassword_datetime(String password_datetime) {
		this.password_datetime = password_datetime;
	}
	// [18.07.04 cslee 추가] 최상위 기관 org_seq 항목
    public String getRoot_org_seq() {
        return root_org_seq;
    }
    public void setRoot_org_seq(String root_org_seq) {
        this.root_org_seq = root_org_seq;
    }

	public String getMobile_auth_code() {
		return mobile_auth_code;
	}

	public void setMobile_auth_code(String mobile_auth_code) {
		this.mobile_auth_code = mobile_auth_code;
	}

	public String getMobile_auth_fail_count() {
		return mobile_auth_fail_count;
	}

	public void setMobile_auth_fail_count(String mobile_auth_fail_count) {
		this.mobile_auth_fail_count = mobile_auth_fail_count;
	}

	public String getMobile_auth_issue_dttm() {
		return mobile_auth_issue_dttm;
	}

	public void setMobile_auth_issue_dttm(String mobile_auth_issue_dttm) {
		this.mobile_auth_issue_dttm = mobile_auth_issue_dttm;
	}

	public String getMobile_auth_2nd_yn() {
		return mobile_auth_2nd_yn;
	}

	public void setMobile_auth_2nd_yn(String mobile_auth_2nd_yn) {
		this.mobile_auth_2nd_yn = mobile_auth_2nd_yn;
	}

	public String getDuplicate_login_yn() {
		return duplicate_login_yn;
	}

	public void setDuplicate_login_yn(String duplicate_login_yn) {
		this.duplicate_login_yn = duplicate_login_yn;
	}
}

