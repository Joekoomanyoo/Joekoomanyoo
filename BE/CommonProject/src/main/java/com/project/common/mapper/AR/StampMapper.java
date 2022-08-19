package com.project.common.mapper.AR;

import com.project.common.dto.AR.StampDto;
import com.project.common.entity.AR.StampEntity;
import com.project.common.mapper.StructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StampMapper extends StructMapper<StampDto, StampEntity> {
    StampMapper MAPPER = Mappers.getMapper(StampMapper.class);

    @Override
    StampEntity toEntity(final StampDto stampDto);

    @Override
    StampDto toDto(final StampEntity stampEntity);
}
