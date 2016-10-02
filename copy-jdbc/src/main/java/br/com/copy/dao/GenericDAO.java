package br.com.copy.dao;

import java.text.MessageFormat;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import br.com.copy.repository.Repository;

public abstract class GenericDAO<T> implements Repository<T> {

	private JdbcTemplate jdbcTemplate;
	private Class<T> entityClass;
	
	public GenericDAO(Class<T> entityClass, DataSource dataSource) {
		this.entityClass = entityClass;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void delete(Long id) {
		String sql = "DELETE FROM {0} WHERE ID = ? ";
		sql = MessageFormat.format(sql, entityClass.getSimpleName());
		jdbcTemplate.update(sql, new Object[] { id });
	}

	public T findById(Long id) {
		try {
			String sql = "SELECT * FROM {0} WHERE ID = ?";
			sql = MessageFormat.format(sql, entityClass.getSimpleName());
			return (T) jdbcTemplate.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<T>(entityClass));			
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<T> findAll() {
		String sql = "SELECT * FROM {0}";
		sql = MessageFormat.format(sql, entityClass.getSimpleName());
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(entityClass));
	}
	
	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
}
