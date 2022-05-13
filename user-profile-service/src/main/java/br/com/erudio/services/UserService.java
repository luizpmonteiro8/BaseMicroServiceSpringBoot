package br.com.erudio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.erudio.dto.UserDTO;
import br.com.erudio.model.User;
import br.com.erudio.model.enums.Permissions;
import br.com.erudio.repository.UserRepository;
import br.com.erudio.services.exception.DataIntegrityException;
import br.com.erudio.services.exception.ObjectNFException;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;

	@Autowired
	private BCryptPasswordEncoder pe;

	public User findById(Long id) {
		Optional<User> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNFException("Usuário não encontrado! Id: " + id));
	}

	public User findByLogin(String login) {
		Optional<User> obj = repo.findByLogin(login);

		return obj.orElseThrow(() -> new ObjectNFException("Usuário não encontrado! Login: " + login));
	}

	public Page<User> findPage(String search, Integer page, Integer size, String orderBy, String direction) {
		Pageable pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);

		return null;// repo.findByNamePagination(search, pageRequest);
	}

	public User insert(User obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public User update(User obj) {
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(obj);
	}

	private void updateData(User newObj, User obj) {
		newObj.setId(obj.getId());
		newObj.setName(obj.getName());
		newObj.setLogin(obj.getLogin());
		newObj.setEmail(obj.getEmail());
		newObj.setPassword(pe.encode(obj.getPassword()));
		newObj.getPermissionList().addAll(obj.getPermissionList());

	}

	public void delete(Long id) {
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível usuário em uso!");
		}
	}

	public User fromDTO(UserDTO objDTO) {
		User user = new User();

		user.setId(objDTO.getId());
		user.setName(objDTO.getName());
		user.setLogin(objDTO.getLogin());
		user.setEmail(objDTO.getEmail());
		user.setPassword(pe.encode(objDTO.getPassword()));
		user.addPermissions(Permissions.ADMIN);

		return user;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
