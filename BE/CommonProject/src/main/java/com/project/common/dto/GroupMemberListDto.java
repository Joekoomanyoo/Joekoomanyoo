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
public class GroupMemberListDto {

	private long userNickName;
	private long groupSeq;
	private int memberStatus;
	private String memberAppeal;
	private char memberIsEvaluated;
	
	@Builder
	public GroupMemberListDto(GroupMemberEntity member) {
		this.userNickName = member.getUserSeq();
		this.groupSeq = member.getGroup().getGroupSeq();
		this.memberStatus = member.getMemberStatus();
		this.memberAppeal = member.getMemberAppeal();
		this.memberIsEvaluated = member.getMemberIsEvaluated();
		
	}
	

	

}
