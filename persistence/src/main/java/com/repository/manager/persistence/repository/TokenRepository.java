package com.repository.manager.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.repository.manager.persistence.entity.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

}
