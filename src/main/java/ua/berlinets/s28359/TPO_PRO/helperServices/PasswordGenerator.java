package ua.berlinets.s28359.TPO_PRO.helperServices;

import org.springframework.stereotype.Service;

@Service
public class PasswordGenerator {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz!@#$%^&*()_+";

    public String generatePassword() {
        StringBuilder builder = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
