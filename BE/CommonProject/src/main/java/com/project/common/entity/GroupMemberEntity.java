package com.project.common.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@ToString
@Table(name="tb_group_member")
public class GroupMemberEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
	private long memberSeq;
	
	@Column(name="user_seq")
	private long userSeq;
	
	@ManyToOne
	@JoinColumn(name="group_seq")
	private GroupEntity group;

	
	
	@Column(name = "member_status",nullable=false)
	private int memberStatus;
	
	@Column(name = "member_appeal",nullable=false)
	private String memberAppeal;
	
	@Column(name = "member_is_evaluated",nullable=false)
	private char memberIsEvaluated;
	
	@Column(name = "member_in_at",nullable=false)
	private Date memberInAt;
	
	@Column(name = "member_created_at")
	private Date memberCreatedAt;
	
	@Column(name = "member_updated_at")
	private Date memberUpdatedAt;

	
	@Builder
	public GroupMemberEntity(long memberSeq, long userSeq, GroupEntity group, int memberStatus, String memberAppeal,
			char memberIsEvaluated, Date memberInAt, Date memberCreatedAt, Date memberUpdatedAt) {
		super();
		this.memberSeq = memberSeq;
		this.userSeq = userSeq;
		this.group = group;
		this.memberStatus = memberStatus;
		this.memberAppeal = memberAppeal;
		this.memberIsEvaluated = memberIsEvaluated;
		this.memberInAt = memberInAt;
		this.memberCreatedAt = memberCreatedAt;
		this.memberUpdatedAt = memberUpdatedAt;
	}

	
	
	


//	public void checkPresenter(long userId) {
//        study.checkPresenter(userId);
//    }





}
