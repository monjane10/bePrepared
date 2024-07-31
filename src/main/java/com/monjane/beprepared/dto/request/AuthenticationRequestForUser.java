package com.monjane.beprepared.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequestForUser {

    private String email;
    private String password;
}
