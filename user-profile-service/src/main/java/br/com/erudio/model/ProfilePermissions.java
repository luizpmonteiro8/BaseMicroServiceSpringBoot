package br.com.erudio.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "profiles_permissions")
@Data
@NoArgsConstructor
public class ProfilePermissions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String menuName;
	private String menuLink;
	private Boolean readValue;
	private Boolean writeValue;
	private Boolean deleteValue;
	private Long profileId;

}
