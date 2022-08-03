package com.project.common.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SimpleGroupDto {
	private long groupSeq;
	private String groupName;
	private String attachSeq;
	private String groupMaker;
	private String groupDescription;
	private int groupTotalCount;
	private char groupAccessType;
	private String groupPassword;
	private String groupStatus;
	private char groupIsActive;
	
	private String groupRegion;
	private int groupStartDate;
	private int groupEndDate;
	private char groupChild;
	private char groupGlobal;
	private int groupAgeRange;

	@Builder
	public SimpleGroupDto(long groupSeq, String groupName, String attachSeq, String groupMaker, String groupDescription,
			int groupTotalCount, char groupAccessType, String groupPassword, String groupStatus, char groupIsActive,
			String groupRegion, int groupStartDate, int groupEndDate, char groupChild, char groupGlobal,
			int groupAgeRange) {
		this.groupSeq = groupSeq;
		this.groupName = groupName;
		this.attachSeq = attachSeq;
		this.groupMaker = groupMaker;
		this.groupDescription = groupDescription;
		this.groupTotalCount = groupTotalCount;
		this.groupAccessType = groupAccessType;
		this.groupPassword = groupPassword;
		this.groupStatus = groupStatus;
		this.groupIsActive = groupIsActive;
		this.groupRegion = groupRegion;
		this.groupStartDate = groupStartDate;
		this.groupEndDate = groupEndDate;
		this.groupChild = groupChild;
		this.groupGlobal = groupGlobal;
		this.groupAgeRange = groupAgeRange;
	}

	




}
