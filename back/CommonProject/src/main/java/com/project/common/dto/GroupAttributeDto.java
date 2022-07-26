package com.project.common.dto;

import com.project.common.entity.GroupAttributeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupAttributeDto {
	private Long gaSeq;
	private Long groupSeq;
	private String gaRegion;
	private int gaStartDate;
	private int gaEndDate;
	private char gaChildJoin;
	private char gaGlobalJoin;
	private int gaAge;
	private Date gaCreatedAt;
	private Date gaUpdatedAt;
	
	
	@Builder
	public GroupAttributeDto(Long gaSeq, Long groupSeq, String gaRegion, int gaStartDate, int gaEndDate,
			char gaChildJoin, char gaGlobalJoin, int gaAge, Date gaCreatedAt, Date gaUpdatedAt) {
		super();
		this.gaSeq = gaSeq;
		this.groupSeq = groupSeq;
		this.gaRegion = gaRegion;
		this.gaStartDate = gaStartDate;
		this.gaEndDate = gaEndDate;
		this.gaChildJoin = gaChildJoin;
		this.gaGlobalJoin = gaGlobalJoin;
		this.gaAge = gaAge;
		this.gaCreatedAt = gaCreatedAt;
		this.gaUpdatedAt = gaUpdatedAt;
	}
	

	
    public GroupAttributeEntity toEntity(){
        return GroupAttributeEntity.builder()
                .gaSeq(gaSeq)
                .groupSeq(groupSeq)
                .gaRegion(gaRegion)
                .gaStartDate(gaStartDate)
                .gaEndDate(gaEndDate)
                .gaChildJoin(gaChildJoin)
                .gaGlobalJoin(gaGlobalJoin)
                .gaAge(gaAge)
                .gaCreatedAt(gaCreatedAt)
                .gaUpdatedAt(gaUpdatedAt).build();

    }




}
