package br.com.copy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.copy.exception.ApplicationException;
import br.com.copy.helper.ResponseStatus;
import br.com.copy.model.User;
import br.com.copy.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository usuarios;

	public void save(User usuario) {
		//aqui ficará regras de negocio
		usuarios.save(usuario);
	}

	public void update(User usuario) throws ApplicationException {
		User userBase = usuarios.findById(usuario.getId());

		if (userBase == null) throw new ApplicationException(ResponseStatus.USER_NOT_EXIST);
		
		userBase.setAge(usuario.getAge());
		userBase.setName(usuario.getName());
		//novos campos aqui...
		
		usuarios.update(userBase);
	}
}
