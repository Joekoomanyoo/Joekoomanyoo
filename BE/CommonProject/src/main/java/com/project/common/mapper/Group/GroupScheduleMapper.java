package com.project.common.mapper.Group;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.common.dto.Group.GroupScheduleDto;
import com.project.common.entity.Group.GroupScheduleEntity;
import com.project.common.mapper.StructMapper;

@Mapper
public interface GroupScheduleMapper extends StructMapper<GroupScheduleDto,GroupScheduleEntity>{
	GroupScheduleMapper MAPPER = Mappers.getMapper(GroupScheduleMapper.class);
	
	@Override
	GroupScheduleEntity toEntity(final GroupScheduleDto dto);
	
	@Override
	GroupScheduleDto toDto(final GroupScheduleEntity entity);
	
	@Override
	List<GroupScheduleDto> toDtoList(List<GroupScheduleEntity> entityList);
	
	
	
}