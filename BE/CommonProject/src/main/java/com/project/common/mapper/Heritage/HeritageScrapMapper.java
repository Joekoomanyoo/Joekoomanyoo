package com.project.common.mapper.Heritage;

import com.project.common.dto.Heritage.HeritageScrapDto;
import com.project.common.entity.Heritage.HeritageScrapEntity;
import com.project.common.mapper.StructMapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HeritageScrapMapper extends StructMapper<HeritageScrapDto, HeritageScrapEntity> {
    HeritageScrapMapper MAPPER = Mappers.getMapper(HeritageScrapMapper.class);

    @Override
    HeritageScrapEntity toEntity(final HeritageScrapDto heritageScrapDto);

    @Override
    HeritageScrapDto toDto(final HeritageScrapEntity heritageScrapEntity);
}
