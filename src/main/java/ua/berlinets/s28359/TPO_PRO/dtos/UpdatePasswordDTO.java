package ua.berlinets.s28359.TPO_PRO.dtos;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String username;
    private String password;
    private String password_repeat;
}
