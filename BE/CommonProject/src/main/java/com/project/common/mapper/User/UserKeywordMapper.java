package com.project.common.mapper.User;

import com.project.common.dto.User.UserKeywordDto;
import com.project.common.entity.User.UserKeywordEntity;
import com.project.common.mapper.StructMapper;

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
