package com.project.common.mapper;

import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.entity.Push.FcmHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FcmHistoryMapper extends StructMapper<FcmHistoryDto, FcmHistoryEntity> {
    FcmHistoryMapper MAPPER = Mappers.getMapper(FcmHistoryMapper.class);

    @Override
    FcmHistoryEntity toEntity(final FcmHistoryDto fcmHistoryDto);

    @Override
    FcmHistoryDto toDto(final FcmHistoryEntity fcmHistoryEntity);
}
