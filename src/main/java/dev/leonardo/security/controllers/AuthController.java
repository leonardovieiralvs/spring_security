package dev.leonardo.security.controllers;

import dev.leonardo.security.domain.User;
import dev.leonardo.security.dto.LoginRequestDto;
import dev.leonardo.security.dto.RegisterRequestDto;
import dev.leonardo.security.dto.ResponseDto;
import dev.leonardo.security.infra.security.TokenService;
import dev.leonardo.security.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.email()).orElseThrow(() -> new RuntimeException("User Not Found"));

        if (passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDto(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        Optional<User> user = userRepository.findByEmail(registerRequestDto.email());
        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(registerRequestDto.password()));
            newUser.setEmail(registerRequestDto.email());
            newUser.setName(registerRequestDto.name());
            userRepository.save(newUser);

            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDto(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}