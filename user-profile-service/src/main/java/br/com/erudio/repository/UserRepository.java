package br.com.erudio.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.erudio.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT t FROM User t WHERE " + "LOWER(t.name) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	Page<User> findByNamePagination(@Param("searchTerm") String searchTerm, Pageable pageRequest);

	Optional<User> findByLogin(String login);

}