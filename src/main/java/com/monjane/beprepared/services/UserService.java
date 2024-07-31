package com.monjane.beprepared.services;

import com.monjane.beprepared.dto.response.StatsResponse;
import com.monjane.beprepared.model.User;

import javax.naming.ldap.StartTlsResponse;

public interface UserService {

    String createUser(User user);

    User getUserById(Long id);

    StatsResponse getAllStats();
}
