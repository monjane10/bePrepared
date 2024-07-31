package com.monjane.beprepared.services.impl;

import com.monjane.beprepared.dto.response.StatsResponse;
import com.monjane.beprepared.exception.BadRequestException;
import com.monjane.beprepared.exception.EntityNotFoundException;
import com.monjane.beprepared.model.User;
import com.monjane.beprepared.model.enums.Role;
import com.monjane.beprepared.repository.AlertRepository;
import com.monjane.beprepared.repository.CitizenRepository;
import com.monjane.beprepared.repository.UserRepository;
import com.monjane.beprepared.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final AlertRepository alertRepository;
    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new BadRequestException("Ja existe  existe um utilizador com esse Email");
        }
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Utilizador criado com sucesso";
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Utilizador nao encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public StatsResponse getAllStats() {
        return StatsResponse.builder()
                .citizens(citizenRepository.count())
                .totalAlerts(alertRepository.count())
                .activeAlerts(alertRepository.countByActive(true))
                .build();

    }
}
