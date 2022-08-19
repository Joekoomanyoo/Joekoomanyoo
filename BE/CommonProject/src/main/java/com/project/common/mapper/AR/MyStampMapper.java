package com.project.common.mapper.AR;

import com.project.common.dto.AR.MyStampDto;
import com.project.common.entity.AR.MyStampEntity;
import com.project.common.mapper.StructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MyStampMapper extends StructMapper<MyStampDto, MyStampEntity> {
    MyStampMapper MAPPER = Mappers.getMapper(MyStampMapper.class);

    @Override
    MyStampEntity toEntity(final MyStampDto myStampDto);

    @Override
    MyStampDto toDto(final MyStampEntity myStampEntity);
}
