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
@Table(name="tb_group")
public class GroupEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupSeq;
    
    @JoinColumn(name="group_seq")
	private String attachSeq;
    
    
    @Column(name="group_maker",nullable = false)
    private Long groupMaker;

    @Column(name="group_pwd",length = 100, nullable = false)
    private String groupPassword;
    
    @Column(name="group_name",nullable=false)
    private String groupName;
    
    @Column(name="group_max",nullable=false)
	private int groupMaxCount;
    
    @Column(name= "group_description", nullable=false)
    private String groupDescription;
    
    @Column(name="group_status",nullable=false)
    private String groupStatus;
    
    @Column(name="group_is_active",length = 1, nullable = false)
    private char groupIsActive;
    
    @Column(name="group_access_type",length = 1, nullable = false)
    private char groupAccessType;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date groupCreatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date groupUpdatedAt;
    
//    @OneToOne(mappedBy = "groupEntity",cascade = CascadeType.ALL)
//    private GroupAttributeEntity groupAttributeEntity;

    @Builder
	public GroupEntity(Long groupSeq, String attachSeq, Long groupMaker, String groupPassword, String groupName,
			int groupMaxCount, String groupDescription, String groupStatus, char groupIsActive, char groupAccessType,
			Date groupCreatedAt, Date groupUpdatedAt, GroupAttributeEntity groupAttributeEntity) {
		super();
		this.groupSeq = groupSeq;
		this.attachSeq = attachSeq;
		this.groupMaker = groupMaker;
		this.groupPassword = groupPassword;
		this.groupName = groupName;
		this.groupMaxCount = groupMaxCount;
		this.groupDescription = groupDescription;
		this.groupStatus = groupStatus;
		this.groupIsActive = groupIsActive;
		this.groupAccessType = groupAccessType;
		this.groupCreatedAt = groupCreatedAt;
		this.groupUpdatedAt = groupUpdatedAt;
	//	this.groupAttributeEntity = groupAttributeEntity;
	}


	
    
    

    

}
