package com.project.common.dto.Group.Request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReqGroupJoinDto {
	private int userSeq;
	private String memberAppeal;

	@Builder
	public ReqGroupJoinDto(int userSeq, String memberAppeal) {
		super();
		this.userSeq = userSeq;
		this.memberAppeal = memberAppeal;
	}
}
