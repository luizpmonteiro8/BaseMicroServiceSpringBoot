package br.com.erudio.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.erudio.model.User;
import br.com.erudio.proxy.UserProxy;
import br.com.erudio.security.UserSS;

@Service
public class UserService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserProxy userProxy;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		// User user = userProxy.findByLogin(login).getBody();
		User user = new User(1L, "teste", "teste", "teste@teste.com",
				"$2a$10$S8wfQA8BIizJdcKYxs7.lOm/uIrSxXrtUy00PA9oAu2wTHtRCD1jm");
		if (user == null) {
			logger.error("Login not found: " + login);
			throw new UsernameNotFoundException("Login not found");
		}
		logger.info("Email found: " + login);
		return new UserSS(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getPermissions());
	}
}
