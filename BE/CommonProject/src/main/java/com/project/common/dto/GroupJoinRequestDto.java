package com.project.common.dto;

import java.util.Date;

import com.project.common.entity.GroupMemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupJoinRequestDto {

	private long userSeq;
	private String memberAppeal;

	@Builder
	public GroupJoinRequestDto(long userSeq, String memberAppeal) {
		super();
		this.userSeq = userSeq;
		this.memberAppeal = memberAppeal;
	}
}
