package com.project.common.entity.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.project.common.entity.User.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
@Getter @Setter
@ToString
@Table(name="tb_group")
public class GroupEntity {

	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="group_seq")
	private int groupSeq;
    
	// 모임 기본 정보 //
	@Column(name="group_name")
    private String groupName;
    
    @Column(name="group_img_url")
    private String groupImgUrl;
 
    @Column(name="group_master")
    private String groupMaster;
    
    @Column(name= "group_description")
    private String groupDescription;
    
    @Column(name="group_access_type")
    private char groupAccessType;
    
    @Column(name="group_pwd")
    private String groupPassword;
    
    @Column(name="group_total_count")
    private int groupMaxCount;
    
    @Column(name="group_region")
   	private String groupRegion;
       
	@Temporal(TemporalType.DATE)
    @Column(name="group_start_date")
   	private Date groupStartDate;
       
	@Temporal(TemporalType.DATE)
    @Column(name="group_end_date")
   	private Date groupEndDate;
       
    @Column(name="group_age_range")
   	private int groupAgeRange;

    @Column(name="group_child")
   	private char groupWithChild;
       
    @Column(name="group_global")
   	private char groupWithGlobal;
    
    @Column(name="group_is_active")
   	private char groupActive;

    @Column(name="group_status")
   	private char groupStatus;
    
    // 모임 설정 정보 //
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="group_created_at")
    private Date createdTime;
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="group_updated_at")
    private Date updatedTime;

    @ManyToOne
	@JoinColumn(name="user_seq")
	private UserEntity user;
    

    // 모임 멤버
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private List<GroupMemberEntity> members = new ArrayList<>();
    
    // 모임 멤버 Method
    public void addGroupMember(GroupMemberEntity groupMember) {
    	this.members.add(groupMember);
    	groupMember.setGroup(this);
    }

    public void removeGroupMember(int memberSeq) {
        this.members.removeIf(groupMember ->
                groupMember.getMemberSeq()==memberSeq);
    }
    
    
    
    // 모임 일정
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private List<GroupScheduleEntity> schedules = new ArrayList<>();
    
    // 모임 일정 Method
    public void addGroupSchedule(GroupScheduleEntity groupSchedule) {
    	this.schedules.add(groupSchedule);
    	groupSchedule.setGroup(this);
    }

    public void removeGroupSchedule(int gsSeq) {
        this.schedules.removeIf(groupSchedule ->
        groupSchedule.getGsSeq()==gsSeq);
    }
    
    
    
    // 모임 목적지
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private List<GroupDestinationEntity> destinations = new ArrayList<>();
    
    // 모임 목적지  Method
    public void addGroupDestination(GroupDestinationEntity groupDestination) {
    	this.destinations.add(groupDestination);
    	groupDestination.setGroup(this);
    }

    public void removeGroupDestination(int gdSeq) {
        this.destinations.removeIf(groupDestination ->
        groupDestination.getGdSeq()==gdSeq);
    }
    
 
}
