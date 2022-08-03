package com.project.common.dto;

import java.util.List;


public interface StructMapper<D,E>{

    E toEntity(final D dto);
    D toDto(final E entity);

    List<D> toDtoList(List<E> entityList);
    List<E> toEntityList(List<D> dtoList);
}
