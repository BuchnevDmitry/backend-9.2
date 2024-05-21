package com.example.user.service;

import com.example.user.api.model.UserRequest;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    public void addUser(UserRequest userRequest) {
        User user = keycloakService.registrationUser(userRequest);
        userRepository.save(user);
//        User user = new User();
//        user.setLogin(userRequest.login());
//        user.setLogin(userRequest.login());
    }
}
