package ua.berlinets.s28359.TPO_PRO.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateUserRequest;
import ua.berlinets.s28359.TPO_PRO.dtos.CreateUserResponse;
import ua.berlinets.s28359.TPO_PRO.dtos.UpdatePasswordDTO;
import ua.berlinets.s28359.TPO_PRO.entities.User;
import ua.berlinets.s28359.TPO_PRO.enums.RoleEnum;
import ua.berlinets.s28359.TPO_PRO.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final UserService userService;

    @GetMapping("/")
    public String mainPage() {
        return "redirect:/calendar";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
        model.addAttribute("isAuthorized", isAuthorized);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("createUserRequest", new CreateUserRequest());
        model.addAttribute("availableRoles", RoleEnum.values());
        return "admin";
    }

    @PostMapping("/admin/createUser")
    public String createUser(CreateUserRequest createUserRequest, Model model) {
        CreateUserResponse userResponse = userService.saveUser(createUserRequest);
        model.addAttribute("userResponse", userResponse);
        return "new-user-details";
    }

    @GetMapping("/admin/updateUser")
    public String updateUser(@RequestParam String username, Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
        User user = userService.findByUsername(username).orElse(null);
        if (user == null)
            return "redirect:/admin";
        List<RoleEnum> roles = userService.getAvailableRolesToAdd(user);
        model.addAttribute("isAuthorized", isAuthorized);
        model.addAttribute("availableRoles", roles);
        model.addAttribute("user", user);
        return "user-info-update";
    }

    @PostMapping("/admin/update")
    public String updateUserRoles(@RequestParam String username, @RequestParam List<String> roles) {
        List<RoleEnum> roleEnums = roles.stream()
                .map(RoleEnum::valueOf)
                .collect(Collectors.toList());
        userService.updateUserRoles(username, roleEnums);
        return "redirect:/admin";
    }

    @GetMapping("/account")
    public String account(Model model, Authentication authentication) {
        boolean isAuthorized = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
        boolean isAdmin = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        boolean isAllowedToSeeTasks = authentication != null && userService.isUserAllowedToSeeTasks(authentication.getName());
        model.addAttribute("isAuthorized", isAuthorized);
        model.addAttribute("isAllowedToSeeTasks", isAllowedToSeeTasks);
        model.addAttribute("isAdmin", isAdmin);
        User user = userService.findByUsername(authentication.getName()).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("updatePasswordDTO", new UpdatePasswordDTO());
        return "account";
    }

    @PostMapping("/account/update-password")
    public String updatePassword(UpdatePasswordDTO updatePasswordDTO, RedirectAttributes redirectAttributes) {
        if (!updatePasswordDTO.getPassword().equals(updatePasswordDTO.getPassword_repeat())) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/account";
        }

        userService.updatePassword(updatePasswordDTO);
        redirectAttributes.addFlashAttribute("success", "Password updated successfully");
        return "redirect:/account";
    }


}
