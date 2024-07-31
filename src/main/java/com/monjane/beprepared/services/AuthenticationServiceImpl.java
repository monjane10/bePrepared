package com.monjane.beprepared.services;

import com.monjane.beprepared.dto.request.AuthenticationRequestForCitizens;
import com.monjane.beprepared.dto.request.AuthenticationRequestForUser;
import com.monjane.beprepared.dto.response.TokenResponse;
import com.monjane.beprepared.model.Citizen;
import com.monjane.beprepared.model.Token;
import com.monjane.beprepared.model.User;
import com.monjane.beprepared.repository.CitizenRepository;
import com.monjane.beprepared.repository.TokenRepository;
import com.monjane.beprepared.repository.UserRepository;
import com.monjane.beprepared.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final CitizenRepository citizenRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public TokenResponse authenticate(AuthenticationRequestForUser authenticationRequestForUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestForUser.getEmail(),
                        authenticationRequestForUser.getPassword()
                )
        );

        var user = userRepository.findByEmail(authenticationRequestForUser.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));

        var userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        var token = jwtService.generateToken(userDetails);

        saveUserToken(user, token);

        return TokenResponse.builder()
                .acessToken(token)
                .build();
    }

    @Override
    public TokenResponse authenticateCitizen(AuthenticationRequestForCitizens authenticationRequestForCitizens) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestForCitizens.getPhone(),
                        authenticationRequestForCitizens.getOtp()
                ));

        var citizen = citizenRepository.findByPhone(authenticationRequestForCitizens.getPhone()).orElseThrow();
        var token = jwtService.generateToken(citizen);
        saveCitizenToken(citizen, token);
        citizen.setOtp(null);
        citizenRepository.save(citizen);
        return TokenResponse.builder()
                .acessToken(token)
                .build();
    }

    private void saveUserToken(User user, String token) {
        var jwtToken = Token.builder()
                .user(user)
                .token(token)
                .expired(false)
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .build();
        tokenRepository.save(jwtToken);
    }

    private void saveCitizenToken(Citizen citizen, String token) {
        var jwtToken = Token.builder()
                .citizen(citizen)
                .token(token)
                .expired(false)
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .build();
        tokenRepository.save(jwtToken);
    }
}