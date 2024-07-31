package com.monjane.beprepared.controller;

import com.monjane.beprepared.dto.request.AuthenticationRequestForCitizens;
import com.monjane.beprepared.dto.request.AuthenticationRequestForUser;
import com.monjane.beprepared.dto.response.TokenResponse;
import com.monjane.beprepared.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "1. Authentication Controller")

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @Operation(summary = "Entre aqui para fazer a autenticação do ADMIN")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthenticationRequestForUser authenticationRequestForUser){
        return   ResponseEntity.ok(authenticationService.authenticate(authenticationRequestForUser));
    }

    @PostMapping("/login")
    @Operation(summary = "Entre aqui para fazer a autenticação do CIDADÃO")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthenticationRequestForCitizens  authenticationRequestForCitizens){
        return ResponseEntity.ok(authenticationService.authenticateCitizen(authenticationRequestForCitizens));
    }
}
