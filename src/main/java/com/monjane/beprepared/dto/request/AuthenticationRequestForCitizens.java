package com.monjane.beprepared.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequestForCitizens {
    @NotBlank
    private String phone;
    @NotBlank
    private String otp;
}
