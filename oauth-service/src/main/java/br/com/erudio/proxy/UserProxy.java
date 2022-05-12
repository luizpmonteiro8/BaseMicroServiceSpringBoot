package br.com.erudio.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.erudio.model.User;

@FeignClient(name = "user-profile-service")
public interface UserProxy {

	@GetMapping(value = "/search")
	ResponseEntity<User> findByLogin(@RequestParam String login);

}