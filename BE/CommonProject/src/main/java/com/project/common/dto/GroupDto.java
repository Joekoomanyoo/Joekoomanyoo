package com.project.common.dto;



import java.util.Date;

import com.project.common.entity.GroupEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupDto {
	private long groupSeq;
	private String attachSeq;
	private String groupMaker;
	private String groupName;
	private String groupDescription;
	private char groupAccessType;
	private String groupPassword;
	private String groupStatus;
	private char groupIsActive;
	private int groupTotalCount;
	private String groupRegion;
	private int groupStartDate;
	private int groupEndDate;
	private char groupChild;
	private char groupGlobal;
	private int groupAgeRange;
	private Date groupCreatedAt;
	private Date groupUpdatedAt;

	@Builder
	public GroupDto(long groupSeq, String attachSeq, String groupMaker, String groupName, String groupDescription,
			char groupAccessType, String groupPassword, String groupStatus, char groupIsActive, int groupTotalCount,
			String groupRegion, int groupStartDate, int groupEndDate, char groupChild, char groupGlobal,
			int groupAgeRange, Date groupCreatedAt, Date groupUpdatedAt) {
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

    public GroupEntity toEntity(){
        return GroupEntity.builder()
                .groupSeq(groupSeq)
                .attachSeq(attachSeq)
                .groupMaker(groupMaker)
                .groupName(groupName)
                .groupDescription(groupDescription)
                .groupAccessType(groupAccessType)
                .groupPassword(groupPassword)
                .groupStatus(groupStatus)
                .groupIsActive(groupIsActive)
                .groupTotalCount(groupTotalCount)
                .groupRegion(groupRegion)
                .groupStartDate(groupStartDate)
                .groupEndDate(groupEndDate)
                .groupChild(groupChild)
                .groupGlobal(groupGlobal)
                .groupAgeRange(groupAgeRange)
                .groupCreatedAt(groupCreatedAt)
                .groupUpdatedAt(groupUpdatedAt).build();

    }












}
