package com.project.common.mapper.Heritage;

import com.project.common.dto.Heritage.HeritageDto;
import com.project.common.entity.Heritage.HeritageEntity;
import com.project.common.mapper.StructMapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HeritageMapper extends StructMapper<HeritageDto, HeritageEntity> {

        HeritageMapper MAPPER = Mappers.getMapper(HeritageMapper.class);

        @Override
        HeritageEntity toEntity(final HeritageDto heritageDto);

        @Override
        HeritageDto toDto(final HeritageEntity heritageEntity);

        @Override
        List<HeritageDto> toDtoList(List<HeritageEntity> entityList);

        @Override
        List<HeritageEntity> toEntityList(List<HeritageDto> dtoList);

}
