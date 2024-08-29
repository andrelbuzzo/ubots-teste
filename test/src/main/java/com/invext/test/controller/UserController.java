package com.invext.test.controller;

import com.invext.test.dto.AuthenticationRequest;
import com.invext.test.dto.AuthenticationResponse;
import com.invext.test.dto.UserRequest;
import com.invext.test.dto.UserResponse;
import com.invext.test.model.User;
import com.invext.test.service.AuthenticationService;
import com.invext.test.service.UserService;
import com.invext.test.util.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserMapper mapper;
	private final AuthenticationService authenticationService;

	@GetMapping
	public ResponseEntity<List<UserResponse>> getUsers() {
		List<User> users = userService.find();
		List<UserResponse> resp = users.stream().map(mapper::toResponse).toList();
		return ResponseEntity.ok(resp);
	}

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
		User user = mapper.toModel(request);
		user = userService.save(user);
		UserResponse resp = mapper.toResponse(user);
		return ResponseEntity.created(URI.create(user.getId().toString())).body(resp);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
		String token = authenticationService.authenticate(request.getEmail(), request.getPassword());
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}

}