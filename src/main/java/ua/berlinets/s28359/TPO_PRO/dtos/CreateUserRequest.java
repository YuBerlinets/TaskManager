package ua.berlinets.s28359.TPO_PRO.dtos;

import lombok.Data;
import ua.berlinets.s28359.TPO_PRO.enums.RoleEnum;

import java.util.List;

@Data
public class CreateUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<RoleEnum> roles;
}
