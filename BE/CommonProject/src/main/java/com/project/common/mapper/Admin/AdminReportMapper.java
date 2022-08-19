package com.project.common.mapper.Admin;

import com.project.common.dto.Admin.AdminReportDto;
import com.project.common.entity.Admin.AdminReportEntity;
import com.project.common.mapper.StructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdminReportMapper extends StructMapper<AdminReportDto, AdminReportEntity> {
    AdminReportMapper MAPPER = Mappers.getMapper(AdminReportMapper.class);

    @Override
    AdminReportEntity toEntity(final AdminReportDto adminReportDto);

    @Override
    AdminReportDto toDto(final AdminReportEntity adminReportEntity);

    @Override
    List<AdminReportEntity> toEntityList(List<AdminReportDto> dtoList);

    @Override
    List<AdminReportDto> toDtoList(List<AdminReportEntity> entityList);

}
