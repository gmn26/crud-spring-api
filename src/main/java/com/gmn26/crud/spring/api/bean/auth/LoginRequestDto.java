package com.gmn26.crud.spring.api.bean.auth;

import com.gmn26.crud.spring.api.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LoginRequestDto {

    @NotNull(message = ValidationMessage.USERNAME_REQUIRED)
    @NotEmpty(message = ValidationMessage.USERNAME_REQUIRED)
    @Size(min = 6, max = 16, message = "Username should be between 6 to 16 character")
    private String username;

    @NotNull(message = ValidationMessage.PASSWORD_REQUIRED)
    @NotEmpty(message = ValidationMessage.PASSWORD_REQUIRED)
    @Size(min = 8, message = "Password should have at least 8 character")
    private String password;
}
