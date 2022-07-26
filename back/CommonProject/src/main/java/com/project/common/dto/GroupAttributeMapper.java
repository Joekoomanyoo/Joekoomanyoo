package com.project.common.dto;

import org.mapstruct.factory.Mappers;

import com.project.common.entity.GroupAttributeEntity;

public interface GroupAttributeMapper extends Mapper<GroupAttributeDto,GroupAttributeEntity>{
	GroupAttributeMapper MAPPER = Mappers.getMapper(GroupAttributeMapper.class);
	
	@Override
	GroupAttributeEntity toEntity(final GroupAttributeDto dto);
	
	@Override
	GroupAttributeDto toDto(final GroupAttributeEntity entity);
	

	
	
}