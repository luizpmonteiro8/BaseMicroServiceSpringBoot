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
		User user = userProxy.findByLogin(login).getBody();
		if (user == null) {
			logger.error("Email not found: " + login);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("Email found: " + login);
		return new UserSS(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getPermissions());
	}
}
