package com.project.common.mapper.AR;

import com.project.common.dto.AR.StampCategoryDto;
import com.project.common.entity.AR.StampCategoryEntity;
import com.project.common.mapper.StructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StampCategoryMapper extends StructMapper<StampCategoryDto, StampCategoryEntity> {
    StampCategoryMapper MAPPER = Mappers.getMapper(StampCategoryMapper.class);

    @Override
    StampCategoryEntity toEntity(final StampCategoryDto stampCategoryDto);

    @Override
    StampCategoryDto toDto(final StampCategoryEntity stampCategoryEntity);

    @Override
    List<StampCategoryDto> toDtoList(List<StampCategoryEntity> entityList);

    @Override
    List<StampCategoryEntity> toEntityList(List<StampCategoryDto> dtoList);
}
