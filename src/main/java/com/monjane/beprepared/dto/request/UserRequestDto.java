package com.monjane.beprepared.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max = 20)
    private String password;
}
