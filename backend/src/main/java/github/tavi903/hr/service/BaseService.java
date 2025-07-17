package github.tavi903.hr.service;

import github.tavi903.hr.mapper.IBaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Transactional
@RequiredArgsConstructor
public abstract class BaseService<ID, E, DTO> {

    protected final JpaRepository<E, ID> repository;
    protected final IBaseMapper<E, DTO> mapper;

    public DTO createOrUpdate(DTO dto) {
        E entity = mapper.fromDto(dto);
        return mapper.fromEntity(repository.save(entity));
    }

    public List<DTO> createOrUpdate(DTO... dtos) {
        List<E> entities = mapper.fromDtos(Arrays.asList(dtos));
        return mapper.fromEntities(repository.saveAll(entities));
    }

    public void delete(DTO... dtos) {
        List<E> entities = mapper.fromDtos(Arrays.asList(dtos));
        repository.deleteAll(entities);
    }

    public Page<DTO> findAll(Pageable pageable) {
        Page<E> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.getContent().stream().map(mapper::fromEntity).toList(),
                pageable,
                page.getTotalElements()
        );
    }

}
