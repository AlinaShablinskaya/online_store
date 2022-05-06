package by.it.academy.onlinestore.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E, ID> {
    Optional<E> save(E entity);

    void saveAll(List<E> entities);

    Optional<E> findById(ID id);

    List<E> findAll(int page, int itemsPerPage);

    List<E> findAll();

    void update(E entity);

    void deleteById(ID id);
}
