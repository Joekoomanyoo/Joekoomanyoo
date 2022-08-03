package com.project.common.dto;

import com.project.common.entity.HeritageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HeritageMapper extends StructMapper<HeritageDto, HeritageEntity> {

    HeritageMapper MAPPER = Mappers.getMapper(HeritageMapper.class);

    @Override
    HeritageEntity toEntity(final HeritageDto heritageDto);

    @Override
    HeritageDto toDto(final HeritageEntity heritageEntity);

}
