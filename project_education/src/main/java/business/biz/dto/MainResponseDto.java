package business.biz.dto;

import lombok.Data;

@Data
public class MainResponseDto {
	private long id;
	private String name;
	private long parent_id;
	private int level;
	private boolean child_yn;
}
