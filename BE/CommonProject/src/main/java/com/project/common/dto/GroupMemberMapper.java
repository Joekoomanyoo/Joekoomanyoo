package com.project.common.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.common.entity.GroupMemberEntity;

@Mapper
public interface GroupMemberMapper extends StructMapper<GroupMemberDto,GroupMemberEntity>{
	GroupMemberMapper MAPPER = Mappers.getMapper(GroupMemberMapper.class);
	
	@Override
	GroupMemberEntity toEntity(final GroupMemberDto dto);
	
	@Override
	GroupMemberDto toDto(final GroupMemberEntity entity);
	
	@Override
	List<GroupMemberDto> toDtoList(List<GroupMemberEntity> entityList);
	
	
	
}