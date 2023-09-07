package business.biz.model;

import lombok.Data;

@Data
public class Member {
	private String member_id;
	private String password;
	private String name;
	private String email;
	private String phone_number;
}
