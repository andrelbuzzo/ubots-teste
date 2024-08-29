package com.invext.test.util;

import com.invext.test.dto.UserRequest;
import com.invext.test.dto.UserResponse;
import com.invext.test.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Provides objects mapping,
 * acting as a abstraction layer of mapper provider
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

	private final ModelMapper mapper;

	public User toModel(UserRequest request) {
		return mapper.map(request, User.class);
	}

	public UserRequest toRequest(User user) {
		return mapper.map(user, UserRequest.class);
	}

	public UserResponse toResponse(User user) {
		return mapper.map(user, UserResponse.class);
	}

}