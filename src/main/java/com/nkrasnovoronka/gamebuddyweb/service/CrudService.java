package com.nkrasnovoronka.gamebuddyweb.service;

import java.util.List;

public interface CrudService<T, I> {
    T create(T t);

    void update(I id, T updated);

    void delete(I id);

    T get(I id);

    List<T> getAll();
}
