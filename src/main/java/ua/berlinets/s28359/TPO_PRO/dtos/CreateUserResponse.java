package ua.berlinets.s28359.TPO_PRO.dtos;

import lombok.Data;
import ua.berlinets.s28359.TPO_PRO.entities.Role;

import java.util.List;

@Data
public class CreateUserResponse {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;
}
