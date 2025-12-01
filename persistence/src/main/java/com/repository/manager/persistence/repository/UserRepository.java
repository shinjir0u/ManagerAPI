package com.repository.manager.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.repository.manager.persistence.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
