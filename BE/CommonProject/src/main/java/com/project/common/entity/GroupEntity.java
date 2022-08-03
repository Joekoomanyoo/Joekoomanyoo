package com.project.common.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name="tb_group")
public class GroupEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="group_seq",insertable=false,updatable=false)
    private Long groupSeq;
    
    @Column(name="attach_seq")
	private String attachSeq;
   
    @Column(name="group_maker")
    private String groupMaker;

    @Column(name="group_name",nullable=false)
    private String groupName;

    @Column(name= "group_description", nullable=false)
    private String groupDescription;
    
    @Column(name="group_access_type",length = 1, nullable = false)
    private char groupAccessType;
    
    @Column(name="group_pwd",length = 100, nullable = false)
    private String groupPassword;
    
    @Column(name="group_status",nullable=false)
    private String groupStatus;
    
    @Column(name="group_is_active",length = 1, nullable = false)
    private char groupIsActive;
    
    @Column(name="group_total_count",nullable=false)
	private int groupTotalCount;
    
    @Column(name="group_region",nullable=false)
	private String groupRegion;
    
    @Column(name="group_start_date",nullable=false)
	private int groupStartDate;
    
    @Column(name="group_end_date",nullable=false)
	private int groupEndDate;
    
    @Column(name="group_child",nullable=false)
	private char groupChild;
    
    @Column(name="group_global",nullable=false)
	private char groupGlobal;
    
    @Column(name="group_age_range",nullable=false)
	private int groupAgeRange;
    
    @OneToMany(mappedBy="group")
    private List<GroupMemberEntity> members = new ArrayList<>();
    
    public void addMember(GroupMemberEntity groupMember) {
    	this.members.add(groupMember);
    	groupMember.setGroup(this);
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name="group_created_at")
    private Date groupCreatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name="group_updated_at")
    private Date groupUpdatedAt;
    

    @Builder
	public GroupEntity(Long groupSeq, String attachSeq, String groupMaker, String groupName, String groupDescription,
			char groupAccessType, String groupPassword, String groupStatus, char groupIsActive, int groupTotalCount,
			String groupRegion, int groupStartDate, int groupEndDate, char groupChild, char groupGlobal, int groupAgeRange,
			Date groupCreatedAt, Date groupUpdatedAt) {
		super();
		this.groupSeq = groupSeq;
		this.attachSeq = attachSeq;
		this.groupMaker = groupMaker;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.groupAccessType = groupAccessType;
		this.groupPassword = groupPassword;
		this.groupStatus = groupStatus;
		this.groupIsActive = groupIsActive;
		this.groupTotalCount = groupTotalCount;
		this.groupRegion = groupRegion;
		this.groupStartDate = groupStartDate;
		this.groupEndDate = groupEndDate;
		this.groupChild = groupChild;
		this.groupGlobal = groupGlobal;
		this.groupAgeRange = groupAgeRange;
		this.groupCreatedAt = groupCreatedAt;
		this.groupUpdatedAt = groupUpdatedAt;
	}
    

    
    

    

    

}
