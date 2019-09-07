package com.jazzchris.currencyexchange.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jazzchris.currencyexchange.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByUsername(String username);

}
