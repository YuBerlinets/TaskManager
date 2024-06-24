package ua.berlinets.s28359.TPO_PRO.apiControllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateUserRequest;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateUserResponse;
import ua.berlinets.s28359.TPO_PRO.dtos.UpdatePasswordDTO;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.services.UserService;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse response = userService.saveUser(request);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user.getRoles());
    }

    @PatchMapping("/update-password")
    public ResponseEntity<Object> updatePassword(@RequestBody UpdatePasswordDTO request) {
        userService.updatePassword(request);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
}
