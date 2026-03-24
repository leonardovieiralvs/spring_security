package dev.leonardo.security.mapper;

import dev.leonardo.security.domain.User;
import dev.leonardo.security.dto.RegisterRequestDto;
import dev.leonardo.security.dto.ResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ResponseDto toDto (User user);
    User toEntityRequest(RegisterRequestDto registerDto);
    User toEntity(ResponseDto responseDto);
}
