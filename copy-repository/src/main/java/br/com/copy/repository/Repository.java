package br.com.copy.repository;

import java.util.List;

public interface Repository<T> {
	public void save(T entity);

	public void update(T entity);

	public void delete(Long id);

	public T findById(Long id);

	public List<T> findAll();
}
