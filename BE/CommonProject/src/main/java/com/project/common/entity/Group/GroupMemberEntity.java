package com.project.common.entity.Group;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter 
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name="tb_group_member")
public class GroupMemberEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
	private int memberSeq;
	
	@Column(name = "member_status")
	private int memberStatus;
	
	@Column(name = "member_appeal")
	private String memberAppeal;
	
	@Column(name = "member_is_evaluated")
	private char memberIsEvaluated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "member_approve_at")
	private Date approveTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_created_at")
	private Date createdTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "member_updated_at")
    private Date updatedTime;
	
    @Column(name = "user_seq")
    private int userSeq;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_seq")
	private GroupEntity group;

}
