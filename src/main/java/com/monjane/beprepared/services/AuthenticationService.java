package com.monjane.beprepared.services;

import com.monjane.beprepared.dto.request.AuthenticationRequestForCitizens;
import com.monjane.beprepared.dto.request.AuthenticationRequestForUser;
import com.monjane.beprepared.dto.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse authenticate(AuthenticationRequestForUser authenticationRequestForUser);

    TokenResponse authenticateCitizen(AuthenticationRequestForCitizens authenticationRequestForCitizens);
}
