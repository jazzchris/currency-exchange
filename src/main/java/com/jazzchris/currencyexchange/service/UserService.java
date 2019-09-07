package com.jazzchris.currencyexchange.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jazzchris.currencyexchange.entity.UserRegistration;
import com.jazzchris.currencyexchange.entity.Users;

public interface UserService extends UserDetailsService {

	Optional<Users> findByUsername(String username);

	void save(UserRegistration userRegistration);
}
