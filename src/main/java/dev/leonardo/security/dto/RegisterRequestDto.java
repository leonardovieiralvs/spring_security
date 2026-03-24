package dev.leonardo.security.dto;

import dev.leonardo.security.enums.UserRole;

public record RegisterRequestDto(String name, String email, String password, UserRole role) {
}
