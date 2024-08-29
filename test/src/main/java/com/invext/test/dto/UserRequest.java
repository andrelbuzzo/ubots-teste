package com.invext.test.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequest {

	@NotEmpty(message = "{required.field}")
	private String name;

	@NotEmpty(message = "{required.field}")
	private String email;

	@NotEmpty(message = "{required.field}")
	private String password;

	@NotEmpty(message = "{required.field}")
	private String role;

}