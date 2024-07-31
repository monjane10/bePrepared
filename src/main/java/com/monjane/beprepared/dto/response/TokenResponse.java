package com.monjane.beprepared.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String acessToken;
}
