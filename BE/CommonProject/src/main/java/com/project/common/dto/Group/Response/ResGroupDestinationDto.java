package com.project.common.dto.Group.Response;

import com.project.common.entity.Group.GroupDestinationEntity;
import com.project.common.entity.Heritage.HeritageEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResGroupDestinationDto {
    
	private int heritageSeq;
    private char gdCompleted;
    private HeritageEntity heritage;
    
    public ResGroupDestinationDto(GroupDestinationEntity destination,HeritageEntity heritage) {
    	super();
    	this.heritageSeq = destination.getHeritageSeq();
    	this.gdCompleted = destination.getGdCompleted();
    	this.heritage=heritage;
    }
    
  




}

