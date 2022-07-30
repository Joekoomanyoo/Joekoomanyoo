package com.project.common.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name="tb_group_attribute")
public class GroupAttributeEntity {

  
//	@Id
//	@Column(name="ga_seq",nullable=false)
//	private Long gaSeq;
	
//    @Column(name="group_seq",nullable=false)
//	private Long groupSeq;
	@Id
	//@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="group_seq")
	private Long groupSeq;

    @Column(name="ga_region",nullable=false)
	private String gaRegion;

    @Column(name="ga_start_date",nullable=false)
	private int gaStartDate;

    @Column(name="ga_end_date",nullable=false)
	private int gaEndDate;
	
	@Column(name="ga_child_join",length = 1, nullable = false)
	private char gaChildJoin;
	
	@Column(name="ga_global_join",length = 1, nullable = false)
	private char gaGlobalJoin;
	
	@Column(name="ga_age",nullable=false)
	private int gaAge;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date gaCreatedAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date gaUpdatedAt;
	

	@Builder
	public GroupAttributeEntity(Long groupSeq,String gaRegion, int gaStartDate, int gaEndDate,
			char gaChildJoin, char gaGlobalJoin, int gaAge, Date gaCreatedAt, Date gaUpdatedAt) {
		super();
	//	this.gaSeq = gaSeq;
	this.groupSeq = groupSeq;
		this.gaRegion = gaRegion;
		this.gaStartDate = gaStartDate;
		this.gaEndDate = gaEndDate;
		this.gaChildJoin = gaChildJoin;
		this.gaGlobalJoin = gaGlobalJoin;
		this.gaAge = gaAge;
		this.gaCreatedAt = gaCreatedAt;
		this.gaUpdatedAt = gaUpdatedAt;
	//	this.groupEntity = groupEntity;
	}


	
	
	
 
}
