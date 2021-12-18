package com.kamilwnek.lift_management.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface DtoMapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    default List<D> listToDtos(List<E> entities){
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
