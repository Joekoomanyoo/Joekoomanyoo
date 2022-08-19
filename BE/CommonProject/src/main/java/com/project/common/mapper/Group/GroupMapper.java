package com.project.common.mapper.Group;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.common.dto.Group.GroupDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.mapper.StructMapper;

@Mapper
public interface GroupMapper extends StructMapper<GroupDto,GroupEntity>{
	GroupMapper MAPPER = Mappers.getMapper(GroupMapper.class);
	
	@Override
	GroupEntity toEntity(final GroupDto dto);
	
	@Override
	GroupDto toDto(final GroupEntity entity);
	
	@Override
	List<GroupDto> toDtoList(List<GroupEntity> entityList);
	
}