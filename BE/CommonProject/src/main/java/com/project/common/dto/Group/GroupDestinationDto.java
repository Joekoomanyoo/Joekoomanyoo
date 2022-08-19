package com.project.common.dto.Group;

import com.project.common.entity.Group.GroupDestinationEntity;
import com.project.common.entity.Group.GroupEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupDestinationDto {
    private int gdSeq;
    private int heritageSeq;
    private char gdCompleted;
    private GroupEntity group;
    
    @Builder
    public GroupDestinationDto(int gdSeq, int heritageSeq, char gdCompleted, GroupEntity group) {
    	super();
    	this.gdSeq = gdSeq;
    	this.heritageSeq = heritageSeq;
    	this.gdCompleted = gdCompleted;
    	this.group = group;
    }
    
   
    public GroupDestinationEntity toEntity(){
        return GroupDestinationEntity.builder()
                .gdSeq(gdSeq)
                .gdCompleted(gdCompleted)
                .group(group)
                .heritageSeq(heritageSeq)
                .build();
    }




}

