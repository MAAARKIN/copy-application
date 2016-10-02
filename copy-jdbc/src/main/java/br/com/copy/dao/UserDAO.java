package br.com.copy.dao;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import br.com.copy.model.User;
import br.com.copy.repository.UserRepository;

@Repository
public class UserDAO extends GenericDAO<User> implements UserRepository {

	public UserDAO(DataSource dataSource) {
		super(User.class, dataSource);
	}

	@Override
	public void save(User user) {
		String sql = "INSERT INTO USER (NAME, AGE) VALUES (?, ?)";
		getJdbcTemplate().update(sql, new Object[] { user.getName(), user.getAge() });
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE USER SET name=?, age=? WHERE id=?";
		getJdbcTemplate().update(sql, new Object[] { user.getName(), user.getAge(), user.getId() });
	}

}
