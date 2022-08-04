package com.project.common.dto;

import com.project.common.entity.UserKeywordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserKeywordMapper extends StructMapper<UserKeywordDto, UserKeywordEntity> {
    UserKeywordMapper MAPPER = Mappers.getMapper(UserKeywordMapper.class);

    @Override
    UserKeywordEntity toEntity(final UserKeywordDto userKeywordDto);

    @Override
    UserKeywordDto toDto(final UserKeywordEntity userKeywordEntity);
}
