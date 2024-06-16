package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.features.auth.dto.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserRegisterRequest(RegisterRequest registerRequest);
}
