package com.pingan.edgeservice.security;

import java.util.List;

public record User(
        String username,
        String firstname,
        String lastName,
        List<String> roles) {
}
