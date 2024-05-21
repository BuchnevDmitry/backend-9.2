package com.example.user.api.model.response;

import com.example.user.model.User;
import java.util.List;

public record ListUserResponse(
    List<User> users,
    Integer size
) {
}
