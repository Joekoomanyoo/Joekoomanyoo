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
	private String memberJoinAppeal;
	private char memberEval;
	private Date memberInAt;
	private Date memberCreatedAt;
	private Date memberUpdatedAt;
	
	@Builder
	public GroupMemberDto(long memberSeq, long userSeq, long groupSeq, int memberStatus, String memberJoinAppeal,
			char memberEval, Date memberInAt, Date memberCreatedAt, Date memberUpdatedAt) {
		this.memberSeq = memberSeq;
		this.userSeq = userSeq;
		this.groupSeq = groupSeq;
		this.memberStatus = memberStatus;
		this.memberJoinAppeal = memberJoinAppeal;
		this.memberEval = memberEval;
		this.memberInAt = memberInAt;
		this.memberCreatedAt = memberCreatedAt;
		this.memberUpdatedAt = memberUpdatedAt;
	}
	
    public GroupMemberEntity toEntity(){
        return GroupMemberEntity.builder()
                .memberSeq(memberSeq)
                .userSeq(userSeq)
                .groupSeq(groupSeq)
                .memberStatus(memberStatus)
                .memberJoinAppeal(memberJoinAppeal)
                .memberEval(memberEval)
                .memberInAt(memberInAt)
                .memberCreatedAt(memberCreatedAt)
                .memberUpdatedAt(memberUpdatedAt).build();

    }

}
