package com.project.common.mapper.Feed;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.common.dto.Feed.FeedHashtagDto;
import com.project.common.entity.Feed.FeedHashtagEntity;
import com.project.common.mapper.StructMapper;

@Mapper
public interface FeedHashtagMapper extends StructMapper<FeedHashtagDto,FeedHashtagEntity>{
	FeedHashtagMapper MAPPER = Mappers.getMapper(FeedHashtagMapper.class);
	
	@Override
	FeedHashtagEntity toEntity(final FeedHashtagDto dto);
	
	@Override
	FeedHashtagDto toDto(final FeedHashtagEntity entity);
	
	@Override
	List<FeedHashtagDto> toDtoList(List<FeedHashtagEntity> entityList);
	
}