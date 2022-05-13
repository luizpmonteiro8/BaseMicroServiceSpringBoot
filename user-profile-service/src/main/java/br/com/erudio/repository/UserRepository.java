package br.com.erudio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.erudio.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByLogin(String login);

	// @Query("SELECT t FROM User t WHERE " + "LOWER(t.name) LIKE
	// LOWER(CONCAT('%',:searchTerm, '%'))")
	// Page<User> findByNamePagination(@Param("searchTerm") String searchTerm,
	// Pageable pageRequest);

}