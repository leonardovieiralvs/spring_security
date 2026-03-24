package dev.leonardo.security.controllers;

import dev.leonardo.security.dto.LoginRequestDto;
import dev.leonardo.security.dto.RegisterRequestDto;
import dev.leonardo.security.dto.ResponseDto;
import dev.leonardo.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authenticate;

    public AuthController(AuthService authenticate) {
        this.authenticate = authenticate;
    }

    @GetMapping("/test")
    public String testando() {
        return "test security!!";
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        ResponseDto userAuthenticate = authenticate.authenticate(loginRequestDto);
        return ResponseEntity.ok(userAuthenticate);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        authenticate.register(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}