package com.project.common.dto.Group;

import java.time.LocalDateTime;

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
	private long gsDateTime;
	private LocalDateTime gsRegisteredAt;
    private LocalDateTime gsUpdatedAt;
	private GroupEntity group;
    
 
	public GroupScheduleDto(GroupScheduleEntity schedule) {
		this.gsContent = schedule.getGsContent();
		this.gsDateTime = schedule.getGsDateTime();
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
	public GroupScheduleDto(int gsSeq, String gsContent, long gsDateTime, LocalDateTime gsRegisteredAt,
			LocalDateTime gsUpdatedAt, GroupEntity group) {
		super();
		this.gsSeq = gsSeq;
		this.gsContent = gsContent;
		this.gsDateTime = gsDateTime;
		this.gsRegisteredAt = gsRegisteredAt;
		this.gsUpdatedAt = gsUpdatedAt;
		this.group = group;
	}
}
