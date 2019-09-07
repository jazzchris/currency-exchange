package com.jazzchris.currencyexchange.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jazzchris.currencyexchange.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(String string);

}
