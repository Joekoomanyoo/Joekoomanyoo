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
public class GroupMemberDto {

	private long memberSeq;
	private long userSeq;
	private long groupSeq;
	private int memberStatus;
	private String memberAppeal;
	private char memberIsEvaluated;
	private Date memberInAt;
	private Date memberCreatedAt;
	private Date memberUpdatedAt;
	private GroupDto group;
	
	@Builder
	public GroupMemberDto(long memberSeq, long userSeq, long groupSeq, int memberStatus, String memberAppeal,
			char memberIsEvaluated, Date memberInAt, Date memberCreatedAt, Date memberUpdatedAt, GroupDto group) {
		this.memberSeq = memberSeq;
		this.userSeq = userSeq;
		this.groupSeq = groupSeq;
		this.memberStatus = memberStatus;
		this.memberAppeal = memberAppeal;
		this.memberIsEvaluated = memberIsEvaluated;
		this.memberInAt = memberInAt;
		this.memberCreatedAt = memberCreatedAt;
		this.memberUpdatedAt = memberUpdatedAt;
		this.group = group;
	}
	
    public GroupMemberEntity toEntity(){
        return GroupMemberEntity.builder()
                .memberSeq(memberSeq)
                .userSeq(userSeq)
                .memberStatus(memberStatus)
                .memberAppeal(memberAppeal)
                .memberIsEvaluated(memberIsEvaluated)
                .memberInAt(memberInAt)
                .memberCreatedAt(memberCreatedAt)
                .memberUpdatedAt(memberUpdatedAt).build();

    }

	

}
