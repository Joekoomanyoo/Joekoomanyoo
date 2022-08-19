package com.project.common.dto.Group;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupScheduleEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class GroupScheduleDto {

	private int gsSeq;
    private String gsContent;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date gsDateTime;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date gsRegisteredAt;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date gsUpdatedAt;
	
	private GroupEntity group;
  
	public GroupScheduleDto(GroupScheduleEntity schedule) {
		this.gsSeq = schedule.getGsSeq();
		this.gsContent = schedule.getGsContent();
		this.gsDateTime = schedule.getGsDateTime();
		this.gsRegisteredAt = schedule.getGsRegisteredAt();
		this.gsUpdatedAt=schedule.getGsUpdatedAt();
	}
	
    public GroupScheduleEntity toEntity(){
        return GroupScheduleEntity.builder()
                .gsSeq(gsSeq)
                .gsContent(gsContent)
                .gsDateTime(gsDateTime)
                .gsRegisteredAt(gsRegisteredAt)
                .gsUpdatedAt(gsUpdatedAt)
                .group(group)
                .build();
    }

    @Builder
	public GroupScheduleDto(int gsSeq, String gsContent, Date gsDateTime, Date gsRegisteredAt,
			Date gsUpdatedAt, GroupEntity group) {
		super();
		this.gsSeq = gsSeq;
		this.gsContent = gsContent;
		this.gsDateTime = gsDateTime;
		this.gsRegisteredAt = gsRegisteredAt;
		this.gsUpdatedAt = gsUpdatedAt;
		this.group = group;
	}
}

