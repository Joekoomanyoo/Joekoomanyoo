package com.project.common.mapper.Feed;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.common.dto.Feed.FeedDto;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.mapper.StructMapper;

@Mapper
public interface FeedMapper extends StructMapper<FeedDto,FeedEntity>{
	FeedMapper MAPPER = Mappers.getMapper(FeedMapper.class);
	
	@Override
	FeedEntity toEntity(final FeedDto dto);
	
	@Override
	FeedDto toDto(final FeedEntity entity);
	
	@Override
	List<FeedDto> toDtoList(List<FeedEntity> entityList);
	
}