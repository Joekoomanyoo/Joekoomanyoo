package com.project.common.dto.Group.Request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.common.entity.Group.GroupEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReqGroupDto {
private int groupSeq;  
	
	// 모임 기본 정보 //
    private String groupName;
    private String groupImgUrl;
    private String groupDescription;
    private char groupAccessType;
    private String groupPassword;
    private int groupMaxCount;
   	private String groupRegion;

   	
	private int groupAgeRange;
   	private char groupWithChild;
   	private char groupWithGlobal;
	
   	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
   	private Date groupStartDate;
   	
   	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
   	private Date groupEndDate;

   	
    public GroupEntity toEntity(){
        return GroupEntity.builder()
                .groupSeq(groupSeq)
                .groupName(groupName)
                .groupImgUrl(groupImgUrl)
                .groupDescription(groupDescription)
                .groupAccessType(groupAccessType)
                .groupPassword(groupPassword)
                .groupMaxCount(groupMaxCount)
                .groupRegion(groupRegion)
                .groupStartDate(groupStartDate)
                .groupEndDate(groupEndDate)
                .groupAgeRange(groupAgeRange)
                .groupWithChild(groupWithChild)
                .groupWithGlobal(groupWithGlobal)
                .build();
    }
}
