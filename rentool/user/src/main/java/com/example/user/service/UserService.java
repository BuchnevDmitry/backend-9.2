package com.example.user.service;

import com.example.user.api.model.request.UserRequest;
import com.example.user.exception.NotFoundException;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    public void addUser(UserRequest userRequest) {
        User user = keycloakService.registrationUser(userRequest);
        userRepository.save(user);
    }

    public List<User> getUsers(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).getContent();
    }

    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь с данным id не найден!"));
    }
}
