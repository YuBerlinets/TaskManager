package ua.berlinets.s28359.TPO_PRO.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateUserRequest;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateUserResponse;
import ua.berlinets.s28359.TPO_PRO.dtos.UpdatePasswordDTO;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.enums.RoleEnum;
import ua.berlinets.s28359.TPO_PRO.helperServices.PasswordGenerator;
import ua.berlinets.s28359.TPO_PRO.helperServices.UserMapper;
import ua.berlinets.s28359.TPO_PRO.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final PasswordGenerator passwordGenerator;
    private final RoleService roleService;

    public CreateUserResponse saveUser(CreateUserRequest request) {
        User user = userMapper.requestToUser(request);
        String password = passwordGenerator.generatePassword();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        return userMapper.createUserToResponse(user, password);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findById(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void updatePassword(UpdatePasswordDTO request) {
        User user = userRepository.findById(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public boolean isUserAllowedToSeeTasks(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.ADMIN.toString()))
                || user.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.PM.toString()))
                || user.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.DEV.toString()))
                || user.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.TEAM_LEAD.toString()));
    }

    public boolean isUserAllowedToCreateTasks(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.ADMIN.toString()))
                || user.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.PM.toString()))
                || user.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.TEAM_LEAD.toString()));
    }

    public List<RoleEnum> getAvailableRolesToAdd(User user) {
        List<RoleEnum> result = new ArrayList<>();
        for (RoleEnum role : RoleEnum.values()) {
            if (!user.getAuthorities().contains(new SimpleGrantedAuthority(role.toString())))
                result.add(role);
        }
        return result;
    }

    public void updateUserRoles(String username, List<RoleEnum> newRoles) {
        User user = findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        for (RoleEnum role : newRoles) {
            user.getRoles().add(roleService.findByRoleName(role));
        }
        userRepository.save(user);
    }
}
