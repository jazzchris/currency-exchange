package com.jazzchris.currencyexchange.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jazzchris.currencyexchange.dao.RoleRepository;
import com.jazzchris.currencyexchange.dao.UserRepository;
import com.jazzchris.currencyexchange.dto.UserDto;
import com.jazzchris.currencyexchange.dto.UserDtoConverter;
import com.jazzchris.currencyexchange.entity.Role;
import com.jazzchris.currencyexchange.entity.UserRegistration;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.entity.UsersWallet;
import com.jazzchris.currencyexchange.stock.Currency;

@Service
public class UserServiceImpl implements UserService {

	@Autowired private UserRepository userRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	private UserDtoConverter userDtoConverter = new UserDtoConverter();

	@Override
	public Optional<Users> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void save(UserRegistration userRegistration) {
		Users user = convert(userRegistration);
		userRepository.save(user);
	}

	private Users convert(UserRegistration userRegistration) {
		Users user = new Users();
		user.setUsername(userRegistration.getUsername());
		user.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
		user.setFirstName(userRegistration.getFirstName());
		user.setLastName(userRegistration.getLastName());
		user.setEmail(userRegistration.getEmail());
		UsersWallet wallet = new UsersWallet();
		Arrays.asList(Currency.values()).stream()
			.forEach(curr -> wallet.setByCurrency(curr, Optional.ofNullable(userRegistration.getByCurrency(curr))
			.orElse(BigDecimal.ZERO)));
		user.setUsersWallet(wallet);
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").get()));
		return user;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		Users user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public UserDto findAndConvert(String username) {
		return userDtoConverter.convert(findByUsername(username).get());
	}
}
