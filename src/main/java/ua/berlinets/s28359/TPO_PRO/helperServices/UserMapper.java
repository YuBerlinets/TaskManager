package ua.berlinets.s28359.TPO_PRO.helperServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateUserRequest;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateUserResponse;
import ua.berlinets.s28359.TPO_PRO.entities.Role;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.enums.RoleEnum;
import ua.berlinets.s28359.TPO_PRO.services.RoleService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final RoleService roleService;

    public User requestToUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleService.findByRoleName(RoleEnum.USER));
        for (RoleEnum roleEnum : request.getRoles()) {
            Role role = roleService.findByRoleName(roleEnum);
            userRoles.add(role);
        }

        user.setRoles(userRoles);
        user.setEvents(new ArrayList<>());
        user.setCreatedEvents(new ArrayList<>());
        user.setCreatedTasks(new ArrayList<>());
        user.setWorkingOnTasks(new ArrayList<>());

        return user;
    }

    public CreateUserResponse createUserToResponse(User user, String password) {
        CreateUserResponse response = new CreateUserResponse();
        response.setUsername(user.getUsername());
        response.setPassword(password);
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles());
        return response;
    }
}
