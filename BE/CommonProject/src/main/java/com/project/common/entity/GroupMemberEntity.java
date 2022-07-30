package com.project.common.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name="tb_group_member")
public class GroupMemberEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_participant_id")
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
	public GroupMemberEntity(long memberSeq, long userSeq, long groupSeq, int memberStatus, String memberJoinAppeal,
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



//	public void checkPresenter(long userId) {
//        study.checkPresenter(userId);
//    }





}
