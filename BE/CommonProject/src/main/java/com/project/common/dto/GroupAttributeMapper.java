package com.project.common.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.common.entity.GroupAttributeEntity;

@Mapper
public interface GroupAttributeMapper extends StructMapper<GroupAttributeDto,GroupAttributeEntity>{
	GroupAttributeMapper MAPPER = Mappers.getMapper(GroupAttributeMapper.class);
	
	@Override
	GroupAttributeEntity toEntity(final GroupAttributeDto dto);
	
	@Override
	GroupAttributeDto toDto(final GroupAttributeEntity entity);
	

	
	
}