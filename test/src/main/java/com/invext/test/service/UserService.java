package com.invext.test.service;

import com.invext.test.enums.TicketType;
import com.invext.test.exception.ResourceAlreadyExistsException;
import com.invext.test.model.User;
import com.invext.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public User save(User user) {
		if (userRepo.existsByEmail(user.getEmail()))
			throw new ResourceAlreadyExistsException("User", user.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public List<User> find() {
		return userRepo.findAll();
	}

	public List<User> findAllByRole(TicketType role) {
		return userRepo.findAllByRole(role);
	}

	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	public User getUser(Authentication authentication){
		return findByEmail(authentication.getPrincipal().toString()).get();
	}

}