package github.tavi903.hr.mapper;

import java.util.List;

public interface IBaseMapper<E, DTO> {
    DTO fromEntity(E entity);
    List<DTO> fromEntities(List<E> entities);
    E fromDto(DTO dto);
    List<E> fromDtos(List<DTO> dtos);
}
