package ua.berlinets.s28359.TPO_PRO.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "Username: " + username + " Email: " + email + " First Name: " + firstName + " Last Name: " + lastName;
    }
}
