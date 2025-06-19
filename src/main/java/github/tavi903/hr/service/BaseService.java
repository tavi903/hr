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

    @SafeVarargs
    public final List<DTO> createOrUpdate(E... entities) {
        return mapper.fromEntities(repository.saveAll(Arrays.asList(entities)));
    }

    @SafeVarargs
    public final void delete(E... entities) {
        repository.deleteAll(Arrays.asList(entities));
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
