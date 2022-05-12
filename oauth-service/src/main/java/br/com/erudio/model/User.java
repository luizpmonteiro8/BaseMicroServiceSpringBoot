package br.com.erudio.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.erudio.model.enums.Permissions;
import lombok.Data;

@Data
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String login;
	private String email;
	private String password;

	private Set<Integer> permissionList = new HashSet<Integer>();

	public Set<Permissions> getPermissions() {
		return permissionList.stream().map(x -> Permissions.toEnum(x)).collect(Collectors.toSet());
	}

	public User(Long id, String name, String login, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.login = login;
		this.email = email;
		this.password = password;
	}

}
