package com.project.common.dto.Group.Response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.common.entity.Group.GroupScheduleEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResGroupScheduleDto {

	private int groupSeq;
	private int gsSeq;
    private String gsContent;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date gsDateTime;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date gsRegisteredAt;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date gsUpdatedAt;
	
	
	public ResGroupScheduleDto(GroupScheduleEntity schedule) {
		this.groupSeq=schedule.getGroup().getGroupSeq();
		this.gsSeq = schedule.getGsSeq();
		this.gsContent = schedule.getGsContent();
		this.gsDateTime = schedule.getGsDateTime();
		this.gsRegisteredAt = schedule.getGsRegisteredAt();
		this.gsUpdatedAt=schedule.getGsUpdatedAt();
	}
}
