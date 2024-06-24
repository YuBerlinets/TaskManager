package ua.berlinets.s28359.TPO_PRO.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import ua.berlinets.s28359.TPO_PRO.enums.RoleEnum;

import java.util.Set;

@Data
@Entity
public class Role {
    @Id
    @Column(name = "roleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;

    @Override
    public String toString() {
        return "Role_" + role.toString();
    }
}
