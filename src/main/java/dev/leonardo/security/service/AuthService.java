package dev.leonardo.security.service;

import dev.leonardo.security.domain.User;
import dev.leonardo.security.dto.LoginRequestDto;
import dev.leonardo.security.dto.RegisterRequestDto;
import dev.leonardo.security.dto.ResponseDto;
import dev.leonardo.security.infra.security.TokenService;
import dev.leonardo.security.mapper.UserMapper;
import dev.leonardo.security.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public ResponseDto authenticate(LoginRequestDto requestDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password()));

        User authenticateUser = (User) authenticate.getPrincipal();

        String token = tokenService.generateToken(authenticateUser);

        return new ResponseDto(authenticateUser.getName(), token);
    }

    public ResponseDto register(RegisterRequestDto registerDto) {
        User userSave = userMapper.toEntityRequest(registerDto);
        userSave.setPassword(encoder.encode(registerDto.password()));

        User save = userRepository.save(userSave);
        return userMapper.toDto(save);
    }
}
